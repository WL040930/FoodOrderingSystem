package com.Group3.foodorderingsystem.Module.Platform.Customer.Order.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.OrderModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ComplainModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Assets.CustomerNavigationEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.ComplainStatusEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.OrderMethodEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;
import com.Group3.foodorderingsystem.Core.Services.CustomerOrderServices;
import com.Group3.foodorderingsystem.Core.Services.TransactionServices;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;

public class OrderDetailsUI extends BorderPane {

    OrderModel selectedOrder = (OrderModel) SessionUtil.getSelectedOrderFromSession();
    VendorModel vendor = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.VENDOR), VendorModel.class,
            vendor -> vendor.getId().equals(selectedOrder.getVendor())).get(0);

    public OrderDetailsUI() {

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

        // Create a VBox for the "Cancel Order" or "reorder" button
        VBox fixedVBox = createBottomButtonContainer(selectedOrder);

        // Create a back button
        TitleBackButton backButton = new TitleBackButton(vendor.getShopName(), () -> {
                SessionUtil.setSelectedOrderInSession(null);
                // Navigate back to OrderHistoryUI
                CustomerViewModel.getOrderViewModel().navigate(CustomerViewModel.getOrderViewModel().getOrderHistoryUI());
            });

        // Create a VBox for the top section
        HBox topContainer = new HBox(10);
        topContainer.getChildren().addAll(backButton);
        topContainer.setAlignment(Pos.CENTER_LEFT);

        // Create a container for the back button and fixedVBox
        VBox bottomContainer = new VBox(10);
        bottomContainer.getChildren().addAll(fixedVBox);
        bottomContainer.setAlignment(Pos.CENTER);

        // Set the topContainer at the top, ScrollPane in the center, and
        // bottomContainer at the bottom
        this.setTop(topContainer);
        this.setCenter(scrollPane);
        this.setBottom(bottomContainer);

        this.getStylesheets()
                .add("/com/Group3/foodorderingsystem/Module/Platform/Customer/Order/ui/OrderDetailsUI.css");
    }

    private VBox createBottomButtonContainer(OrderModel selectedOrder) {
        // Button container fixed at the bottom
        VBox bottomContainer = new VBox(10);
        bottomContainer.setPadding(new Insets(10));
        bottomContainer.setAlignment(Pos.CENTER);

        // Decide button based on order status
        if (selectedOrder != null && selectedOrder.getStatus().equals(StatusEnum.PENDING)) {
            Button cancelOrderButton = new Button("Cancel Order");
            cancelOrderButton.getStyleClass().add("cancel-order-button");
            cancelOrderButton.setOnAction(e -> cancelOrderAction(selectedOrder));
            bottomContainer.getChildren().add(cancelOrderButton);
        } else if (selectedOrder != null && (selectedOrder.getStatus().equals(StatusEnum.CANCELLED) || selectedOrder.getStatus().equals(StatusEnum.REJECTED))) {
            Button reorderButton = new Button("Reorder");
            reorderButton.getStyleClass().add("reorder-button");

            // Set actions for the buttons
            reorderButton.setOnAction(e -> {
                SessionUtil.setItemsInSession(selectedOrder.getItems());
                SessionUtil.setOrderSummaryEntryInSession("Reorder");
                CustomerViewModel.getOrderViewModel().setOrderSummaryUI(new OrderSummaryUI());
                CustomerViewModel.getOrderViewModel().navigate(CustomerViewModel.getOrderViewModel().getOrderSummaryUI());
            });
            bottomContainer.getChildren().addAll(reorderButton);

        } else {
            Button reorderButton = new Button("Reorder");
            Button generateReceiptButton = new Button("Generate Receipt");
            reorderButton.getStyleClass().add("reorder-button");
            generateReceiptButton.getStyleClass().add("generate-receipt-button");

            // Set actions for the buttons
            reorderButton.setOnAction(e -> {
                SessionUtil.setItemsInSession(selectedOrder.getItems());
                SessionUtil.setOrderSummaryEntryInSession("Reorder");
                CustomerViewModel.getOrderViewModel().setOrderSummaryUI(new OrderSummaryUI());
                CustomerViewModel.getOrderViewModel()
                        .navigate(CustomerViewModel.getOrderViewModel().getOrderSummaryUI());
            });
            generateReceiptButton.setOnAction(e -> CustomerOrderServices.generateReceipt());
            bottomContainer.getChildren().addAll(reorderButton, generateReceiptButton);
        }

        return bottomContainer;
    }

    private void cancelOrderAction(OrderModel selectedOrder) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel this order?",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION, "Order cancelled successfully.");
                infoAlert.showAndWait();
                CustomerOrderServices.cancelOrder(selectedOrder.getOrderId());
                TransactionServices.createTransaction(selectedOrder.getOrderId(), TransactionModel.TransactionType.CANCEL, RoleEnum.CUSTOMER);

                
                //refresh view model
                CustomerViewModel.initTransactionViewModel();
                CustomerViewModel.initNotificationViewModel();


                CustomerViewModel.initOrderViewModel();
                CustomerViewModel.getCustomerMainFrame().handleNavigation(CustomerNavigationEnum.Order);
            }
        });
    }

    private void displayOrderDetails(VBox root, OrderModel order) {
        VBox statusUI = getOrderDetails();
        VBox vendorUI = getVendorDetails();
        VBox itemsUI = getItemsDetails();
        VBox paymentUI = getPaymentDetails();

        root.getChildren().add(vendorUI);

        Boolean isCompleted = order.getStatus().equals(StatusEnum.DELIVERED) || order.getStatus().equals(StatusEnum.SERVED) || order.getStatus().equals(StatusEnum.PICKED_UP);

        //check if the order is delivered, served or picked up, if yes, show rating section
        if (isCompleted)  {
            VBox ratingUI = getRatingUI();
            root.getChildren().add(ratingUI);
        }

        
        root.getChildren().addAll(statusUI, itemsUI, paymentUI);

        if (isCompleted) {
            VBox complainUI = getComplainUI();
            root.getChildren().add(complainUI);
        }
        

    }

    private VBox getVendorDetails() {
        VBox vendorBox = new VBox(10);
        vendorBox.setAlignment(Pos.CENTER);

        ImageView shopImageView = loadShopImage("path/to/logo.png");
        shopImageView.setFitHeight(200);
        shopImageView.setFitWidth(450);

        Label vendorLabel = new Label(vendor.getShopName());
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
            image = new Image("/com/Group3/foodorderingsystem/Assets/Resource/logo.png", 50, 50, true, true); // Default
                                                                                                              // image
                                                                                                              // if
                                                                                                              // loading
                                                                                                              // fails
        }
        return new ImageView(image);
    }

    private VBox getOrderDetails() {
        VBox orderBox = new VBox(10);
        orderBox.setPadding(new Insets(10, 0, 0, 0));

        // Order Details Label
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

        orderBox.getChildren().addAll(detailsLabel, orderStatusBox, orderIdBox, orderTimeBox, orderMethodBox);

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

    private VBox getItemsDetails() {
        VBox itemsBox = new VBox(10);
        itemsBox.setPadding(new Insets(10, 0, 0, 0));
        Separator separator = new Separator();

        // Order Details Label
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

    private VBox getPaymentDetails() {


        Separator separator1 = new Separator();
        separator1.getStyleClass().add("separator");

        Double subtotal = selectedOrder.getSubTotalPrice();
        Double totalPrice = selectedOrder.getTotalPrice();
        VBox paymentBox = new VBox(10);
        paymentBox.setPadding(new Insets(10, 0, 0, 0));

        HBox subtotalBox = new HBox(10);
        Label subtotalLabel = new Label("Subtotal:");
        subtotalLabel.getStyleClass().add("subtotal-label");
        Label subtotalAmountLabel = new Label("RM" + String.format("%.2f", subtotal));
        subtotalAmountLabel.getStyleClass().add("subtotal-amount-label");
        subtotalBox.getChildren().addAll(subtotalLabel, subtotalAmountLabel);

        HBox disocuntBox = new HBox(10);
        Label discountLabel = new Label("Discount:");
        discountLabel.getStyleClass().add("subtotal-label");
        Label discountAmountLabel = new Label("RM" + String.format("%.2f", (selectedOrder.getSubTotalPrice() * selectedOrder.getVoucherRate() / 100)));
        discountAmountLabel.getStyleClass().add("subtotal-amount-label");
        disocuntBox.getChildren().addAll(discountLabel, discountAmountLabel);


        HBox deliveryFeeBox = new HBox(10);
        Label deliveryFeeLabel = new Label("Delivery Fee:");
        deliveryFeeLabel.getStyleClass().add("subtotal-label");
        Label deliveryFeeAmountLabel = new Label("RM" + String.format("%.2f", selectedOrder.getDeliveryFee()));
        deliveryFeeAmountLabel.getStyleClass().add("subtotal-amount-label");
        deliveryFeeBox.getChildren().addAll(deliveryFeeLabel, deliveryFeeAmountLabel);

        HBox totalPriceBox = new HBox(10);
        Label totalPriceLabel = new Label("Total Price:");
        totalPriceLabel.getStyleClass().add("total-price-label");
        Label totalPriceAmountLabel = new Label("RM" + String.format("%.2f", totalPrice));
        totalPriceAmountLabel.getStyleClass().add("total-price-amount-label");
        totalPriceBox.getChildren().addAll(totalPriceLabel, totalPriceAmountLabel);

        Separator separator2 = new Separator();
        separator2.getStyleClass().add("separator");

        paymentBox.getChildren().addAll(separator1, subtotalBox, disocuntBox, deliveryFeeBox, totalPriceBox, separator2);

        return paymentBox;
    }

    private int ratingStars = 0;

    private VBox getRatingUI() {
        VBox ratingBox = new VBox(10);
        ratingBox.setPadding(new Insets(0)); // Adjust padding as needed

        // Title
        Label ratingLabel = new Label("Rate Your Experience");
        ratingLabel.getStyleClass().add("detail-title-label");
       

        // Stars for rating
        HBox starsBox = new HBox(5);
        ImageView[] stars = new ImageView[5];


        for (int i = 0; i < stars.length; i++) {
            ImageView star = Images.getImageView("star_empty.png", 30, 30);
            StackPane starPane = new StackPane(star);
            starPane.setStyle("-fx-background-color: transparent; -fx-padding: 5px;");
            int finalI = i;
            starPane.setOnMouseClicked(event -> {
                updateStars(stars, finalI);
                ratingStars = finalI + 1;
            });
            stars[i] = star;
            starsBox.getChildren().add(starPane);
        }


        // Text area for feedback
        TextArea feedbackArea = new TextArea();
        feedbackArea.setPromptText("Write your feedback here...");
        feedbackArea.setWrapText(true);

        // Check if there is existing rating and feedback
        if (selectedOrder.getRating() > 0) {
            updateStars(stars, selectedOrder.getRating() - 1); // Subtract 1 because rating index starts at 0
            feedbackArea.setText(selectedOrder.getReview());
        }

        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.getStyleClass().add("submit-button");
        submitButton.setOnAction(event -> {
            if (ratingStars == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please provide star rating.");
                alert.showAndWait();
                return;
            } else {
                submitRatingFeedback(ratingStars, feedbackArea.getText());
            }
        });

        //if the order is already rated, disable the submit button
        if (selectedOrder.getRating() > 0) {
            submitButton.setDisable(true);
            feedbackArea.setEditable(false);
        }

        Separator separator = new Separator();
        separator.getStyleClass().add("separator");

        ratingBox.getChildren().addAll(ratingLabel, starsBox, feedbackArea, submitButton, separator);
        return ratingBox;
    }

    private void updateStars(ImageView[] stars, int rating) {
        Image emptyStar = Images.getImageView("star_empty.png", 30, 30).getImage();
        Image filledStar = Images.getImageView("star_filled.png", 30, 30).getImage();
        for (int i = 0; i < stars.length; i++) {
            stars[i].setImage(i <= rating ? filledStar : emptyStar);
        }
    }
    
    private void submitRatingFeedback(int rating, String feedback) {

        // Show success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Rating submitted successfully.");
        alert.showAndWait();

        CustomerOrderServices.updateRatingAndFeedback(selectedOrder.getOrderId(), rating, feedback);
        CustomerViewModel.getOrderViewModel().setOrderHistoryUI(new OrderHistoryUI("Active"));
        CustomerViewModel.getOrderViewModel().navigate(CustomerViewModel.getOrderViewModel().getOrderHistoryUI("Past"));
        
    }

    private VBox getComplainUI() {
        VBox complainBox = new VBox(10);
        complainBox.setPadding(new Insets(0)); // Adjust padding as needed

        // Title
        Label complainLabel = new Label("Complain");
        complainLabel.getStyleClass().add("detail-title-label");
        complainLabel.setStyle("-fx-text-fill: #ff0000;");

        // Complain Description
        TextArea complainDescription = new TextArea();
        complainDescription.setPromptText("Write your complain here...");
        complainDescription.setWrapText(true);

        complainBox.getChildren().addAll(complainLabel, complainDescription);

        ComplainModel complain = CustomerOrderServices.getComplain(selectedOrder.getOrderId());
        
        
        if (complain != null) {
            complainDescription.setText(complain.getComplainDescription());
            complainDescription.setEditable(false);


            // Complain Reply
            VBox complainReplyBox = new VBox(10);
            Label complainReplyLabel = new Label("Complain Reply:");
            complainReplyLabel.getStyleClass().add("status-label");
            Label complainReplyValueLabel = new Label(complain.getComplainReply());
            complainReplyValueLabel.getStyleClass().add("delivery-address-label"); 
            complainReplyBox.getChildren().addAll(complainReplyLabel, complainReplyValueLabel);


            //if reply is null, set default text
            if (complain.getComplainReply() == null) {
                complainReplyValueLabel.setText("We are working on your complain. Please wait for our reply.");
            }

            complainBox.getChildren().addAll(complainReplyBox);



        } else{
            // Submit button
            Button submitButton = new Button("Submit");
            submitButton.getStyleClass().add("complain-button");
            submitButton.setOnAction(event -> {
                if (complainDescription.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please provide complain description.");
                    alert.showAndWait();
                    return;
                } else {
                    submitComplain(complainDescription.getText());
                }
            });

            complainBox.getChildren().add(submitButton);
        }


        Separator separator = new Separator();
        separator.getStyleClass().add("separator");

        complainBox.getChildren().addAll(separator);
        return complainBox;
    }

    private void submitComplain(String complainDescription) {
        // Show success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Complain submitted successfully.");
        alert.showAndWait();

        CustomerOrderServices.submitComplain(selectedOrder.getOrderId(), complainDescription);
        CustomerViewModel.getOrderViewModel().setOrderHistoryUI(new OrderHistoryUI("Active"));
        CustomerViewModel.getOrderViewModel().navigate(CustomerViewModel.getOrderViewModel().getOrderHistoryUI("Past"));
    }

} 