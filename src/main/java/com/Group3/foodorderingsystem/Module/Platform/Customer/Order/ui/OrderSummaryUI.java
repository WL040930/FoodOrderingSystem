package com.Group3.foodorderingsystem.Module.Platform.Customer.Order.ui;


import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.OrderMethodEnum;
import com.Group3.foodorderingsystem.Core.Services.CustomerOrderServices;
import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;

public class OrderSummaryUI extends VBox {

    List<ItemModel> items = SessionUtil.getItemsFromSession();
    CustomerModel customer = SessionUtil.getCustomerFromSession();

    Double totalPrice;

    public OrderSummaryUI() {
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #f8fafc;");

        VBox mainVBox = new VBox(10);



        if (!items.isEmpty()) {
            mainVBox.getChildren().add(displayOrderSummary());
        } else {
            Label noItemsLabel = new Label("No items in the order.");
            noItemsLabel.getStyleClass().add("no-items-label");
            this.getChildren().add(noItemsLabel);
        }

        // Create a top fixed HBox (header)
        HBox topFixedHBox = createTopFixedHBox();

        // Create a bottom fixed VBox (footer)
        VBox bottomFixedVBox = createBottomFixedVBox(items);

        this.getChildren().addAll(topFixedHBox, mainVBox, bottomFixedVBox);

        // Apply CSS
        this.getStylesheets().add(getClass().getResource("/com/Group3/foodorderingsystem/Module/Platform/Customer/Order/ui/OrderSummaryUI.css").toExternalForm());
    }

    private VBox displayOrderSummary() {

        VBox itemsList = getItemsDetails();

        // Create order option section
        VBox orderOptionSection = createOrderOptionSection();

        return new VBox(itemsList, orderOptionSection);
    }

    private HBox createTopFixedHBox() {
        HBox topFixedHBox = new HBox(10);
        topFixedHBox.getStyleClass().add("top-fixed-hbox");

        // Create a back button with an icon
        Button backButton = new Button();
        backButton.setGraphic(Images.getImageView("left_arrow.png", 20, 20));
        backButton.getStyleClass().add("back-button");
        //TODO: back button action if enter from cart
        backButton.setOnAction(e -> {
            String entryPoint = SessionUtil.getOrderSummaryEntryFromSession();
            if (entryPoint == "Reorder") {
                CustomerViewModel.getOrderViewModel().navigate(CustomerViewModel.getOrderViewModel().getOrderDetailsUI());
                SessionUtil.setOrderSummaryEntryInSession(null);
            }
        });

        // Create a shop name label, retrieve the shop name from the item list in the session
        Label shopNameLabel = new Label("Shop Name");
        shopNameLabel.getStyleClass().add("shop-name-label");

        topFixedHBox.getChildren().addAll(backButton, shopNameLabel);
        return topFixedHBox;
    }

    private VBox createBottomFixedVBox(List<ItemModel> items) {
        VBox bottomFixedVBox = new VBox(10);
        bottomFixedVBox.getStyleClass().add("bottom-fixed-vbox");
        // bottomFixedVBox.setAlignment(Pos.CENTER);

        // Create a HBox to hold the total price label and the total price
        HBox totalPriceBox = new HBox(10);
        totalPriceBox.getStyleClass().add("total-price-box");
        totalPriceBox.setPadding(new Insets(10));

        // Create a label to display the total price
        Label totalPriceLabel = new Label("Total Price:");
        totalPriceLabel.getStyleClass().add("total-price-label");

        // Calculate total price
        totalPrice = items.stream()
                .mapToDouble(item -> item.getItemPrice() * item.getItemQuantity())
                .sum();

        // Create a label to display the total price value
        Label totalPriceValueLabel = new Label(String.format("$%.2f", totalPrice));
        totalPriceValueLabel.getStyleClass().add("total-price-value-label");

        totalPriceBox.getChildren().addAll(totalPriceLabel, totalPriceValueLabel);

        // Create a button to place order
        Button placeOrderButton = new Button("Place Order");
        placeOrderButton.getStyleClass().add("place-order-button");
        placeOrderButton.setOnAction(e -> handlePlaceOrder());

        bottomFixedVBox.getChildren().addAll(totalPriceBox, placeOrderButton);
        return bottomFixedVBox;
    }

    private VBox createOrderOptionSection() {
        VBox orderOptionSection = new VBox(10);
        orderOptionSection.setPadding(new Insets(10, 0, 0, 0));
        orderOptionSection.getStyleClass().add("order-option-section");

        // Create order option selection
        Label orderOptionLabel = new Label("Select Order Option:");
        orderOptionLabel.getStyleClass().add("order-option-label");
        ComboBox<String> orderOptionComboBox = new ComboBox<>();
        orderOptionComboBox.getStyleClass().add("order-option-combobox");
        orderOptionComboBox.getItems().addAll("Dine In", "Takeaway", "Delivery");
        orderOptionComboBox.setValue("Dine In");

        // Create address input field (initially hidden)
        VBox addressBox = new VBox(10);
        addressBox.getStyleClass().add("address-box");
        addressBox.setVisible(false);
        Label addressLabel = new Label("Delivery Address:");
        addressLabel.getStyleClass().add("address-label");
        TextArea addressTextArea = new TextArea();
        addressTextArea.setPrefRowCount(5);
        addressTextArea.getStyleClass().add("address-textarea");
        addressBox.getChildren().addAll(addressLabel, addressTextArea);

        // Show address input field if "Delivery" is selected
        orderOptionComboBox.setOnAction(e -> {
            if ("Delivery".equals(orderOptionComboBox.getValue())) {
                addressBox.setMaxHeight(200);
                addressBox.setMinHeight(200);
                addressBox.setVisible(true);
            } else {
                addressBox.setMaxHeight(0);
                addressBox.setMinHeight(0);
                addressBox.setVisible(false);

            }
        });

        Separator separator = new Separator();
        separator.getStyleClass().add("separator");

        orderOptionSection.getChildren().addAll(separator, orderOptionLabel, orderOptionComboBox, addressBox);
        return orderOptionSection;
    }



    private void handlePlaceOrder() {
        Double balance = customer.getBalance();
        OrderMethodEnum orderMethod;
        String deliveryAddress = null;

        // Get tthe order method from the value of the order option combo box
        ComboBox<String> orderOptionComboBox = (ComboBox<String>) this.lookup(".order-option-combobox");
        if ("Dine In".equals(orderOptionComboBox.getValue())) {
            orderMethod = OrderMethodEnum.DINE_IN;
        } else if ("Takeaway".equals(orderOptionComboBox.getValue())) {
            orderMethod = OrderMethodEnum.TAKEAWAY;
        } else {
            orderMethod = OrderMethodEnum.DELIVERY;
            TextArea addressTextArea = (TextArea) this.lookup(".address-textarea");
            deliveryAddress = addressTextArea.getText();
        }
    
        // Check if the customer has enough balance to place the order
        if (balance < totalPrice) {
            showDialog("Insufficient Balance", "Unable to place order", "You do not have enough balance to complete this order.");
        } else {
            // Ask for confirmation to place the order
            boolean confirmationResult = showConfirmationDialog("Confirm Order", balance, totalPrice);
            if (confirmationResult) {
                showDialog("Order Placed", null, "Your order has been successfully placed.");
                CustomerOrderServices.placeOrder(orderMethod, deliveryAddress);
                CustomerViewModel.getOrderViewModel().navigate(CustomerViewModel.getOrderViewModel().getOrderHistoryUI("Pending"));

                // Clear session items after placing the order
                SessionUtil.setItemsInSession(null);
            }
        }
    }
    
    private boolean showConfirmationDialog(String title, double balance, double totalPrice) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText("Confirm Order Placement");
    
        // Set the custom content for the dialog
        VBox contentBox = new VBox(10);
        contentBox.setPadding(new Insets(20, 150, 10, 10));
        contentBox.getStyleClass().add("content-box");
    


        Label balanceLabel = new Label("Current Balance:");
        balanceLabel.getStyleClass().add("balance-label");
        Label balanceValue = new Label(String.format("RM%.2f", balance));
        balanceValue.getStyleClass().add("balance-value");

        Label priceLabel = new Label("Total Price:");
        priceLabel.getStyleClass().add("price-label");
        Label priceValue = new Label(String.format("RM%.2f", totalPrice));
        priceValue.getStyleClass().add("price-value");

        Label newBalanceLabel = new Label("Balance After Order:");
        newBalanceLabel.getStyleClass().add("new-balance-label");
        Label newBalanceValue = new Label(String.format("RM%.2f", balance - totalPrice));
        newBalanceValue.getStyleClass().add("new-balance-value");
    
        contentBox.getChildren().addAll(
            new HBox(10, balanceLabel, balanceValue),
            new HBox(10, priceLabel, priceValue),
            new HBox(10, newBalanceLabel, newBalanceValue)
        );
    
        dialog.getDialogPane().setContent(contentBox);
    
        // Setup buttons
        ButtonType confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().setAll(confirmButtonType, cancelButtonType);
        dialog.getDialogPane().getStyleClass().add("confirmation-dialog");
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/com/Group3/foodorderingsystem/Module/Platform/Customer/Order/ui/OrderSummaryUI.css").toExternalForm());
    
        // Show the dialog and wait for the user to respond
        Optional<ButtonType> result = dialog.showAndWait();
        return result.isPresent() && result.get() == confirmButtonType;
    }
    
    
    private void showDialog(String title, String header, String content) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);
    
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(okButtonType);
        dialog.showAndWait();
    }
    

    /**
     * Inner class to display order items
     */

    public VBox getItemsDetails() {
        VBox itemsBox = new VBox(10);
        itemsBox.setPadding(new Insets(10, 0, 0, 0));

        // Order summary label
        Label orderSummaryLabel = new Label("Order Summary");
        orderSummaryLabel.getStyleClass().add("order-summary-label");
        Separator separator = new Separator();
        separator.getStyleClass().add("separator");
        itemsBox.getChildren().addAll(orderSummaryLabel, separator);

        for (ItemModel item : items) {
            HBox itemBox = new HBox(10);
            itemBox.setPadding(new Insets(5, 0, 5, 0));
            itemBox.setPrefWidth(450);
            itemBox.setMaxWidth(Double.MAX_VALUE); // Allow items to expand horizontally

            Label itemQuantityLabel = new Label(item.getItemQuantity() + "x");
            itemQuantityLabel.getStyleClass().add("item-quantity-label");

            Label itemLabel = new Label(item.getItemName());
            itemLabel.getStyleClass().add("item-label");
            HBox.setHgrow(itemLabel, Priority.ALWAYS);

            Label priceLabel = new Label("$" + String.format("%.2f", item.getItemPrice() * item.getItemQuantity()));
            priceLabel.getStyleClass().add("price-label");

            itemBox.getChildren().addAll(itemQuantityLabel, itemLabel, priceLabel);
            itemsBox.getChildren().add(itemBox);
        }

        return itemsBox;
    }
}

