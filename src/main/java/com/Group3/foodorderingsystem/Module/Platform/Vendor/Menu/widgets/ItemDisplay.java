package com.Group3.foodorderingsystem.Module.Platform.Vendor.Menu.widgets;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Util.Images;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Menu.ui.SearchBarWithData;

import java.util.List;

public class ItemDisplay implements SearchBarWithData.ObjectDisplayTemplate<ItemModel> {

    private VBox resultContainer;

    public ItemDisplay(VBox resultContainer) {
        this.resultContainer = resultContainer;
    }

    @Override
    public void updateDisplay(List<ItemModel> filteredList) {
        resultContainer.getChildren().clear(); 

        if (filteredList.isEmpty()) {
            Label noResultsLabel = new Label("No items found.");
            noResultsLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #888;");
            noResultsLabel.setAlignment(Pos.CENTER); 

            VBox noResultsContainer = new VBox(noResultsLabel);
            noResultsContainer.setAlignment(Pos.CENTER); 
            resultContainer.getChildren().add(noResultsContainer);
        } else {
            for (ItemModel item : filteredList) {
                HBox itemBox = new HBox(15);
                itemBox.setPadding(new Insets(10));
                itemBox.setStyle(
                        "-fx-border-color: #d3d3d3; -fx-border-width: 1px; -fx-background-color: #ffffff; -fx-background-radius: 10px;");

                itemBox.setStyle(
                        "-fx-border-color: #d3d3d3; -fx-border-width: 1px; -fx-background-color: #ffffff; -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 8, 0, 0, 2);");

                ImageView itemImageView = Images.getImageView(item.getItemImage(), 80, 80);
                itemImageView.setStyle("-fx-background-radius: 10px;");

                Label itemNameLabel = new Label(item.getItemName());
                itemNameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

                Label itemDescriptionLabel = new Label(item.getItemDescription());
                itemDescriptionLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #888;");

                Label itemPriceLabel = new Label("$" + item.getItemPrice());
                itemPriceLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #333;");

                VBox itemTextBox = new VBox(5);
                itemTextBox.getChildren().addAll(itemNameLabel, itemDescriptionLabel, itemPriceLabel);
                itemTextBox.setStyle("-fx-alignment: top-left;");

                itemBox.getChildren().addAll(itemImageView, itemTextBox);

                resultContainer.getChildren().add(itemBox);

                VBox.setMargin(itemBox, new Insets(0, 10, 10, 10));
            }
        }
    }
}
