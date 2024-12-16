package com.Group3.foodorderingsystem.Core.Services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import com.itextpdf.layout.Document;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.awt.Desktop;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;



import com.Group3.foodorderingsystem.Core.Model.Entity.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.ItemModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.OrderModel;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;

public class CustomerOrderServices {

    //generate unique order id
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



    //save order to file
    private static void saveOrderToFile(OrderModel order) {
        List<OrderModel> orders = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class);

        orders.add(order);

        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.ORDER), orders);
    }


    //calculate total price
    public double calculatePrice(List<ItemModel> items) {
        return items.stream()
            .mapToDouble(item -> item.getItemPrice() * item.getItemQuantity())
            .sum();
    }

    //check if customer id same with session id
    public boolean checkCustomerId(String customerId) {
        return customerId.equals(SessionUtil.getCustomerFromSession().getId());
    }


    // save order to file
    public void saveOrder() {
        List<ItemModel> items = SessionUtil.getItemsFromSession();
        CustomerModel customer = SessionUtil.getCustomerFromSession();

        // Calculate total price
        double totalPrice = calculatePrice(items);

        // Create order object
        OrderModel order = new OrderModel();
        order.setOrderId(generateUniqueOrderId());
        order.setItems(items);
        order.setCustomer(customer);
        order.setTotalPrice(totalPrice);
        order.setStatus(StatusEnum.PENDING);

        // Save order to file
        saveOrderToFile(order);


        // Clear only item from session
        SessionUtil.setItemsInSession(null);

    }

    
    // cancel order
    public void cancelOrder(String orderId) {
        List<OrderModel> orders = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class);

        for (OrderModel order : orders) {
            if (order.getOrderId().equals(orderId)) {
                order.setStatus(StatusEnum.CANCELLED);
                break;
            }
        }

        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.ORDER), orders);
    }


    //list pending order history
    public List<OrderModel> listPendingOrders() {
        List<OrderModel> orders = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class);
        List<OrderModel> pendingOrders = new ArrayList<>();

        for (OrderModel order : orders) {
            if (order.getStatus() == StatusEnum.PENDING && checkCustomerId(order.getCustomer().getId())) {
                pendingOrders.add(order);
            }
        }

        return pendingOrders;
    }

    //list  active order history 
    public List<OrderModel> listActiveOrders() {
        List<OrderModel> orders = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class);
        List<OrderModel> activeOrders = new ArrayList<>();

        EnumSet<StatusEnum> validStatuses = EnumSet.of(
            StatusEnum.PREPARING,
            StatusEnum.READY_FOR_PICKUP,
            StatusEnum.DELIVERING,
            StatusEnum.DELIVERED
        );

        for (OrderModel order : orders) {
            if (validStatuses.contains(order.getStatus()) && checkCustomerId(order.getCustomer().getId())) {
                activeOrders.add(order);
            }
        }

        return activeOrders;
    }


    //list past order history
    public List<OrderModel> listPastOrders() {
        List<OrderModel> orders = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class);
        List<OrderModel> pastOrders = new ArrayList<>();

        EnumSet<StatusEnum> validStatuses = EnumSet.of(
            StatusEnum.DELIVERED,
            StatusEnum.CANCELLED,
            StatusEnum.REJECTED
        );

        for (OrderModel order : orders) {
            if (validStatuses.contains(order.getStatus()) && checkCustomerId(order.getCustomer().getId())) {
                pastOrders.add(order);
            }
        }

        return pastOrders;
    }


    //list a specific order details
    public OrderModel getOrderDetails(String orderId) {
        List<OrderModel> orders = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class);

        for (OrderModel order : orders) {
            if(order.getOrderId().equals(orderId)) {
                return order;
            }
        }

        return null;
    }

    //TODO: Generate receipt 

    public void generateReceipt() {
        OrderModel order = (OrderModel) SessionUtil.getSelectedOrderFromSession();
    
        if (order == null) {
            System.out.println("No order selected.");
            return;
        }
    
        String pdfPath = "receipt_" + order.getOrderId() + ".pdf";
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
            document.add(new Paragraph("Customer Name: " + order.getCustomer().getName()).setFontSize(10));
            document.add(new Paragraph("Order Date: " + order.getTime().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))).setFontSize(10));
            document.add(new Paragraph("Shop Name: " + order.getVendor().getShopName()).setFontSize(10));
            document.add(new Paragraph("Order Method: " + order.getOrderMethod()).setFontSize(10));
    
            // Pre-table Text
            document.add(new Paragraph("RM" + order.getTotalPrice() + " paid on " + order.getTime().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))).setBold().setFontSize(15));
    
            // Items Table
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 5, 2, 2})).useAllAvailableWidth();  // Adjusted column widths
            table.addHeaderCell("Qty");
            table.addHeaderCell("Item");
            table.addHeaderCell("Price");
            table.addHeaderCell("Total");
    
            order.getItems().forEach(item -> {
                table.addCell(String.valueOf(item.getItemQuantity()));
                table.addCell(item.getItemName());
                table.addCell(String.format("$%.2f", item.getItemPrice()));
                table.addCell(String.format("$%.2f", item.getItemPrice() * item.getItemQuantity()));
            });
            document.add(table);
    
            // Total
            document.add(new Paragraph("Amount Paid: $" + order.getTotalPrice()).setBold().setTextAlignment(TextAlignment.RIGHT).setFontSize(15));
    
            // Footer
            document.add(new Paragraph("Thank you for your purchase!").setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("If you have any questions about your order, please contact us.").setTextAlignment(TextAlignment.CENTER));
    
            document.close();
            System.out.println("Receipt generated: " + pdfPath);
    
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


    
}
