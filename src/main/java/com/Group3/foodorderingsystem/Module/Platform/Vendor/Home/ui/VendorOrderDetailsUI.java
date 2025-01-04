package com.Group3.foodorderingsystem.Module.Platform.Vendor.Home.ui;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Order.OrderModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.OrderMethodEnum;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;
import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;
import com.Group3.foodorderingsystem.Core.Services.VendorOrderServices;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;



public class VendorOrderDetailsUI extends BorderPane {

    OrderModel selectedOrder = (OrderModel) SessionUtil.getSelectedOrderFromSession();
    VendorModel vendor = (VendorModel) SessionUtil.getVendorFromSession();

    //retrieve the customer details
    String customerID = selectedOrder.getCustomer();
    CustomerModel customer = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.CUSTOMER), CustomerModel.class, customer -> customer.getId().equals(customerID)).get(0);

    public VendorOrderDetailsUI() {

        this.setStyle("-fx-background-color: #f8fafc;");

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


        // Create a back button
        TitleBackButton backButton = new TitleBackButton("", () -> {
                SessionUtil.setSelectedOrderInSession(null);
                // Navigate back to OrderHistoryUI
                VendorViewModel.getHomeViewModel().navigate(VendorViewModel.getHomeViewModel().getVendorOrderListUI());
            });
 
        // Create a label for the shop name
        Label orderIDLabel = new Label(selectedOrder.getOrderMethod() + " - " + selectedOrder.getOrderId());
        orderIDLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Create a VBox for the top section
        HBox topContainer = new HBox(10);
        topContainer.getChildren().addAll(backButton, orderIDLabel);
        topContainer.setAlignment(Pos.CENTER_LEFT);



        // Set the topContainer at the top, ScrollPane in the center, and bottomContainer at the bottom
        this.setTop(topContainer);
        this.setCenter(scrollPane);

        this.getStylesheets().add(getClass().getResource("/com/Group3/foodorderingsystem/Module/Platform/Vendor/Home/ui/VendorOrderDetailsUI.css").toExternalForm());
    }

    private void displayOrderDetails(VBox root, OrderModel order) {

        // Create a container for the back button and fixedVBox
        VBox buttonVBox = createButtonContainer(selectedOrder);
        VBox buttonContainer = new VBox(10);
        buttonContainer.getChildren().addAll(buttonVBox);
        buttonContainer.setAlignment(Pos.CENTER);


        VBox statusUI = getOrderDetails();
        VBox customerUI = getCustomerDetails();
        VBox itemsUI = getItemsDetails();
        VBox paymentUI = getPaymentDetails();

        root.getChildren().addAll(buttonContainer, paymentUI, itemsUI, statusUI, customerUI);
    }


    public VBox getOrderDetails() {
        VBox orderBox = new VBox(10);
        orderBox.setPadding(new Insets(10, 0, 0, 0));
        Separator separator1 = new Separator();
        separator1.getStyleClass().add("separator");

        //add a title
        Label detailsLabel = new Label("Order Details");
        detailsLabel.getStyleClass().add("detail-title-label");
        HBox orderStatusBox = new HBox(10);
        Label orderStatusLabel = new Label("Order Status:");
        orderStatusLabel.getStyleClass().add("status-label");
        Label orderStatusValueLabel = new Label(selectedOrder.getStatus().toString());
        orderStatusValueLabel.getStyleClass().add("status-value-label");
        orderStatusBox.getChildren().addAll(orderStatusLabel, orderStatusValueLabel);

        HBox orderIdBox = new HBox(10);
        Label orderIdLabel = new Label("Order ID:");
        orderIdLabel.getStyleClass().add("status-label");
        Label orderIdValueLabel = new Label(selectedOrder.getOrderId());
        orderIdValueLabel.getStyleClass().add("status-value-label");
        orderIdBox.getChildren().addAll(orderIdLabel, orderIdValueLabel);

        // Convert Date to LocalDateTime
        LocalDateTime orderDateTime = Instant.ofEpochMilli(selectedOrder.getTime().getTime())
                                     .atZone(ZoneId.systemDefault())
                                     .toLocalDateTime();

        // Format LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm");
        String formattedDate = orderDateTime.format(formatter);


        HBox orderTimeBox = new HBox(10);
        Label orderTimeLabel = new Label("Order Time:");
        orderTimeLabel.getStyleClass().add("status-label");
        Label orderTimeValueLabel = new Label(formattedDate);
        orderTimeValueLabel.getStyleClass().add("status-value-label");
        orderTimeBox.getChildren().addAll(orderTimeLabel, orderTimeValueLabel);

        HBox orderMethodBox = new HBox(10);
        Label orderMethodLabel = new Label("Order Method:");
        orderMethodLabel.getStyleClass().add("status-label");
        Label orderMethodValueLabel = new Label(selectedOrder.getOrderMethod().toString());
        orderMethodValueLabel.getStyleClass().add("status-value-label");
        orderMethodBox.getChildren().addAll(orderMethodLabel, orderMethodValueLabel);

        orderBox.getChildren().addAll(separator1, detailsLabel, orderStatusBox, orderIdBox, orderTimeBox, orderMethodBox);

        if (selectedOrder.getOrderMethod().equals(OrderMethodEnum.DELIVERY)) {
            VBox deliveryAddressBox = new VBox(10);
            Label deliveryAddressLabel = new Label("Delivery Address:");
            deliveryAddressLabel.getStyleClass().add("status-label");
            Label deliveryAddressValueLabel = new Label(selectedOrder.getDeliveryAddress());
            deliveryAddressValueLabel.getStyleClass().add("delivery-address-label");
            Separator separator = new Separator();
            deliveryAddressBox.getChildren().addAll(separator, deliveryAddressLabel, deliveryAddressValueLabel);
            orderBox.getChildren().addAll(deliveryAddressBox);
        }

        return orderBox;
    }

    public VBox getCustomerDetails() {
        VBox customerBox = new VBox(10);
        customerBox.setPadding(new Insets(10, 0, 0, 0));

        Separator separator = new Separator();
        separator.getStyleClass().add("separator");
        //add a title
        Label detailsLabel = new Label("Customer Details");
        detailsLabel.getStyleClass().add("detail-title-label");
        
        HBox customerNamesBox = new HBox(10);
        Label customerNameLabel = new Label("Customer Name:");
        customerNameLabel.getStyleClass().add("status-label");
        Label customerNameValueLabel = new Label(customer.getName());
        customerNameValueLabel.getStyleClass().add("status-value-label");
        customerNamesBox.getChildren().addAll(customerNameLabel, customerNameValueLabel);

        HBox customerPhoneBox = new HBox(10);
        Label customerPhoneLabel = new Label("Customer PhoneNumber:");
        customerPhoneLabel.getStyleClass().add("status-label");
        Label customerPhoneValueLabel = new Label(customer.getPhoneNumber());
        customerPhoneValueLabel.getStyleClass().add("status-value-label");
        customerPhoneBox.getChildren().addAll(customerPhoneLabel, customerPhoneValueLabel);

        HBox customerEmailBox = new HBox(10);
        Label customerEmailLabel = new Label("Customer Email:");
        customerEmailLabel.getStyleClass().add("status-label");
        Label customerEmailValueLabel = new Label(customer.getEmail());
        customerEmailValueLabel.getStyleClass().add("status-value-label");
        customerEmailBox.getChildren().addAll(customerEmailLabel, customerEmailValueLabel);

        customerBox.getChildren().addAll(separator, detailsLabel, customerNamesBox, customerPhoneBox, customerEmailBox);

        return customerBox;
    }



    public VBox getItemsDetails() {
        VBox itemsBox = new VBox(10);
        itemsBox.setPadding(new Insets(10, 0, 0, 0));
        Separator separator = new Separator();

        //add a title
        Label detailsLabel = new Label("Items Details");
        detailsLabel.getStyleClass().add("detail-title-label");

        separator.getStyleClass().add("separator");
        itemsBox.getChildren().addAll(separator, detailsLabel);

        for (ItemModel item : selectedOrder.getItems()) {
            HBox itemBox = new HBox(10);
            itemBox.setPadding(new Insets(5, 0, 5, 0));
            itemBox.setPrefWidth(450);
            itemBox.setMaxWidth(450);

            Label itemQuantityLabel = new Label(item.getItemQuantity() + "x");
            Label itemLabel = new Label(item.getItemName());
            itemQuantityLabel.getStyleClass().add("item-quantity-label");
            itemLabel.getStyleClass().add("item-label");
            HBox.setHgrow(itemLabel, Priority.ALWAYS);

            Label priceLabel = new Label("RM" + String.format("%.2f", item.getItemPrice() * item.getItemQuantity()));
            priceLabel.getStyleClass().add("price-label");

            itemBox.getChildren().addAll(itemQuantityLabel, itemLabel, priceLabel);
            itemsBox.getChildren().addAll(itemBox);
        }

        return itemsBox;
    }

    public VBox getPaymentDetails() {
        Double deliveryFee = 0.0;
        if (selectedOrder.getOrderMethod().equals(OrderMethodEnum.DELIVERY)) {
            deliveryFee = 5.0;
        } else {
            deliveryFee = 0.0;
        }

        Separator separator1 = new Separator();
        separator1.getStyleClass().add("separator");

        Separator separator2 = new Separator();
        separator2.getStyleClass().add("separator");

        Double subtotal = selectedOrder.getTotalPrice() - deliveryFee;
        Double totalPrice = selectedOrder.getTotalPrice();
        VBox paymentBox = new VBox(10);
        paymentBox.setPadding(new Insets(10, 0, 0, 0));


        VBox totalBox = new VBox(10);
        totalBox.setAlignment(Pos.CENTER);
        totalBox.setPadding(new Insets(0, 20, 0, 0));
        Label totelLabel = new Label("Total Price:");
        totelLabel.getStyleClass().add("total-price-label");
        Label total = new Label("RM" + String.format("%.2f", totalPrice));
        total.getStyleClass().add("total-price-amount-label");
        totalBox.getChildren().addAll(totelLabel, total);
        

        HBox subtotalBox = new HBox(10);
        Label subtotalLabel = new Label("Subtotal:");
        subtotalLabel.getStyleClass().add("subtotal-label");
        Label subtotalAmountLabel = new Label("RM" + String.format("%.2f", subtotal));
        subtotalAmountLabel.getStyleClass().add("subtotal-amount-label");
        subtotalBox.getChildren().addAll(subtotalLabel, subtotalAmountLabel);

        HBox deliveryFeeBox = new HBox(10);
        Label deliveryFeeLabel = new Label("Delivery Fee:");
        deliveryFeeLabel.getStyleClass().add("subtotal-label");
        Label deliveryFeeAmountLabel = new Label("RM" + String.format("%.2f", deliveryFee));
        deliveryFeeAmountLabel.getStyleClass().add("subtotal-amount-label");
        deliveryFeeBox.getChildren().addAll(deliveryFeeLabel, deliveryFeeAmountLabel);


        paymentBox.getChildren().addAll(separator1, totalBox, separator2, subtotalBox, deliveryFeeBox);

        return paymentBox;
    }

    private VBox createButtonContainer(OrderModel selectedOrder) {

        VBox firstcontainer = new VBox(10);

        // Button container fixed at the bottom
        VBox buttonContainer = new VBox(10);
        buttonContainer.setPadding(new Insets(0, 20, 0, 0));
        buttonContainer.setAlignment(Pos.CENTER);


        // Decide button based on order status
        if (selectedOrder != null && selectedOrder.getStatus().equals(StatusEnum.PENDING)) {

            ImageView imageView = Images.getImageView("pending_order.png", 150, 150);
            VBox.setMargin(imageView, new Insets(0, 0, 20, 0));


            Button rejectOrderButton = new Button("Reject");
            rejectOrderButton.getStyleClass().add("reject-order-button");
            rejectOrderButton.setOnAction(e -> buttonAction(selectedOrder, "Reject"));

            Button acceptOrdButton = new Button("Accept");
            acceptOrdButton.getStyleClass().add("accept-order-button");
            acceptOrdButton.setOnAction(e -> buttonAction(selectedOrder, "Accept"));
            
            buttonContainer.getChildren().addAll(imageView, rejectOrderButton, acceptOrdButton);
            firstcontainer.getChildren().add(buttonContainer);

        } else if (selectedOrder != null && selectedOrder.getStatus().equals(StatusEnum.PREPARING)) {

            ImageView imageView = Images.getImageView("preparing_order.png", 150, 150);
            VBox.setMargin(imageView, new Insets(0, 0, 20, 0));

            Button readyButton = new Button("Press when order is ready");
            readyButton.getStyleClass().add("reorder-button");
            readyButton.setOnAction(e -> buttonAction(selectedOrder, "Ready"));
            

            // Set actions for the buttons
            buttonContainer.getChildren().addAll(imageView, readyButton);
            firstcontainer.getChildren().add(buttonContainer);

        } else if (selectedOrder != null && selectedOrder.getStatus().equals(StatusEnum.READY_FOR_PICKUP)) {

            ImageView imageView = Images.getImageView("ready_for_pick_up_icon.png", 150, 150);
            VBox.setMargin(imageView, new Insets(0, 0, 20, 0));

            Button pickUpButton = new Button("Press when order is picked up");
            pickUpButton.getStyleClass().add("reorder-button");
            pickUpButton.setOnAction(e -> buttonAction(selectedOrder, "ReadyForPickup"));
            

            // Set actions for the buttons
            buttonContainer.getChildren().addAll(imageView, pickUpButton);
            firstcontainer.getChildren().add(buttonContainer);

        } else if (selectedOrder != null && (selectedOrder.getStatus().equals(StatusEnum.WAITING_FOR_RIDER) || selectedOrder.getStatus().equals(StatusEnum.DELIVERING))) {

            ImageView imageView = Images.getImageView("food_delivering.png", 150, 150);
            VBox.setMargin(imageView, new Insets(0, 0, 20, 0));

            // Set actions for the buttons
            buttonContainer.getChildren().addAll(imageView);
            firstcontainer.getChildren().add(buttonContainer);

        } else {
            ImageView imageView = Images.getImageView("order_completion.png", 150, 150);
            VBox.setMargin(imageView, new Insets(0, 0, 20, 0));

            Separator separator = new Separator();
            separator.getStyleClass().add("separator");


            //add a title
            Label reviewTitleLabel = new Label("Customer Rating");
            reviewTitleLabel.getStyleClass().add("detail-title-label");
            VBox reviewBox = new VBox(10);

            // Section for customer review
            if (selectedOrder.getRating() == 0) {

                Label message = new Label("Customer has not provided a review yet.");
                message.getStyleClass().add("status-label");

                reviewBox.getChildren().addAll(message);
                

            } else {

                HBox starsBox = new HBox(5);
                ImageView[] stars = new ImageView[5];

                for (int i = 0; i < 5; i++) {
                    if (i < selectedOrder.getRating()) {
                        stars[i] = Images.getImageView("star_filled.png", 30, 30);
                    } else {
                        stars[i] = Images.getImageView("star_empty.png", 30, 30);
                    }
                    starsBox.getChildren().add(stars[i]);
                }

                Label reviewLabel = new Label("Review : ");
                reviewLabel.getStyleClass().add("status-label");

                TextArea feedbackArea = new TextArea();
                feedbackArea.setWrapText(true);
                feedbackArea.setEditable(false);
                feedbackArea.setText(selectedOrder.getReview());

                if (selectedOrder.getReview().isEmpty()) {
                    feedbackArea.setText("No review provided.");
                }

                reviewBox.getChildren().addAll(starsBox, reviewLabel, feedbackArea);
            }

            buttonContainer.getChildren().addAll(imageView);
            firstcontainer.getChildren().addAll(buttonContainer, separator, reviewTitleLabel, reviewBox);
        }

        return firstcontainer;
    }

    private void buttonAction(OrderModel selectedOrder, String Reply) {

        String message;
        StatusEnum status;
        boolean findRider = false;

        if (Reply.equals("Reject")) {
            message = "Are you sure you want to reject this order?";
            status = StatusEnum.REJECTED;
        } else if (Reply.equals("Accept")) {
            message = "Are you sure you want to accept this order?";
            status = StatusEnum.PREPARING;

            if (selectedOrder.getOrderMethod().equals(OrderMethodEnum.DELIVERY)) {
                findRider = true;
            }

        } else if (Reply.equals("Ready")) {
            message = "Is the order done?";
            if (selectedOrder.getOrderMethod().equals(OrderMethodEnum.DELIVERY)) {
                status = StatusEnum.WAITING_FOR_RIDER;
            } else if (selectedOrder.getOrderMethod().equals(OrderMethodEnum.DINE_IN)) {
                status = StatusEnum.SERVED;
            } else {
                status = StatusEnum.READY_FOR_PICKUP;
            }
        } else {
            message = "Has the order been picked up?";
            status = StatusEnum.PICKED_UP;
        }

        boolean finalFindRider = findRider;


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                VendorOrderServices.updateOrderStatus(selectedOrder, status);

                VendorViewModel.getHomeViewModel().setVendorOrderListUI(new VendorOrderListUI());
                VendorViewModel.getHomeViewModel().navigate(VendorViewModel.getHomeViewModel().getVendorOrderListUI());
            }

            if (finalFindRider) {
                //TODO: Find a rider
                return;
            }
            
        });
    }

}
