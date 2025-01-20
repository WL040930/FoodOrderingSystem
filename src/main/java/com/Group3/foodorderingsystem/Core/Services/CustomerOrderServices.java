package com.Group3.foodorderingsystem.Core.Services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
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
import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.Group3.foodorderingsystem.Core.Model.Enum.OrderMethodEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;

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

    // check if customer id same with session id
    public boolean checkCustomerId(String customerId) {
        return customerId.equals(SessionUtil.getCustomerFromSession().getId());
    }

    // place order. it will accept order method, delivery address (but not required)
    public static void placeOrder(OrderMethodEnum orderMethod, String deliveryAddress) {
        List<ItemModel> items = SessionUtil.getItemsFromSession();
        CustomerModel customer = SessionUtil.getCustomerFromSession();
        VendorModel vendor = items.get(0).getVendorModel();

        // Calculate total price
        double totalPrice = calculatePrice(items);

        // Create order object
        OrderModel order = new OrderModel();
        order.setOrderId(generateUniqueOrderId());
        order.setItems(items);
        order.setCustomer(customer.getId());
        order.setTotalPrice(totalPrice);
        order.setStatus(StatusEnum.PENDING);
        order.setOrderMethod(orderMethod);
        order.setDeliveryAddress(deliveryAddress);
        order.setVendor(vendor.getId());

        // Save order to file
        saveOrderToFile(order);

        // deduct the balance from customer
        customer.setBalance(customer.getBalance() - totalPrice);

        // Save customer to file
        List<CustomerModel> customers = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.CUSTOMER),
                CustomerModel.class);
        for (CustomerModel c : customers) {
            if (c.getId().equals(customer.getId())) {
                c.setBalance(customer.getBalance());
                break;
            }
        }
        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.CUSTOMER), customers);

        // Clear only item from session
        SessionUtil.setItemsInSession(null);

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

    // change to static 
    public List<OrderModel> getOrderByCustomerId(String customerId) {
        List<OrderModel> vendorOrders = new ArrayList<>();

        vendorOrders.addAll(listPendingOrders());
        vendorOrders.addAll(listActiveOrders());
        vendorOrders.addAll(listPastOrders());

        vendorOrders.sort((o1, o2) -> o2.getTime().compareTo(o1.getTime()));

        return vendorOrders;
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
            document.add(new Paragraph("RM" + order.getTotalPrice() + " paid on " + formattedDate).setBold()
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
            document.add(new Paragraph("Amount Paid: RM" + order.getTotalPrice()).setBold()
                    .setTextAlignment(TextAlignment.RIGHT).setFontSize(15));

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

}
