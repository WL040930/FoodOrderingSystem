package com.Group3.foodorderingsystem.Module.Platform.Customer.Order.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.OrderModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.OrderMethodEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;
import com.Group3.foodorderingsystem.Core.Services.CustomerOrderServices;
import com.Group3.foodorderingsystem.Core.Util.Images;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Separator;


public class OrderDetailsUI extends BorderPane {

    CustomerOrderServices customerOrderServices = new CustomerOrderServices();

    public OrderDetailsUI() {
        this.setStyle("-fx-background-color: #f8fafc;");

        // Get the selected order from session
        OrderModel selectedOrder = (OrderModel) SessionUtil.getSelectedOrderFromSession();
        System.out.println("Order retrieved from session: " + (selectedOrder != null ? selectedOrder.getOrderId() : "null"));

        VBox contentBox = new VBox(15);
        contentBox.setPadding(new Insets(20));
        contentBox.setStyle("-fx-background-color: #f8fafc;");

        if (selectedOrder != null) {
            displayOrderDetails(contentBox, selectedOrder);
        } else {
            Label noOrderLabel = new Label("No order selected.");
            noOrderLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666666;");
            contentBox.getChildren().add(noOrderLabel);
        }

        // Wrap the main content in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(contentBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #f8fafc; -fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Create a VBox for the "Cancel Order" button
        VBox fixedVBox = createBottomButtonContainer(selectedOrder);

        // Create a back button
        Button backButton = new Button();
        backButton.setStyle("-fx-background-color: transparent;");
        backButton.setGraphic(Images.getImageView("left_arrow.png", 20, 20));
        backButton.setOnAction(e -> {
            SessionUtil.setSelectedOrderInSession(null);
            // Navigate back to OrderHistoryUI
            CustomerViewModel.getOrderViewModel().navigate(CustomerViewModel.getOrderViewModel().getOrderHistoryUI("  Active"));
        });

        // Create a label for the shop name
        Label shopNameLabel = new Label(selectedOrder != null ? selectedOrder.getVendor().getShopName() : "Shop Name");
        shopNameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Create a VBox for the top section
        HBox topContainer = new HBox(10);
        topContainer.getChildren().addAll(backButton, shopNameLabel);
        topContainer.setAlignment(Pos.CENTER_LEFT);

        // Create a container for the back button and fixedVBox
        VBox bottomContainer = new VBox(10);
        bottomContainer.getChildren().addAll(fixedVBox);
        bottomContainer.setAlignment(Pos.CENTER);

        // Set the topContainer at the top, ScrollPane in the center, and bottomContainer at the bottom
        this.setTop(topContainer);
        this.setCenter(scrollPane);
        this.setBottom(bottomContainer);

        this.getStylesheets().add("/com/Group3/foodorderingsystem/Module/Platform/Customer/Order/ui/OrderDetailsUI.css");
    }

    private VBox createBottomButtonContainer(OrderModel selectedOrder) {
        // Button container fixed at the bottom
        VBox bottomContainer = new VBox(10);
        bottomContainer.setPadding(new Insets(10));
        bottomContainer.setAlignment(Pos.CENTER);

        // Decide button based on order status
        if (selectedOrder != null && selectedOrder.getStatus().equals(StatusEnum.PENDING)) {
            Button cancelOrderButton = new Button("Cancel Order");
            
            cancelOrderButton.setOnAction(e -> cancelOrderAction(selectedOrder));
            bottomContainer.getChildren().add(cancelOrderButton);
        } else {
            Button reorderButton = new Button("Reorder");
            Button generateReceiptButton = new Button("Generate Receipt");
            reorderButton.getStyleClass().add("reorder-button");
            generateReceiptButton.getStyleClass().add("generate-receipt-button");
            reorderButton.setOnAction(e -> {
                SessionUtil.setItemsInSession(selectedOrder.getItems());
                CustomerViewModel.getOrderViewModel().setOrderSummaryUI(new OrderSummaryUI());
                CustomerViewModel.getOrderViewModel().navigate(CustomerViewModel.getOrderViewModel().getOrderSummaryUI());
            });
            generateReceiptButton.setOnAction(e -> customerOrderServices.generateReceipt());
            bottomContainer.getChildren().addAll(reorderButton, generateReceiptButton);
        }

        return bottomContainer;
    }

    private void cancelOrderAction(OrderModel selectedOrder) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel this order?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION, "Order cancelled successfully.");
                infoAlert.showAndWait();
                // TODO:Logic to cancel the order
                CustomerViewModel.getOrderViewModel().navigate(CustomerViewModel.getOrderViewModel().getOrderHistoryUI());
            }
        });
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

            ImageView shopImageView = loadShopImage("path/to/logo.png");
            shopImageView.setFitHeight(200);
            shopImageView.setFitWidth(450);

            Label vendorLabel = new Label(order.getVendor().getShopName());
            vendorLabel.getStyleClass().add("vendor-label");
            vendorLabel.setAlignment(Pos.CENTER);
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

            HBox orderStatusBox = new HBox(10);
            Label orderStatusLabel = new Label("Order Status:");
            orderStatusLabel.getStyleClass().add("order-status-label");
            Label orderStatusValueLabel = new Label(order.getStatus().toString());
            orderStatusValueLabel.getStyleClass().add("status-value-label");
            orderStatusBox.getChildren().addAll(orderStatusLabel, orderStatusValueLabel);

            HBox orderIdBox = new HBox(10);
            Label orderIdLabel = new Label("Order ID:");
            orderIdLabel.getStyleClass().add("status-label");
            Label orderIdValueLabel = new Label(order.getOrderId());
            orderIdValueLabel.getStyleClass().add("status-value-label");
            orderIdBox.getChildren().addAll(orderIdLabel, orderIdValueLabel);

            HBox orderTimeBox = new HBox(10);
            Label orderTimeLabel = new Label("Order Time:");
            orderTimeLabel.getStyleClass().add("status-label");
            Label orderTimeValueLabel = new Label(order.getTime().toString());
            orderTimeValueLabel.getStyleClass().add("status-value-label");
            orderTimeBox.getChildren().addAll(orderTimeLabel, orderTimeValueLabel);

            HBox orderMethodBox = new HBox(10);
            Label orderMethodLabel = new Label("Order Method:");
            orderMethodLabel.getStyleClass().add("status-label");
            Label orderMethodValueLabel = new Label(order.getOrderMethod().toString());
            orderMethodValueLabel.getStyleClass().add("status-value-label");
            orderMethodBox.getChildren().addAll(orderMethodLabel, orderMethodValueLabel);

            orderBox.getChildren().addAll(orderStatusBox, orderIdBox, orderTimeBox, orderMethodBox);

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

            HBox subtotalBox = new HBox(10);
            Label subtotalLabel = new Label("Subtotal:");
            subtotalLabel.getStyleClass().add("subtotal-label");
            Label subtotalAmountLabel = new Label("$" + String.format("%.2f", subtotal));
            subtotalAmountLabel.getStyleClass().add("subtotal-amount-label");
            subtotalBox.getChildren().addAll(subtotalLabel, subtotalAmountLabel);

            HBox deliveryFeeBox = new HBox(10);
            Label deliveryFeeLabel = new Label("Delivery Fee:");
            deliveryFeeLabel.getStyleClass().add("subtotal-label");
            Label deliveryFeeAmountLabel = new Label("$" + String.format("%.2f", deliveryFee));
            deliveryFeeAmountLabel.getStyleClass().add("subtotal-amount-label");
            deliveryFeeBox.getChildren().addAll(deliveryFeeLabel, deliveryFeeAmountLabel);

            HBox totalPriceBox = new HBox(10);
            Label totalPriceLabel = new Label("Total Price:");
            totalPriceLabel.getStyleClass().add("total-price-label");
            Label totalPriceAmountLabel = new Label("$" + String.format("%.2f", totalPrice));
            totalPriceAmountLabel.getStyleClass().add("total-price-amount-label");
            totalPriceBox.getChildren().addAll(totalPriceLabel, totalPriceAmountLabel);


            paymentBox.getChildren().addAll(separator1, subtotalBox, deliveryFeeBox, totalPriceBox);

            return paymentBox;
        }
    }
}