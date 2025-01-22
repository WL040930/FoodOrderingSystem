package com.Group3.foodorderingsystem.Module.Platform.Runner.Home.ui;

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
import com.Group3.foodorderingsystem.Core.Util.FileUtil;
import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Runner.RunnerViewModel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class RunnerViewDetailsUI extends BorderPane {
    
    OrderModel selectedOrder = (OrderModel) SessionUtil.getSelectedOrderFromSession();
    VendorModel vendor = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.VENDOR), VendorModel.class, vendor -> vendor.getId().equals(selectedOrder.getVendor())).get(0);
    CustomerModel customer = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.CUSTOMER), CustomerModel.class, customer -> customer.getId().equals(selectedOrder.getCustomer())).get(0);

    public RunnerViewDetailsUI() {

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
        TitleBackButton backButton = new TitleBackButton(vendor.getShopName(), () -> {
                SessionUtil.setSelectedOrderInSession(null);
                // Navigate back to the home page
                RunnerViewModel.getHomeViewModel().navigate(RunnerViewModel.getHomeViewModel().getRunnerHomeUI());
            });


        // Create a VBox for the top section
        HBox topContainer = new HBox(10);
        topContainer.getChildren().addAll(backButton);
        topContainer.setAlignment(Pos.CENTER_LEFT);

        // Set the topContainer at the top, ScrollPane in the center, and
        // bottomContainer at the bottom
        this.setTop(topContainer);
        this.setCenter(scrollPane);

        this.getStylesheets().add(getClass().getResource("/com/Group3/foodorderingsystem/Module/Platform/Runner/Home/ui/RunnerViewDetailsUI.css").toExternalForm());
    }


    private void displayOrderDetails(VBox root, OrderModel order) {

        // Create a container for the back button and fixedVBox
        VBox buttonVBox = createIconReview(selectedOrder);
        VBox buttonContainer = new VBox(10);
        buttonContainer.getChildren().addAll(buttonVBox);
        buttonContainer.setAlignment(Pos.CENTER);

        VBox paymentUI = getPaymentDetails();
        VBox itemsUI = getItemsDetails();
        VBox statusUI = getOrderDetails();
        VBox customerUI = getCustomerDetails();
        VBox vendorUI = getVendorDetails();



        root.getChildren().addAll(buttonContainer, paymentUI, itemsUI, statusUI, customerUI, vendorUI);
    }

    private VBox createIconReview(OrderModel selectedOrder) {

        VBox firstcontainer = new VBox(10);

        VBox imageContainer = new VBox(10);
        imageContainer.setPadding(new Insets(0, 20, 0, 0));
        imageContainer.setAlignment(Pos.CENTER);

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

        imageContainer.getChildren().addAll(imageView);
        firstcontainer.getChildren().addAll(imageContainer, separator, reviewTitleLabel, reviewBox);
    

        return firstcontainer;
    }

    private VBox getPaymentDetails() {


        Separator separator1 = new Separator();
        separator1.getStyleClass().add("separator");

        Separator separator2 = new Separator();
        separator2.getStyleClass().add("separator");

        Double subtotal = selectedOrder.getSubTotalPrice();
        Double deliveryFee = selectedOrder.getDeliveryFee();
        Double voucherRate = selectedOrder.getVoucherRate() / 100;
        VBox paymentBox = new VBox(10);
        paymentBox.setPadding(new Insets(10, 0, 0, 0));

        // Total Earn
        VBox totalBox = new VBox(10);
        totalBox.setAlignment(Pos.CENTER);
        totalBox.setPadding(new Insets(0, 20, 0, 0));
        Label totelLabel = new Label("Total Earn:");
        totelLabel.getStyleClass().add("total-price-label");
        Label total = new Label("RM" + String.format("%.2f", deliveryFee));
        total.getStyleClass().add("total-price-amount-label");
        totalBox.getChildren().addAll(totelLabel, total);
        

        // Subtotal
        HBox subtotalBox = new HBox(10);
        Label subtotalLabel = new Label("Subtotal:");
        subtotalLabel.getStyleClass().add("subtotal-label");
        Label subtotalAmountLabel = new Label("RM" + String.format("%.2f", subtotal));
        subtotalAmountLabel.getStyleClass().add("subtotal-amount-label");
        subtotalBox.getChildren().addAll(subtotalLabel, subtotalAmountLabel);

        // voucher discount
        HBox voucherBox = new HBox(10);
        Label voucherLabel = new Label("Voucher Discount " + voucherRate + ":");
        voucherLabel.getStyleClass().add("subtotal-label");
        Label voucherAmountLabel = new Label("RM" + String.format("%.2f", subtotal * voucherRate));
        voucherAmountLabel.getStyleClass().add("subtotal-amount-label");
        voucherBox.getChildren().addAll(voucherLabel, voucherAmountLabel);


        // Delivery Fee
        HBox deliveryFeeBox = new HBox(10);
        Label deliveryFeeLabel = new Label("Delivery Fee:");
        deliveryFeeLabel.getStyleClass().add("subtotal-label");
        Label deliveryFeeAmountLabel = new Label("RM" + String.format("%.2f", selectedOrder.getDeliveryFee()));
        deliveryFeeAmountLabel.getStyleClass().add("subtotal-amount-label");
        deliveryFeeBox.getChildren().addAll(deliveryFeeLabel, deliveryFeeAmountLabel);

        // Order Total
        HBox orderTotalBox = new HBox(10);
        Label orderTotalLabel = new Label("Order Total:");
        orderTotalLabel.getStyleClass().add("subtotal-label");
        Label orderTotalAmountLabel = new Label("RM" + String.format("%.2f", selectedOrder.getTotalPrice()));
        orderTotalAmountLabel.getStyleClass().add("subtotal-amount-label");
        orderTotalBox.getChildren().addAll(orderTotalLabel, orderTotalAmountLabel);


        paymentBox.getChildren().addAll(separator1, totalBox, separator2, subtotalBox, deliveryFeeBox, orderTotalBox);

        return paymentBox;
    }

    private VBox getItemsDetails() {
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

    private VBox getOrderDetails() {
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

    private VBox getCustomerDetails() {
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

    private VBox getVendorDetails() {
        VBox vendorBox = new VBox(10);
        vendorBox.setPadding(new Insets(10, 0, 0, 0));

        Separator separator = new Separator();
        separator.getStyleClass().add("separator");
        //add a title
        Label detailsLabel = new Label("Vendor Details");
        detailsLabel.getStyleClass().add("detail-title-label");
        
        // Vendor Details
        HBox vendorNamesBox = new HBox(10);
        Label vendorNameLabel = new Label("Vendor Name:");
        vendorNameLabel.getStyleClass().add("status-label");
        Label vendorNameValueLabel = new Label(vendor.getShopName());
        vendorNameValueLabel.getStyleClass().add("status-value-label");
        vendorNamesBox.getChildren().addAll(vendorNameLabel, vendorNameValueLabel);


        // Vendor Phone
        HBox vendorPhoneBox = new HBox(10);
        Label vendorPhoneLabel = new Label("Vendor PhoneNumber:");
        vendorPhoneLabel.getStyleClass().add("status-label");
        Label vendorPhoneValueLabel = new Label(vendor.getShopPhoneNumber());
        vendorPhoneValueLabel.getStyleClass().add("status-value-label");
        vendorPhoneBox.getChildren().addAll(vendorPhoneLabel, vendorPhoneValueLabel);


        // Vendor Email
        HBox vendorEmailBox = new HBox(10);
        Label vendorEmailLabel = new Label("Vendor Email:");
        vendorEmailLabel.getStyleClass().add("status-label");
        Label vendorEmailValueLabel = new Label(vendor.getEmail());
        vendorEmailValueLabel.getStyleClass().add("status-value-label");
        vendorEmailBox.getChildren().addAll(vendorEmailLabel, vendorEmailValueLabel);

        vendorBox.getChildren().addAll(separator, detailsLabel, vendorNamesBox, vendorPhoneBox, vendorEmailBox);

        return vendorBox;
    }


}
