package com.Group3.foodorderingsystem.Module.Platform.Vendor.Menu.widgets;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Util.Images;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.DynamicSearchBarUI;

public class ItemDisplay implements DynamicSearchBarUI.RenderTemplate<ItemModel> {

    private VBox resultContainer;

    // Constructor to initialize the result container
    public ItemDisplay(VBox resultContainer) {
        this.resultContainer = resultContainer;
    }

    // Implementing the 'render' method to display the filtered items
    @Override
    public javafx.scene.Node render(ItemModel item) {
        // Create a container for each item
        HBox itemBox = new HBox(15);
        itemBox.setPadding(new Insets(10));
        itemBox.setStyle(
                "-fx-border-color: #d3d3d3; -fx-border-width: 1px; -fx-background-color: #ffffff; -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 8, 0, 0, 2);");

        // Image view for the item image
        ImageView itemImageView = Images.getImageView(item.getItemImage(), 80, 80);
        itemImageView.setStyle("-fx-background-radius: 10px;");

        // Labels for item name, description, and price
        Label itemNameLabel = new Label(item.getItemName());
        itemNameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label itemDescriptionLabel = new Label(item.getItemDescription());
        itemDescriptionLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #888;");

        Label itemPriceLabel = new Label("$" + item.getItemPrice());
        itemPriceLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #333;");

        // VBox to hold the text information
        VBox itemTextBox = new VBox(5);
        itemTextBox.getChildren().addAll(itemNameLabel, itemDescriptionLabel, itemPriceLabel);
        itemTextBox.setStyle("-fx-alignment: top-left;");

        // Add the image and text container to the main item box
        itemBox.getChildren().addAll(itemImageView, itemTextBox);

        // Add item box to the result container
        resultContainer.getChildren().add(itemBox);

        // Set margin for the item box
        VBox.setMargin(itemBox, new Insets(0, 10, 10, 10));

        // Return the item box as a Node to be rendered
        return itemBox;
    }
}
