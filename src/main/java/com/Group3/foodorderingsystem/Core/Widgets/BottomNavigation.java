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
        this.getStylesheets().add(getClass()
                .getResource("/com/Group3/foodorderingsystem/Module/Common/BottomNavCSS.css")
                .toExternalForm()); // Load the CSS stylesheet

        for (BottomNavigationClass navItem : config) {
            VBox button = createButtonWithIcon(
                    navItem.getTitle(),
                    navItem.getIconName(),
                    navItem.getAction(),
                    navItem.getIsSelected());

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

        // Apply the CSS class for the button box
        buttonBox.getStyleClass().add("button-box");

        // Apply selected class if the button is selected
        if (isSelected) {
            buttonBox.getStyleClass().add("selected");
        }

        // Set button hover and click styles/actions
        buttonBox.setOnMouseEntered(e -> buttonBox.setStyle("-fx-cursor: hand; -fx-background-color: #2563EB;"));
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
