package com.Group3.foodorderingsystem.Module.Platform.Customer.Order.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;

public class OrderSummaryUI extends VBox {

    public OrderSummaryUI() {
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #f8fafc;");

        List<ItemModel> items = SessionUtil.getItemsFromSession();

        if (!items.isEmpty()) {
            displayOrderSummary(this, items);
        } else {
            Label noItemsLabel = new Label("No items in the order.");
            noItemsLabel.getStyleClass().add("no-items-label");
            this.getChildren().add(noItemsLabel);
        }

        // Create a ScrollPane for the main content
        ScrollPane scrollPane = new ScrollPane(this);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #f8fafc; -fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Create a top fixed HBox (header)
        HBox topFixedHBox = createTopFixedHBox();

        // Create a bottom fixed VBox (footer)
        VBox bottomFixedVBox = createBottomFixedVBox(items);

        // Create a StackPane to hold the ScrollPane and the fixed HBoxes/VBoxes
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(scrollPane, topFixedHBox, bottomFixedVBox);
        StackPane.setAlignment(topFixedHBox, Pos.TOP_CENTER);
        StackPane.setAlignment(bottomFixedVBox, Pos.BOTTOM_CENTER);

        // Apply CSS
        //this.getStylesheets().add(getClass().getResource("/com/Group3/foodorderingsystem/Module/Ordering/Customer/OrderSummary.css").toExternalForm());
    }

    private void displayOrderSummary(VBox root, List<ItemModel> items) {
        OrderItemsUI itemsUI = new OrderItemsUI(items);

        // Add empty space to the top
        VBox emptySpace = new VBox();
        emptySpace.getStyleClass().add("empty-space");

        // Create order option section
        VBox orderOptionSection = createOrderOptionSection();

        // Add 70px empty space
        Region emptySpace1 = new Region();
        emptySpace1.setPrefHeight(70);
        VBox.setVgrow(emptySpace1, Priority.ALWAYS);

        root.getChildren().addAll(emptySpace, itemsUI.getItemsDetails(), orderOptionSection, emptySpace1);
    }

    private HBox createTopFixedHBox() {
        HBox topFixedHBox = new HBox(10);
        topFixedHBox.getStyleClass().add("top-fixed-hbox");
        topFixedHBox.setAlignment(Pos.CENTER_LEFT);
        topFixedHBox.setPadding(new Insets(10));

        // Create a back button with an icon
        Button backButton = new Button();
        backButton.getStyleClass().add("back-button");
        ImageView backIcon = Images.getImageView("cancel_icon", 0, 0);
        backIcon.getStyleClass().add("back-icon");
        backIcon.setFitWidth(25);
        backIcon.setFitHeight(25);
        backButton.setGraphic(backIcon);
        // backButton.setOnAction(e -> handleBackAction());

        // Create a shop name label, retrieve the shop name from the item list in the session
        Label shopNameLabel = new Label("Shop Name");
        shopNameLabel.getStyleClass().add("shop-name-label");

        topFixedHBox.getChildren().addAll(backButton, shopNameLabel);
        return topFixedHBox;
    }

    private VBox createBottomFixedVBox(List<ItemModel> items) {
        VBox bottomFixedVBox = new VBox(10);
        bottomFixedVBox.getStyleClass().add("bottom-fixed-vbox");
        bottomFixedVBox.setAlignment(Pos.CENTER);

        // Create a HBox to hold the total price label and the total price
        HBox totalPriceBox = new HBox(10);
        totalPriceBox.getStyleClass().add("total-price-box");
        totalPriceBox.setAlignment(Pos.CENTER_RIGHT);
        totalPriceBox.setPadding(new Insets(10));

        // Create a label to display the total price
        Label totalPriceLabel = new Label("Total Price:");
        totalPriceLabel.getStyleClass().add("total-price-label");

        // Calculate total price
        double totalPrice = items.stream()
                .mapToDouble(item -> item.getItemPrice() * item.getItemQuantity())
                .sum();

        // Create a label to display the total price value
        Label totalPriceValueLabel = new Label(String.format("$%.2f", totalPrice));
        totalPriceValueLabel.getStyleClass().add("total-price-value-label");

        totalPriceBox.getChildren().addAll(totalPriceLabel, totalPriceValueLabel);

        // Create a button to place order
        Button placeOrderButton = new Button("Place Order");
        placeOrderButton.getStyleClass().add("place-order-button");
        // placeOrderButton.setOnAction(e -> handlePlaceOrder());

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
                addressBox.setVisible(true);
            } else {
                addressBox.setVisible(false);
            }
        });

        Separator separator = new Separator();
        separator.getStyleClass().add("separator");

        orderOptionSection.getChildren().addAll(separator, orderOptionLabel, orderOptionComboBox, addressBox);
        return orderOptionSection;
    }

    // private void handleBackAction() {
    //     // Define the action when the back button is clicked
    //     // For example, close the current window or navigate to the previous UI
    //     Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel the order?", ButtonType.YES, ButtonType.NO);
    //     Optional<ButtonType> result = alert.showAndWait();
    //     if (result.isPresent() && result.get() == ButtonType.YES) {
    //         // Clear session items if necessary
    //         SessionUtil.setItemsInSession(null);
    //         // Close the current window
    //         ((Stage) this.getScene().getWindow()).close();
    //     }
    // }

    // private void handlePlaceOrder() {
    //     // Implement the logic to place the order
    //     // For example, validate inputs, send data to the server, etc.
    //     Alert alert = new Alert(Alert.AlertType.INFORMATION, "Order placed successfully!", ButtonType.OK);
    //     alert.showAndWait();

    //     // Clear session items
    //     SessionUtil.setItemsInSession(null);

    //     // Close the current window or navigate to another UI
    //     ((Stage) this.getScene().getWindow()).close();
    // }

    /**
     * Inner class to display order items
     */
    public static class OrderItemsUI {
        private List<ItemModel> items;

        public OrderItemsUI(List<ItemModel> items) {
            this.items = items;
        }

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
}

