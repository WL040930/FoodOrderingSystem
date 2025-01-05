package com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Widgets.Card;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class VendorCard implements DynamicSearchBarUI.RenderTemplate<VendorModel> {

    @Override
    public Node render(VendorModel vendor) {
        // Shop Image
        ImageView shopImageView = Images.getImageView(vendor.getShopImage(), 100, 100);
        shopImageView.setStyle("-fx-border-radius: 10; -fx-border-color: darkgray;");

        // Shop Name
        Label shopName = new Label(vendor.getShopName());
        shopName.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Shop Description (strictly two lines)
        Label shopDescription = new Label(vendor.getShopDescription());
        shopDescription
                .setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");

        // Limit to two lines using CSS style
        shopDescription
                .setStyle("-fx-font-size: 14px; -fx-text-fill: #666; -fx-wrap-text: true; -fx-max-height: 40px;");
        shopDescription.setMaxHeight(40);

        int rating = Math.min(5, 2); 
        StringBuilder starBuilder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            starBuilder.append(i < rating ? "★" : "☆"); 
        }

        Label starRating = new Label(starBuilder.toString());
        starRating.setStyle("-fx-font-size: 16px; -fx-text-fill: #FFA500;");

        // Shop Details Box
        VBox detailsBox = new VBox(shopName, shopDescription, starRating);
        detailsBox.setAlignment(Pos.TOP_LEFT);
        detailsBox.setSpacing(5);
        HBox.setHgrow(detailsBox, Priority.ALWAYS);

        // Card Layout
        HBox contentBox = new HBox(shopImageView, detailsBox);
        contentBox.setAlignment(Pos.CENTER_LEFT);
        contentBox.setSpacing(15);

        Button exploreButton = new Button("Explore & Order");
        exploreButton.getStylesheets().add(getClass()
                .getResource("/com/Group3/foodorderingsystem/Module/Common/ButtonStyles.css")
                .toExternalForm());
        exploreButton.getStyleClass().add("explore-button");
        exploreButton.setOnAction(event -> {
            CustomerViewModel.getHomeViewModel().initMenuSelection(vendor.getId());
        });

        VBox buttonBox = new VBox();
        buttonBox.getChildren().addAll(contentBox, exploreButton);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);

        // Card Component
        return new Card(buttonBox, 500, 80, Color.TRANSPARENT);
    }
}
