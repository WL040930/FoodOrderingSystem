package com.Group3.foodorderingsystem.Module.Platform.Customer.Order.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.OrderModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.OrderMethodEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;
import com.Group3.foodorderingsystem.Core.Services.CustomerOrderServices;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Separator;
import javafx.scene.layout.Region;

import java.util.Optional;

public class OrderDetailsUI extends VBox {

    CustomerOrderServices customerOrderServices = new CustomerOrderServices();

    public OrderDetailsUI() {
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #f8fafc;");

        //get the selected order from session
        OrderModel selectedOrder = (OrderModel) SessionUtil.getSelectedOrderFromSession();
        
        if (selectedOrder != null) {
            displayOrderDetails(this, selectedOrder);
        } else {
            Label noOrderLabel = new Label("No order selected.");
            noOrderLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666666;");
            this.getChildren().add(noOrderLabel);
        }


        // Create a back button
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(e -> {
            SessionUtil.setSelectedOrderInSession(null);
            // Navigate back to OrderHistoryUI
            CustomerViewModel.navigate(CustomerViewModel.getOrderViewModel().getOrderHistoryUI());
        });

        VBox bottomContainer = new VBox(10); // Container for bottom buttons
        bottomContainer.setAlignment(Pos.BOTTOM_CENTER);

        // Create a VBox for the "Cancel Order" button
        VBox fixedVBox = new VBox(10);
        fixedVBox.getStyleClass().add("fixed-vbox");

        // If order status is pending, display cancel order button, else create two buttons, reorder and generate receipt
        if (selectedOrder != null && selectedOrder.getStatus().equals(StatusEnum.PENDING)) {
            Button cancelOrderButton = new Button("Cancel Order");
            cancelOrderButton.getStyleClass().add("cancel-order-button");
            cancelOrderButton.setOnAction(e -> {
                // Create a confirmation alert
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Cancel Order");
                alert.setHeaderText("Are you sure you want to cancel this order?");

                // Show the alert and wait for the user's response
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Pop out a message to inform user that order is cancelled
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Order Cancelled");
                    alert2.setHeaderText(null);
                    alert2.setContentText("Order cancelled.");
                    alert2.showAndWait();

                    // TODO: Implement the logic after database is implemented
                    // Cancel the order
                    // customerOrderServices.cancelOrder(selectedOrder.getOrderId());

                    // Navigate back to OrderHistoryUI
                    SessionUtil.setSelectedOrderInSession(null);
                    CustomerViewModel.navigate(CustomerViewModel.getOrderViewModel().getOrderHistoryUI());
                }
            });

            fixedVBox.getChildren().add(cancelOrderButton);
        } else {
            Button reorderButton = new Button("Reorder");
            reorderButton.getStyleClass().add("reorder-button");
            //TODO: reorder button logic
            reorderButton.setOnAction(e -> {
                // Save items in order into session
                SessionUtil.setItemsInSession(selectedOrder.getItems());
                CustomerViewModel.navigate(CustomerViewModel.getOrderViewModel().getOrderSummaryUI());
            });

            Button generateReceiptButton = new Button("Generate Receipt");
            generateReceiptButton.getStyleClass().add("generate-receipt-button");
            generateReceiptButton.setOnAction(e -> {
                customerOrderServices.generateReceipt();
            });

            fixedVBox.getChildren().addAll(reorderButton, generateReceiptButton);
        }

        // Spacer to push everything else up
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        bottomContainer.getChildren().addAll(backButton, fixedVBox);
        this.getChildren().addAll(spacer, bottomContainer);

        this.getStylesheets().add("/com/Group3/foodorderingsystem/Module/Platform/Customer/Order/ui/OrderDetailsUI.css");
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
                image = new Image("/com/Group3/foodorderingsystem/Assets/Resource/logo.png", 50, 50, true, true); // Default image if loading fails
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

            // Order Status
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

            // If order is delivery, display delivery address
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

            // Add 100px empty space
            Label emptySpace = new Label("");
            emptySpace.setPrefHeight(70);

            paymentBox.getChildren().addAll(separator1, subtotalBox, deliveryFeeBox, totalPriceBox, emptySpace);

            return paymentBox;
        }
    }
}