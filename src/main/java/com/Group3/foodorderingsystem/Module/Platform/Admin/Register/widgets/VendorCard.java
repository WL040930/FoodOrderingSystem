package com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets;

import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import com.Group3.foodorderingsystem.Core.Util.Images;

public class VendorCard extends VBox {

    // Constructor parameters
    private final String title;
    private final int rating;
    private final String profilePicture;

    // UI components
    private Label titleLabel;
    private HBox ratingBox;
    private ImageView profileImageView;

    // Fixed card size
    private static final double CARD_WIDTH = 250;
    private static final double CARD_HEIGHT = 150;

    public VendorCard(String title, int rating, String profilePicture) {
        this.title = title;
        this.rating = rating;
        this.profilePicture = profilePicture;

        // Initialize UI components
        init();
    }

    private void init() {
        // Setting the fixed size and border of the VBox (card)
        this.setPrefWidth(CARD_WIDTH);
        this.setPrefHeight(CARD_HEIGHT);
        this.setStyle("-fx-background-color: #f9f9f9; -fx-padding: 10; " +
                "-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5;");

        // Title Label
        titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        // Rating Box (5 stars)
        ratingBox = new HBox(5); // 5px spacing between stars
        ratingBox.setStyle("-fx-alignment: center;");
        for (int i = 0; i < 5; i++) {
            Text star = new Text("★");
            if (i < rating) {
                star.setFill(Color.YELLOW); // Filled stars for rating
            } else {
                star.setFill(Color.GRAY); // Empty stars
            }
            ratingBox.getChildren().add(star);
        }

        // Profile Image (ImageView)
        profileImageView = Images.getImageView(profilePicture, 50, 50); // Image size set to 50x50
        profileImageView.setPreserveRatio(true);

        // Content Box (for horizontal layout of image and text)
        HBox contentBox = new HBox(10); // 10px space between profile image and text
        contentBox.setAlignment(Pos.CENTER_LEFT); // Align left
        contentBox.getChildren().addAll(profileImageView, createTextContainer());

        // Add content to card
        this.getChildren().add(contentBox);
        this.setStyle("-fx-alignment: center-left;");
    }

    private VBox createTextContainer() {
        VBox textContainer = new VBox(5); // Vertical space between title and rating
        textContainer.setAlignment(Pos.CENTER_LEFT); // Align text to the left
        textContainer.getChildren().addAll(titleLabel, ratingBox);
        return textContainer;
    }
}
