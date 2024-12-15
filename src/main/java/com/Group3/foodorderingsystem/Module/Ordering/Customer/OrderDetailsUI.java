package com.Group3.foodorderingsystem.Module.Ordering.Customer;

import com.Group3.foodorderingsystem.Core.Model.Entity.OrderModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.ItemModel;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Core.Model.Enum.OrderMethodEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Separator;

public class OrderDetailsUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f8fafc;");

        OrderModel selectedOrder = (OrderModel) SessionUtil.getSelectedOrderFromSession();

        if (selectedOrder != null) {
            displayOrderDetails(root, selectedOrder);
        } else {
            Label noOrderLabel = new Label("No order selected.");
            noOrderLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666666;");
            root.getChildren().add(noOrderLabel);
        }

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #f8fafc; -fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Create a back button
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(event -> primaryStage.close());

        // Create a VBox for the "Cancel Order" button
        VBox fixedVBox = new VBox(10);
        fixedVBox.getStyleClass().add("fixed-vbox");


        //if order status is pending, display cancel order button, else create two button, reorder and generate receipt
        if (selectedOrder.getStatus().equals(StatusEnum.PENDING)) {
            Button cancelOrderButton = new Button("Cancel Order");
            cancelOrderButton.getStyleClass().add("cancel-order-button");
            fixedVBox.getChildren().add(cancelOrderButton);
        } else {
            Button reorderButton = new Button("Reorder");
            reorderButton.getStyleClass().add("reorder-button");
            Button generateReceiptButton = new Button("Generate Receipt");
            generateReceiptButton.getStyleClass().add("generate-receipt-button");
            fixedVBox.getChildren().addAll(reorderButton, generateReceiptButton);
        }


        // Create a StackPane to hold the ScrollPane and the back button
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(scrollPane, backButton, fixedVBox);
        StackPane.setAlignment(backButton, Pos.TOP_LEFT);
        StackPane.setAlignment(fixedVBox, Pos.BOTTOM_CENTER);
        StackPane.setMargin(backButton, new Insets(10));

        Scene scene = new Scene(stackPane, 500, 780);
        scene.getStylesheets().add(getClass().getResource("/com/Group3/foodorderingsystem/Module/Ordering/Customer/OrderDetails.css").toExternalForm());

        primaryStage.setTitle("Order Details");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void displayOrderDetails(VBox root, OrderModel order) {
        OrderStatusUI statusUI = new OrderStatusUI(order);
        OrderVendorUI vendorUI = new OrderVendorUI(order);
        OrderItemsUI itemsUI = new OrderItemsUI(order);
        OrderPaymentUI paymentUI = new OrderPaymentUI(order);

        root.getChildren().addAll(
            vendorUI.getVendorDetails(),
            statusUI.getOrderDetails(),
            itemsUI.getItemsDetails(),
            paymentUI.getPaymentDetails()
        );
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class OrderVendorUI {
        private OrderModel order;

        public OrderVendorUI(OrderModel order) {
            this.order = order;
        }

        public VBox getVendorDetails() {
            VBox vendorBox = new VBox(10);
            vendorBox.setAlignment(Pos.CENTER);

            // Load and style the shop image
            ImageView shopImageView = loadShopImage("path/to/logo.png");
            shopImageView.setFitHeight(200); // Adjust size as necessary
            shopImageView.setFitWidth(450);



            Label vendorLabel = new Label(order.getVendor().getShopName());
            vendorLabel.getStyleClass().add("vendor-label");
            vendorLabel.setAlignment(Pos.CENTER); // Center the text within the Label
            vendorBox.getChildren().addAll(shopImageView, vendorLabel);
            return vendorBox;
        }

        private ImageView loadShopImage(String path) {
            Image image;
            try {
                image = new Image(path, 50, 50, true, true);
            } catch (Exception e) {
                image = new Image("file:C:/Users/jack8/OneDrive - Asia Pacific University/Documents/Assignment Diploma/Java/food-ordering-system/FoodOrderingSystem/src/main/java/com/Group3/foodorderingsystem/Assets/Resource/logo.png", 50, 50, true, true); // Default image if loading fails
            }
            return new ImageView(image);
        }
    }

    public static class OrderStatusUI {
        private OrderModel order;
    
        public OrderStatusUI(OrderModel order) {
            this.order = order;
        }
    
        public VBox getOrderDetails() {
            VBox orderBox = new VBox(10);
            orderBox.setPadding(new Insets(10, 0, 0, 0));
    
            //Order Status
            HBox orderStatusBox = new HBox(10);
            Label orderStatusLabel = new Label("Order Status:");
            orderStatusLabel.getStyleClass().add("order-status-label");
            Label orderStatusValueLabel = new Label(order.getStatus().toString());
            orderStatusValueLabel.getStyleClass().add("status-value-label");
            orderStatusBox.getChildren().addAll(orderStatusLabel, orderStatusValueLabel);

            // Order ID
            HBox orderIdBox = new HBox(10);
            Label orderIdLabel = new Label("Order ID:");
            orderIdLabel.getStyleClass().add("status-label");
            Label orderIdValueLabel = new Label(order.getOrderId());
            orderIdValueLabel.getStyleClass().add("status-value-label");
            orderIdBox.getChildren().addAll(orderIdLabel, orderIdValueLabel);
    
            // Order Time
            HBox orderTimeBox = new HBox(10);
            Label orderTimeLabel = new Label("Order Time:");
            orderTimeLabel.getStyleClass().add("status-label");
            Label orderTimeValueLabel = new Label(order.getTime().toString());
            orderTimeValueLabel.getStyleClass().add("status-value-label");
            orderTimeBox.getChildren().addAll(orderTimeLabel, orderTimeValueLabel);
    
            // Order Method
            HBox orderMethodBox = new HBox(10);
            Label orderMethodLabel = new Label("Order Method:");
            orderMethodLabel.getStyleClass().add("status-label");
            Label orderMethodValueLabel = new Label(order.getOrderMethod().toString());
            orderMethodValueLabel.getStyleClass().add("status-value-label");
            orderMethodBox.getChildren().addAll(orderMethodLabel, orderMethodValueLabel);


    
            orderBox.getChildren().addAll(orderStatusBox, orderIdBox, orderTimeBox, orderMethodBox);

            //if order is delivery, display delivery address
            if (order.getOrderMethod().equals(OrderMethodEnum.DELIVERY)) {
                VBox deliveryAddressBox = new VBox(10);
                Label deliveryAddressLabel = new Label("Delivery Address:");
                deliveryAddressLabel.getStyleClass().add("status-label");
                Label deliveryAddressValueLabel = new Label(order.getDeliveryAddress());
                deliveryAddressValueLabel.getStyleClass().add("delivery-address-label");
                Separator separator = new Separator();
                deliveryAddressBox.getChildren().addAll(separator, deliveryAddressLabel, deliveryAddressValueLabel);
                orderBox.getChildren().addAll(deliveryAddressBox);
            }
    
            return orderBox;
        }
    }

    public static class OrderItemsUI {
        private OrderModel order;
    
        public OrderItemsUI(OrderModel order) {
            this.order = order;
        }
    
        
        public VBox getItemsDetails() {
            VBox itemsBox = new VBox(10);
            itemsBox.setPadding(new Insets(10, 0, 0, 0));
            Separator separator = new Separator();
            separator.getStyleClass().add("separator");
            itemsBox.getChildren().addAll(separator);
    

            for (ItemModel item : order.getItems()) {
                HBox itemBox = new HBox(10);
                itemBox.setPadding(new Insets(5, 0, 5, 0));
                itemBox.setPrefWidth(450);
                itemBox.setMaxWidth(450);
    
                Label itemQuantityLabel = new Label(item.getItemQuantity() + "x");
                Label itemLabel = new Label(item.getItemName());
                itemQuantityLabel.getStyleClass().add("item-quantity-label");
                itemLabel.getStyleClass().add("item-label");
                HBox.setHgrow(itemLabel, Priority.ALWAYS);
    
                Label priceLabel = new Label("$" + String.format("%.2f", item.getItemPrice() * item.getItemQuantity()));
                priceLabel.getStyleClass().add("price-label");
    
                itemBox.getChildren().addAll(itemQuantityLabel, itemLabel, priceLabel);
                itemsBox.getChildren().addAll(itemBox);
            }
    
            return itemsBox;
        }
    }

    public static class OrderPaymentUI {
        private OrderModel order;
    
        public OrderPaymentUI(OrderModel order) {
            this.order = order;
        }
    
        public VBox getPaymentDetails() {
            // Check if the order method is delivery
            Double deliveryFee = 0.0;
            if (order.getOrderMethod().equals(OrderMethodEnum.DELIVERY)) {
                deliveryFee = 5.0;
            } else {
                deliveryFee = 0.0;
            }
    
            Separator separator1 = new Separator();
            separator1.getStyleClass().add("separator");


            Double subtotal = order.getTotalPrice() - deliveryFee;
            Double totalPrice = order.getTotalPrice();
            VBox paymentBox = new VBox(10);
            paymentBox.setPadding(new Insets(10, 0, 0, 0));
    
            // Subtotal
            HBox subtotalBox = new HBox(10);
            Label subtotalLabel = new Label("Subtotal:");
            subtotalLabel.getStyleClass().add("subtotal-label");
            Label subtotalAmountLabel = new Label("$" + String.format("%.2f", subtotal));
            subtotalAmountLabel.getStyleClass().add("subtotal-amount-label");
            subtotalBox.getChildren().addAll(subtotalLabel, subtotalAmountLabel);
    
            // Delivery Fee
            HBox deliveryFeeBox = new HBox(10);
            Label deliveryFeeLabel = new Label("Delivery Fee:");
            deliveryFeeLabel.getStyleClass().add("subtotal-label");
            Label deliveryFeeAmountLabel = new Label("$" + String.format("%.2f", deliveryFee));
            deliveryFeeAmountLabel.getStyleClass().add("subtotal-amount-label");
            deliveryFeeBox.getChildren().addAll(deliveryFeeLabel, deliveryFeeAmountLabel);
    
            // Total Price
            HBox totalPriceBox = new HBox(10);
            Label totalPriceLabel = new Label("Total Price:");
            totalPriceLabel.getStyleClass().add("total-price-label");
            Label totalPriceAmountLabel = new Label("$" + String.format("%.2f", totalPrice));
            totalPriceAmountLabel.getStyleClass().add("total-price-amount-label");
            totalPriceBox.getChildren().addAll(totalPriceLabel, totalPriceAmountLabel);

            //add 100px empty space
            Label emptySpace = new Label("");
            emptySpace.setPrefHeight(80);

    
            paymentBox.getChildren().addAll(separator1, subtotalBox, deliveryFeeBox, totalPriceBox, emptySpace);
    
            return paymentBox;
        }
    }
}