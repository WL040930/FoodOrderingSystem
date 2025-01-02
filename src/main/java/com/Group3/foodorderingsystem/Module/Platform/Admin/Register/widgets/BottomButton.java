package com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class BottomButton extends Button{
    
    public BottomButton(String text, Runnable action) {
        super(); 

        this.setText(text);
        this.setMaxWidth(Double.MAX_VALUE);

        this.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; "
                        + "-fx-padding: 10px 30px 10px 30px; -fx-border-radius: 5px;");

        this.setOnMouseClicked(event -> {
            action.run();
        });

        this.setOnMouseEntered((MouseEvent e) -> this.setStyle(
                "-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 16px; "
                        + "-fx-padding: 10px 30px 10px 30px; -fx-border-radius: 5px;"));

        this.setOnMouseExited((MouseEvent e) -> this.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; "
                        + "-fx-padding: 10px 30px 10px 30px; -fx-border-radius: 5px;"));
    }
}