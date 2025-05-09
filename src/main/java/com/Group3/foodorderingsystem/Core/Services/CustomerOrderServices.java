package com.Group3.foodorderingsystem.Core.Services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.itextpdf.layout.Document;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.awt.Desktop;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.OrderModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Order.VoucherModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ComplainModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.Group3.foodorderingsystem.Core.Model.Enum.ComplainStatusEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.OrderMethodEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;
import com.Group3.foodorderingsystem.Core.Storage.Storage;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;
import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;

public class CustomerOrderServices {

    // generate unique order id
    private static String generateUniqueOrderId() {
        List<OrderModel> orders = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class);

        if (orders == null) {
            orders = new ArrayList<>();
        }

        String orderId = UUID.randomUUID().toString().substring(0, 8);
        boolean isDuplicate;
        do {
            orderId = UUID.randomUUID().toString().substring(0, 8);
            isDuplicate = false;
            for (OrderModel order : orders) {
                if (order.getOrderId().equals(orderId)) {
                    isDuplicate = true;
                    break;
                }
            }
        } while (isDuplicate);

        return orderId;
    }

    // save order to file
    private static void saveOrderToFile(OrderModel order) {
        List<OrderModel> orders = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class);

        orders.add(order);

        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.ORDER), orders);
    }

    // calculate total price
    public static double calculatePrice(List<ItemModel> items) {
        return items.stream()
                .mapToDouble(item -> item.getItemPrice() * item.getItemQuantity())
                .sum();
    }

    public static double calculateDeliveryFee(String state) {
        if (state.contains("Petaling")) {
            return 4.00;
        } else if (state.contains("Gombak")) {
            return 8.00;
        } else if (state.contains("Klang")) {
            return 10.00;
        } else if (state.contains("Kuala Langat")) {
            return 12.00;
        } else if (state.contains("Kuala Selangor")) {
            return 15.00;
        } else if (state.contains("Hulu Langat")) {
            return 6.00;
        } else if (state.contains("Hulu Selangor")) {
            return 16.00;
        } else if (state.contains("Sabak Bernam")) {
            return 18.00;
        } else if (state.contains("Sepang")) {
            return 12.00;
        } else if (state.contains("Batu")) {
            return 7.00;
        } else if (state.contains("Bukit Bintang")) {
            return 6.00;
        } else if (state.contains("Cheras")) {
            return 6.00;
        } else if (state.contains("Kepong")) {
            return 8.00;
        } else if (state.contains("Lembah Pantai")) {
            return 7.00;
        } else if (state.contains("Segambut")) {
            return 6.50;
        } else if (state.contains("Seputeh")) {
            return 5.50;
        } else if (state.contains("Setiawangsa")) {
            return 7.50;
        } else if (state.contains("Titiwangsa")) {
            return 6.00;
        } else if (state.contains("Wangsa Maju")) {
            return 7.50;
        } else if (state.contains("Bandar Tun Razak")) {
            return 6.00;
        } else if (state.contains("Putrajaya")) {
            return 7.00;
        } else {
            return 0.00;
        }
    }

    // check if customer id same with session id
    public boolean checkCustomerId(String customerId) {
        return customerId.equals(SessionUtil.getCustomerFromSession().getId());
    }

    // place order. it will accept order method, delivery address (but not required)
    public static String placeOrder(OrderMethodEnum orderMethod, String deliveryAddress, String state,
            Double voucherRate) {
        List<ItemModel> items = SessionUtil.getItemsFromSession();
        CustomerModel customer = SessionUtil.getCustomerFromSession();
        VendorModel vendor = items.get(0).getVendorModel();

        // Calculate total price
        double deliveryFee;
        double subTotalPrice = calculatePrice(items);
        if (state == null) {
            deliveryFee = 0.00;
        } else {
            deliveryFee = calculateDeliveryFee(state);
        }

        // generate order id
        String orderId = generateUniqueOrderId();

        // Create order object
        OrderModel order = new OrderModel();
        order.setOrderId(orderId);
        order.setItems(items);
        order.setCustomer(customer.getId());
        order.setSubTotalPrice(subTotalPrice);
        order.setDeliveryFee(deliveryFee);
        order.setStatus(StatusEnum.PENDING);
        order.setOrderMethod(orderMethod);
        order.setDeliveryAddress(deliveryAddress);
        order.setVendor(vendor.getId());
        order.setArea(state);
        order.setVoucherRate(voucherRate);
        order.setTotalPrice(subTotalPrice + deliveryFee - (subTotalPrice * voucherRate / 100));

        // Save order to file
        saveOrderToFile(order);

        //deduct the balance form customer
        customer.setBalance(customer.getBalance() - order.getTotalPrice());
        SessionUtil.setCustomerInSession(customer);

        
        // Clear only item from session
        SessionUtil.setItemsInSession(null);
        return orderId;


    }

    // customer set balance, accpet jd, amoun
    public static void setBalance(String entityId, double amount, String entityType) {
        List<?> entities;

        switch (entityType.toLowerCase()) {
            case "customer":
                entities = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.CUSTOMER), CustomerModel.class);
                break;
            case "vendor":
                entities = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.VENDOR), VendorModel.class);
                break;
            default:
                throw new IllegalArgumentException("Invalid entity type: " + entityType);
        }

        for (Object entity : entities) {
            if (entity instanceof CustomerModel) {
                CustomerModel customer = (CustomerModel) entity;
                if (customer.getId().equals(entityId)) {
                    customer.setBalance(customer.getBalance() + amount);
                    break;
                }
            } else if (entity instanceof VendorModel) {
                VendorModel vendor = (VendorModel) entity;
                if (vendor.getId().equals(entityId)) {
                    vendor.setRevenue(vendor.getRevenue() + amount);
                    break;
                }
            }
        }

        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.valueOf(entityType.toUpperCase())), entities);
    }

    // cancel order
    public static void cancelOrder(String orderId) {
        List<OrderModel> orders = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class);

        for (OrderModel order : orders) {
            if (order.getOrderId().equals(orderId)) {
                order.setStatus(StatusEnum.CANCELLED);
                break;
            }
        }

        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.ORDER), orders);

    }

    // list pending order history
    public List<OrderModel> listPendingOrders() {
        List<OrderModel> orders = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class);
        List<OrderModel> pendingOrders = new ArrayList<>();

        for (OrderModel order : orders) {
            if (order.getStatus() == StatusEnum.PENDING && checkCustomerId(order.getCustomer())) {
                pendingOrders.add(order);
            }
        }

        return pendingOrders;
    }

    // list active order history
    public List<OrderModel> listActiveOrders() {
        List<OrderModel> orders = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class);
        List<OrderModel> activeOrders = new ArrayList<>();

        EnumSet<StatusEnum> validStatuses = EnumSet.of(
                StatusEnum.PREPARING,
                StatusEnum.READY_FOR_PICKUP,
                StatusEnum.DELIVERING,
                StatusEnum.DELIVERED);

        for (OrderModel order : orders) {
            if (validStatuses.contains(order.getStatus()) && checkCustomerId(order.getCustomer())) {
                activeOrders.add(order);
            }
        }

        return activeOrders;
    }

    // list past order history
    public List<OrderModel> listPastOrders() {
        List<OrderModel> orders = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class);
        List<OrderModel> pastOrders = new ArrayList<>();

        EnumSet<StatusEnum> validStatuses = EnumSet.of(
                StatusEnum.DELIVERED,
                StatusEnum.CANCELLED,
                StatusEnum.REJECTED);

        for (OrderModel order : orders) {
            if (validStatuses.contains(order.getStatus()) && checkCustomerId(order.getCustomer())) {
                pastOrders.add(order);
            }
        }

        return pastOrders;
    }

    // list a specific order details
    public OrderModel getOrderDetails(String orderId) {
        List<OrderModel> orders = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class);

        for (OrderModel order : orders) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }

        return null;
    }

    // get vendor model through id
    public VendorModel getVendorModel(String vendorId) {
        List<VendorModel> vendors = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.VENDOR), VendorModel.class);

        for (VendorModel vendor : vendors) {
            if (vendor.getId().equals(vendorId)) {
                return vendor;
            }
        }

        return null;
    }

    public static void generateReceipt() {
        OrderModel order = (OrderModel) SessionUtil.getSelectedOrderFromSession();
        CustomerModel customer = SessionUtil.getCustomerFromSession();

        if (order == null) {
            System.out.println("No order selected.");
            return;
        }

        String pdfPath = "src/main/resources/receipts/receipt_" + order.getOrderId() + ".pdf";
        try {
            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Company Name on the top right
            Paragraph companyName = new Paragraph("Food Orbit")
                    .setBold()
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setFontSize(12);
            document.add(companyName);

            // Title
            document.add(new Paragraph("Receipt").setBold().setTextAlignment(TextAlignment.CENTER).setFontSize(24));

            // Order Details Section
            document.add(new Paragraph("Order ID: " + order.getOrderId()).setFontSize(10));
            document.add(new Paragraph("Customer Name: " + customer.getName()).setFontSize(10));
            // Convert Date to LocalDateTime
            LocalDateTime orderDateTime = Instant.ofEpochMilli(order.getTime().getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            // Format LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm");
            String formattedDate = orderDateTime.format(formatter);
            document.add(new Paragraph("Order Date: " + formattedDate).setFontSize(10));
            String shopName = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.VENDOR), VendorModel.class,
                    vendor -> vendor.getId().equals(order.getVendor())).get(0).getShopName();
            document.add(new Paragraph("Shop Name: " + shopName).setFontSize(10));
            document.add(new Paragraph("Order Method: " + order.getOrderMethod()).setFontSize(10));
            document.add(new Paragraph(
                    "RM" + (order.getDeliveryFee() + order.getSubTotalPrice()) + " paid on " + formattedDate).setBold()
                    .setFontSize(15));

            // Items Table
            Table table = new Table(UnitValue.createPercentArray(new float[] { 1, 5, 2, 2 })).useAllAvailableWidth(); // Adjusted
                                                                                                                      // column
                                                                                                                      // widths
            table.addHeaderCell("Qty");
            table.addHeaderCell("Item");
            table.addHeaderCell("Price");
            table.addHeaderCell("Total");

            order.getItems().forEach(item -> {
                table.addCell(String.valueOf(item.getItemQuantity()));
                table.addCell(item.getItemName());
                table.addCell(String.format("RM%.2f", item.getItemPrice()));
                table.addCell(String.format("RM%.2f", item.getItemPrice() * item.getItemQuantity()));
            });
            document.add(table);

            // Total
            document.add(new Paragraph("Amount Paid: RM" + (order.getDeliveryFee() + order.getSubTotalPrice()))
                    .setBold().setTextAlignment(TextAlignment.RIGHT).setFontSize(15));

            // Footer
            document.add(new Paragraph("Thank you for your purchase!").setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("If you have any questions about your order, please contact us.")
                    .setTextAlignment(TextAlignment.CENTER));

            document.close();

            // Automatically open the PDF
            File pdfFile = new File(pdfPath);
            if (pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // update rating and feedback
    public static void updateRatingAndFeedback(String orderId, int rating, String review) {
        List<OrderModel> orders = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class);

        for (OrderModel order : orders) {
            if (order.getOrderId().equals(orderId)) {
                order.setRating(rating);
                order.setReview(review);
                break;
            }
        }

        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.ORDER), orders);
    }

    // voucher code validation
    public static double validateVoucherCode(String voucherCode) {
        String vendorID = SessionUtil.getItemsFromSession().get(0).getVendorModel().getId();
        List<VoucherModel> voucher = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.VOUCHER),
                VoucherModel.class,
                v -> v.getVendor().getId().equals(vendorID)
                        && v.getVoucherCode().toLowerCase().equals(voucherCode.toLowerCase()));

        // return discount rate if voucher code is valid
        if (voucher.size() > 0) {
            return voucher.get(0).getDiscountRate();
        } else {
            return 0;
        }

    }

    // retrieve complain by order id
    public static ComplainModel getComplain(String orderId) {
        List<ComplainModel> complaints = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.COMPLAIN),
                ComplainModel.class);

        for (ComplainModel complaint : complaints) {
            if (complaint.getOrderId().equals(orderId)) {
                return complaint;
            }
        }

        return null;
    }

    // submit complain
    public static void submitComplain(String orderId, String complainDescription) {
        ComplainModel complain = new ComplainModel();
        complain.setComplainId(UUID.randomUUID().toString().substring(0, 8));
        complain.setOrderId(orderId);
        complain.setComplainDescription(complainDescription);
        complain.setComplainStatus(ComplainStatusEnum.PENDING);

        List<ComplainModel> complaints = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.COMPLAIN),
                ComplainModel.class);

        complaints.add(complain);

        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.COMPLAIN), complaints);
    }

    public static boolean isUserRequestedOrder(User user) {
        return FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class).stream()
                .anyMatch(order -> order.getCustomer().equals(user.getId())
                        || order.getVendor().equals(user.getId())
                        || (order.getRider() != null && order.getRider().equals(user.getId())));
    }

}
