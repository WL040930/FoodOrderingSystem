package com.Group3.foodorderingsystem.Module.Platform.Manager.Complain.ui;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ComplainModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Order.OrderModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.ComplainStatusEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.OrderMethodEnum;
import com.Group3.foodorderingsystem.Core.Services.CustomerOrderServices;
import com.Group3.foodorderingsystem.Core.Services.ManagerComplainServices;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Manager.ManagerViewModel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert;


public class ComplainDetailsUI extends BorderPane{
    
    OrderModel selectedOrder = (OrderModel) SessionUtil.getSelectedOrderFromSession();
    VendorModel vendor = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.VENDOR), VendorModel.class,
            vendor -> vendor.getId().equals(selectedOrder.getVendor())).get(0);

    public ComplainDetailsUI() {

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
                // Navigate back 
                ManagerViewModel.getComplainViewModel().navigate(ManagerViewModel.getComplainViewModel().getComplainListUI());
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
                .add("/com/Group3/foodorderingsystem/Module/Platform/Manager/Complain/ui/ComplainDetailsUI.css");
    }

    private VBox createBottomButtonContainer(OrderModel selectedOrder) {
        // Button container fixed at the bottom
        VBox bottomContainer = new VBox(10);
        bottomContainer.setPadding(new Insets(10));
        bottomContainer.setAlignment(Pos.CENTER);

        ComplainModel complain = CustomerOrderServices.getComplain(selectedOrder.getOrderId());

        // if the complain status is pending, Create two button, one is mark as resolved and the other is fine vendor
        if (complain.getComplainStatus() == ComplainStatusEnum.PENDING) {
            Button markAsResolvedButton = new Button("Mark as Resolved");
            markAsResolvedButton.getStyleClass().add("mark-as-resolved-button");
            markAsResolvedButton.setOnAction(e -> {
                ManagerComplainServices.resolveComplain(selectedOrder.getOrderId());
                ManagerViewModel.initComplainViewModel();
                ManagerViewModel.getComplainViewModel().navigate(ManagerViewModel.getComplainViewModel().getComplainListUI());
            });

            Button fineVendorButton = new Button("Fine Vendor");
            fineVendorButton.getStyleClass().add("fine-vendor-button");
            fineVendorButton.setOnAction(e -> {
                // Create an input dialog for the fine amount
                TextInputDialog fineDialog = new TextInputDialog();
                fineDialog.setTitle("Fine Vendor");
                fineDialog.setHeaderText("Enter the fine amount for the vendor:");
                fineDialog.setContentText("Amount (RM):");
            
                // Show dialog and capture user input
                Optional<String> result = fineDialog.showAndWait();
                result.ifPresent(amount -> {
                    try {
                        double fineAmount = Double.parseDouble(amount);
                        if (fineAmount <= 0) {
                            showAlert("Invalid Amount", "Fine amount must be greater than 0.");
                            return;
                        }
            
                        //TODO: Apply the fine to the vendor
                        ManagerComplainServices.fineVendor(selectedOrder.getOrderId(), fineAmount);
            
                        // Show success message
                        showAlert("Fine Applied", "Vendor has been fined RM" + fineAmount);
                    } catch (NumberFormatException ex) {
                        showAlert("Invalid Input", "Please enter a valid number.");
                    }
                });
            });

            bottomContainer.getChildren().addAll(markAsResolvedButton, fineVendorButton);
        } 
        

        return bottomContainer;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void displayOrderDetails(VBox root, OrderModel order) {

        VBox vendorUI = getVendorDetails();
        VBox complainUI = getComplainUI();
        VBox statusUI = getOrderDetails();
        VBox itemsUI = getItemsDetails();
        VBox paymentUI = getPaymentDetails();

        root.getChildren().addAll(vendorUI, complainUI, statusUI, itemsUI, paymentUI);  

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
            image = new Image("/com/Group3/foodorderingsystem/Assets/Resource/logo.png", 50, 50, true, true);                                                                                                                                                                                                                    // fails
        }
        return new ImageView(image);
    }

    private VBox getComplainUI() {
        VBox complainBox = new VBox(10);
        complainBox.setPadding(new Insets(10, 0, 0, 0));
        Separator separator = new Separator();

        //add a title
        Label detailsLabel = new Label("Complain Details");
        detailsLabel.getStyleClass().add("detail-title-label");
        detailsLabel.setStyle("-fx-text-fill: #ff0000;");

        separator.getStyleClass().add("separator");
        complainBox.getChildren().addAll(separator, detailsLabel);

        ComplainModel complain = CustomerOrderServices.getComplain(selectedOrder.getOrderId());

        TextArea complainDescription = new TextArea();
        complainDescription.setText(complain.getComplainDescription());
        complainDescription.setWrapText(true);
        complainDescription.setEditable(false);

        //Complain Status
        HBox complainStatusBox = new HBox(10);
        Label complainStatusLabel = new Label("Complain Status:");
        complainStatusLabel.getStyleClass().add("status-label");
        Label complainStatusValueLabel = new Label(complain.getComplainStatus().toString());
        complainStatusValueLabel.getStyleClass().add("status-value-label");
        complainStatusBox.getChildren().addAll(complainStatusLabel, complainStatusValueLabel);

        //Complain Reply
        VBox complainReplyBox = new VBox(10);
        Label complainReplyLabel = new Label("Complain Reply:");
        complainReplyLabel.getStyleClass().add("status-label");
        TextArea complainReplyValueArea = new TextArea();
        complainReplyValueArea.setText(complain.getComplainReply());

        Button submitButton = new Button("Submit");
        submitButton.getStyleClass().add("submit-button");
        submitButton.setOnAction(e -> {
            ManagerComplainServices.addReply(selectedOrder.getOrderId(), complainReplyValueArea.getText());
            // pop up a message for confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reply Submitted");
            alert.setHeaderText(null);
            alert.setContentText("Your reply has been submitted successfully.");
            alert.showAndWait();

            // refresh the complain list
            ManagerViewModel.initComplainViewModel();
        });
        
        complainReplyBox.getChildren().addAll(complainReplyLabel, complainReplyValueArea, submitButton);



        complainBox.getChildren().addAll(complainDescription, complainReplyBox, complainStatusBox);

        return complainBox;
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
}
