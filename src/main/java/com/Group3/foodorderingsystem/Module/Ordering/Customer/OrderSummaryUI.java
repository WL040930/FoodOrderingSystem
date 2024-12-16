package com.Group3.foodorderingsystem.Module.Ordering.Customer;

import com.Group3.foodorderingsystem.Core.Model.Entity.ItemModel;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;

import java.util.List;

public class OrderSummaryUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f8fafc;");

        List<ItemModel> items = SessionUtil.getItemsFromSession();

        if (!items.isEmpty()) {
            displayOrderSummary(root, items);
        } else {
            Label noItemsLabel = new Label("No items in the order.");
            noItemsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666666;");
            root.getChildren().add(noItemsLabel);
        }

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #f8fafc; -fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Create a top fixed VBox
        HBox topFixedVBox = new HBox(10);
        topFixedVBox.getStyleClass().add("top-fixed-hbox");

        //create a cancel icon
        Button backButton = new Button("");
        backButton.getStyleClass().add("back-button");
        ImageView backIcon = new ImageView(new Image(getClass().getResourceAsStream("/com/Group3/foodorderingsystem/Assets/Resource/cancel_icon.png")));
        backIcon.getStyleClass().add("back-icon");
        backIcon.setFitWidth(25);
        backIcon.setFitHeight(25);
        backButton.setGraphic(backIcon);
        //TODO: add action to decide how to direct back
        backButton.setOnAction(e -> primaryStage.close());


        //TODO: add shop name
        //create a shop name label, retrieve the shop name from the item list in the session
        Label shopNameLabel = new Label("Shop Name");
        shopNameLabel.getStyleClass().add("shop-name-label");

        topFixedVBox.getChildren().addAll(backButton, shopNameLabel);

        // Create a bottom fixed VBox
        VBox bottomFixedVBox = new VBox(10);
        bottomFixedVBox.getStyleClass().add("bottom-fixed-vbox");

        //Create a hbox to hold the total price label and the total price
        HBox totalPriceBox = new HBox(10);
        totalPriceBox.getStyleClass().add("total-price-box");

        // Create a label to display the total price
        Label totalPriceLabel = new Label("Total Price:");
        totalPriceLabel.getStyleClass().add("total-price-label");

        // Create a label to display the total price value
        Label totalPriceValueLabel = new Label("$" + String.format("%.2f", items.stream()
                .mapToDouble(item -> item.getItemPrice() * item.getItemQuantity())
                .sum()));
        totalPriceValueLabel.getStyleClass().add("total-price-value-label");

        totalPriceBox.getChildren().addAll(totalPriceLabel, totalPriceValueLabel);



        // Create a button to place order
        Button placeOrderButton = new Button("Place Order");
        placeOrderButton.getStyleClass().add("place-order-button");

        

        bottomFixedVBox.getChildren().addAll(totalPriceBox, placeOrderButton);

        //TODO: add action to place order


        // Create a StackPane to hold the ScrollPane and the fixed VBoxes
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(scrollPane, topFixedVBox, bottomFixedVBox);
        StackPane.setAlignment(topFixedVBox, Pos.TOP_CENTER);
        StackPane.setAlignment(bottomFixedVBox, Pos.BOTTOM_CENTER);

        Scene scene = new Scene(stackPane, 500, 780);
        scene.getStylesheets().add(getClass().getResource("/com/Group3/foodorderingsystem/Module/Ordering/Customer/OrderSummary.css").toExternalForm());

        primaryStage.setTitle("Order Summary");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void displayOrderSummary(VBox root, List<ItemModel> items) {
        OrderItemsUI itemsUI = new OrderItemsUI(items);

        // Add empty space to the top
        VBox emptySpace = new VBox();
        emptySpace.getStyleClass().add("empty-space");

        // Create order option section
        VBox orderOptionSection = createOrderOptionSection();

        //add 100px empty space
        Label emptySpace1 = new Label("");
        emptySpace.setPrefHeight(70);

        root.getChildren().addAll(emptySpace, itemsUI.getItemsDetails(), orderOptionSection, emptySpace1);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class OrderItemsUI {
        private List<ItemModel> items;

        public OrderItemsUI(List<ItemModel> items) {
            this.items = items;
        }

        public VBox getItemsDetails() {
            VBox itemsBox = new VBox(10);
            itemsBox.setPadding(new Insets(10, 0, 0, 0));

            //order summary label
            Label orderSummaryLabel = new Label("Order Summary");
            orderSummaryLabel.getStyleClass().add("order-summary-label");
            Separator separator = new Separator();
            separator.getStyleClass().add("separator");
            itemsBox.getChildren().addAll(orderSummaryLabel, separator);

            for (ItemModel item : items) {
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

    

    private VBox createOrderOptionSection() {
        VBox orderOptionSection = new VBox(10);
        orderOptionSection.setPadding(new Insets(10, 0, 0, 0));

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
}