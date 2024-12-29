package com.Group3.foodorderingsystem.Core.Widgets;

import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.HeaderClass;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class Header extends HBox {

    /**
     * Insert a List<HeaderClass> for automatically generating a header
     * 
     * @param config - consists of title, action, and isSelected
     */
    public Header(List<HeaderClass> config) {
        init(config);
    }

    private void init(List<HeaderClass> config) {
        this.setSpacing(10); // Spacing between buttons
        this.setStyle("-fx-padding: 10px; -fx-background-color: #4CAF50;");

        for (HeaderClass headerItem : config) {
            Button button = createButton(headerItem);
            this.getChildren().add(button);
        }
    }

    private Button createButton(HeaderClass headerItem) {
        Button button = new Button(headerItem.getTitle());
        button.setStyle(
                "-fx-background-radius: 15; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-background-color: " + (headerItem.getIsSelected() ? "#3E8E41" : "#FFFFFF") + "; " +
                        "-fx-text-fill: " + (headerItem.getIsSelected() ? "#FFFFFF" : "#4CAF50") + ";" // Text color
        );

        button.setOnAction(event -> {
            headerItem.getAction().run(); 
        });
        return button;
    }
}