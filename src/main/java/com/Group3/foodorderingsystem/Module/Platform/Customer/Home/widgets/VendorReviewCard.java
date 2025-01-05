package com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Widgets.Card;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class VendorReviewCard {

    private VendorModel vendor;
    private Card card;

    public VendorReviewCard(VendorModel vendor) {
        this.vendor = vendor;
        init();
    }

    public Card getCard() {
        return card;
    }

    private void init() {
        // Shop Image
        ImageView shopImage = Images.getImageView(vendor.getShopImage(), 80, 80);
        shopImage.setStyle("-fx-border-radius: 10; -fx-border-color: lightgray;");

        // Shop Name
        Label shopName = new Label(vendor.getShopName());
        shopName.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Shop Description
        Label shopDescription = new Label(vendor.getShopDescription());
        shopDescription.setStyle("-fx-font-size: 14px; -fx-text-fill: #666; -fx-wrap-text: true;");
        shopDescription.setMaxWidth(300);

        // Star Rating
        int rating = Math.min(5, 2); // Ensure rating is capped at 5
        StringBuilder starBuilder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            starBuilder.append(i < rating ? "★" : "☆");
        }
        Label starRating = new Label(starBuilder.toString());
        starRating.setStyle("-fx-font-size: 16px; -fx-text-fill: #FFA500;");

        // Details Box
        VBox detailsBox = new VBox(shopName, shopDescription, starRating);
        detailsBox.setAlignment(Pos.TOP_LEFT);
        detailsBox.setSpacing(5);
        HBox.setHgrow(detailsBox, Priority.ALWAYS);

        // Right Arrow (used to navigate)
        ImageView rightArrow = Images.getImageView("arrow-point-to-right.png", 20, 20);
        rightArrow.setCursor(Cursor.HAND); // Set the arrow as clickable
        rightArrow.setOnMouseClicked(e -> {
            System.out.println("Navigate to Vendor Details, Vendor ID: " + vendor.getId());
        });

        // Content Box (Including both image and arrow)
        HBox contentBox = new HBox(shopImage, detailsBox, rightArrow);
        contentBox.setAlignment(Pos.CENTER_LEFT);
        contentBox.setSpacing(15);

        // Card Layout
        card = new Card(contentBox, 500, 120, javafx.scene.paint.Color.TRANSPARENT);
        card.setStyle("-fx-background-color: white; -fx-border-color: lightgray; -fx-border-radius: 8;");
    }
}
