package com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Widgets.Card;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MenuItemCard implements DynamicSearchBarUI.RenderTemplate<ItemModel> {

    @Override
    public Node render(ItemModel item) {
        // Item Image
        ImageView itemImageView = Images.getImageView(item.getItemImage(), 100, 100); // Reduced size
        itemImageView.setStyle("-fx-border-radius: 8; -fx-border-color: darkgray;");

        // Food Name
        Label itemName = new Label(item.getItemName());
        itemName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;"); // Reduced font size

        // Food Description
        Label itemDescription = new Label(item.getItemDescription());
        itemDescription.setStyle("-fx-font-size: 12px; -fx-text-fill: #666; -fx-wrap-text: true;");
        itemDescription.setMaxHeight(35); // Reduced height

        // Food Price
        Label itemPrice = new Label("RM " + String.format("%.2f", item.getItemPrice()));
        itemPrice.setStyle("-fx-font-size: 14px; -fx-text-fill: #FFA500;");

        // Product Availability
        Label availableQuantityLabel = new Label("Available: " + item.getItemQuantity());
        availableQuantityLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #333;");

        // Quantity Control (starting at 0)
        Label selectedQuantityLabel = new Label("0");
        selectedQuantityLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");

        Button decrementButton = new Button("-");
        decrementButton.setStyle(
                "-fx-font-size: 14px; -fx-background-color: #334e68; -fx-text-fill: white; -fx-border-radius: 4; -fx-background-radius: 4;");
        decrementButton.setOnAction(e -> {
            int currentQuantity = Integer.parseInt(selectedQuantityLabel.getText());
            if (currentQuantity > 0) {
                selectedQuantityLabel.setText(String.valueOf(currentQuantity - 1));
            }
        });
        decrementButton.setMinSize(30, 30);
        decrementButton.setMaxSize(30, 30);

        Button incrementButton = new Button("+");
        incrementButton.setStyle(
                "-fx-font-size: 14px; -fx-background-color: #334e68; -fx-text-fill: white; -fx-border-radius: 4; -fx-background-radius: 4;");
        incrementButton.setOnAction(e -> {
            int currentQuantity = Integer.parseInt(selectedQuantityLabel.getText());
            if (currentQuantity < item.getItemQuantity()) {
                selectedQuantityLabel.setText(String.valueOf(currentQuantity + 1));
            }
        });
        incrementButton.setMinSize(30, 30);
        incrementButton.setMaxSize(30, 30);

        // Quantity Control Box
        HBox quantityControlBox = new HBox(decrementButton, selectedQuantityLabel, incrementButton);
        quantityControlBox.setAlignment(Pos.CENTER_RIGHT);
        quantityControlBox.setSpacing(5); // Reduced spacing

        // Food Details Box
        VBox detailsBox = new VBox(itemName, itemDescription, itemPrice, availableQuantityLabel, quantityControlBox);
        detailsBox.setAlignment(Pos.TOP_LEFT);
        detailsBox.setSpacing(3); // Reduced spacing
        HBox.setHgrow(detailsBox, Priority.ALWAYS);

        // Card Layout
        HBox contentBox = new HBox(itemImageView, detailsBox);
        contentBox.setAlignment(Pos.CENTER_LEFT);
        contentBox.setSpacing(5); // Reduced spacing between elements

        Card card = new Card(contentBox, 470, 100, // Reduced card dimensions
                item.getItemQuantity() > 0 ? null : javafx.scene.paint.Color.LIGHTGRAY);
        card.setStyle("-fx-padding: 5;"); // Remove padding from the card

        HBox cardBox = new HBox(card);
        cardBox.setAlignment(Pos.CENTER);
        cardBox.setPadding(new javafx.geometry.Insets(1)); // Reduced padding between card and container
        return cardBox;
    }
}