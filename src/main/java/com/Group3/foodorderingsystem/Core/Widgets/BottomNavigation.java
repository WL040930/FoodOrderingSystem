package com.Group3.foodorderingsystem.Core.Widgets;

import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.BottomNavigationClass;
import com.Group3.foodorderingsystem.Core.Util.Images;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class BottomNavigation extends HBox {

    /**
     * Insert a List<BottomNavigationClass> for automatically generating a bottom
     * navigation bar
     * 
     * @param config - consists of title, iconName, action, and isSelected
     */
    public BottomNavigation(List<BottomNavigationClass> config) {
        init(config);
    }

    private void init(List<BottomNavigationClass> config) {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(0);
        this.setStyle("-fx-padding: 10px; -fx-background-color: #4CAF50;");

        for (BottomNavigationClass navItem : config) {
            VBox button = createButtonWithIcon(
                    navItem.getTitle(),
                    navItem.getIconName(),
                    navItem.getAction(),
                    navItem.getIsSelected()
            );

            HBox.setHgrow(button, Priority.ALWAYS);
            button.setMaxWidth(Double.MAX_VALUE);

            this.getChildren().add(button);
        }
    }

    private VBox createButtonWithIcon(String buttonText, String iconPath, Runnable action, boolean isSelected) {
        VBox buttonBox = new VBox(5);
        buttonBox.setAlignment(Pos.CENTER);

        // Load icon image
        ImageView icon = Images.getImageView(iconPath, 40, 40);

        // Create label for the button text
        Label label = new Label(buttonText);
        label.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        label.setTextFill(Color.WHITE);

        // Set button style based on selection
        if (isSelected) {
            buttonBox.setStyle("-fx-padding: 10px; -fx-background-color: #2E7D32;"); // Selected background color
        } else {
            buttonBox.setStyle("-fx-padding: 10px; -fx-background-color: transparent;"); // Default background color
        }

        // Set button hover and click styles/actions
        buttonBox.setOnMouseEntered(e -> buttonBox.setStyle("-fx-cursor: hand; -fx-background-color: #3E8E41;"));
        buttonBox.setOnMouseExited(e -> {
            if (!isSelected) {
                buttonBox.setStyle("-fx-background-color: transparent;");
            }
        });
        buttonBox.setOnMouseClicked(e -> action.run());

        // Add icon and label to the button
        buttonBox.getChildren().addAll(icon, label);

        return buttonBox;
    }
}
