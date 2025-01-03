package com.Group3.foodorderingsystem.Module.Common.settings.widgets;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.Cursor;

import com.Group3.foodorderingsystem.Core.Util.Images;

public class IconLabelContainer extends VBox {

    public IconLabelContainer() {
        super();
        init();
    }

    private void init() {
        // Apply styling to the container
        this.setSpacing(10);
        this.setAlignment(Pos.TOP_CENTER);
        this.setStyle(
                "-fx-padding: 15px; -fx-background-color: #ffffff; -fx-border-color: #dcdcdc; -fx-border-radius: 15px; -fx-background-radius: 15px;");
    }

    public void addOption(String labelText, String iconPath, Runnable action) {
        // Create an IconLabel
        IconLabel iconLabel = new IconLabel(labelText, iconPath, action);

        // Add the IconLabel to the container
        this.getChildren().add(iconLabel);
    }

    private class IconLabel extends HBox {

        private String labelText;
        private String iconPath;
        private Runnable action;

        public IconLabel(String labelText, String iconPath, Runnable action) {
            super();
            this.labelText = labelText;
            this.iconPath = iconPath;
            this.action = action;
            init();
        }

        private void init() {
            // Create the label node
            Label labelNode = new Label(labelText);
            labelNode.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            labelNode.setTextFill(Color.DARKGRAY);
            labelNode.setStyle("-fx-padding: 5px;");

            // Create the icon node
            ImageView iconNode = Images.getImageView(iconPath, 30, 30);
            iconNode.setFitWidth(30);
            iconNode.setFitHeight(30);

            // Apply styling to the HBox
            this.setSpacing(10);
            this.setAlignment(Pos.CENTER_LEFT);
            this.setStyle(
                    "-fx-padding: 10px; -fx-background-color: #f9f9f9; -fx-border-color: #dcdcdc; -fx-border-radius: 5px; -fx-background-radius: 5px;");

            // Set cursor to hand on hover
            this.setCursor(Cursor.HAND);

            // Add hover color change
            this.setOnMouseEntered(event -> {
                this.setStyle(
                        "-fx-padding: 10px; -fx-background-color: #e6e6e6; -fx-border-color: #b3b3b3; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            });
            this.setOnMouseExited(event -> {
                this.setStyle(
                        "-fx-padding: 10px; -fx-background-color: #f9f9f9; -fx-border-color: #dcdcdc; -fx-border-radius: 5px; -fx-background-radius: 5px;");
            });

            // Add click action
            this.setOnMouseClicked(event -> {
                if (action != null) {
                    action.run();
                }
            });

            // Add icon and label to the HBox
            this.getChildren().addAll(iconNode, labelNode);
        }
    }
}
