package com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Widgets.Card;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class VendorSmallCard extends Card {

    private VendorModel vendor;

    public VendorSmallCard(VendorModel vendor) {
        super();
        this.vendor = vendor;

        this.setContent(buildContent());
        this.setCardSize(120, 190);
        this.setBackgroundColor(Color.TRANSPARENT);
    }

    private Node buildContent() {
        // Create the ImageView for the shop image
        ImageView imageView = Images.getImageView(vendor.getShopImage(), 100, 100);

        // Create the Label for shop name
        Label shopNameLabel = new Label(vendor.getShopName());
        shopNameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Create the Label for star rating
        Label starRatingLabel = new Label(getStarRating(2)); // Example rating value (2)
        starRatingLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gold;");

        Button button = new Button("Explore");
        button.getStylesheets().add(getClass()
                .getResource("/com/Group3/foodorderingsystem/Module/Common/ButtonStyles.css")
                .toExternalForm());
        button.getStyleClass().add("explore-button");
        button.setMinWidth(100); 

        VBox vbox = new VBox(1); 
        vbox.getChildren().addAll(imageView, shopNameLabel, starRatingLabel, button);

        vbox.setPadding(new Insets(10));

        return vbox;
    }

    // Helper method to generate a star rating string
    private String getStarRating(int rating) {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (i < rating) {
                stars.append("★"); // Filled star
            } else {
                stars.append("☆"); // Empty star
            }
        }
        return stars.toString();
    }
}
