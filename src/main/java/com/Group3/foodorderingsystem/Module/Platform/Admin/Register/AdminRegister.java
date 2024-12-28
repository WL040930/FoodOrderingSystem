package com.Group3.foodorderingsystem.Module.Platform.Admin.Register;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class AdminRegister extends VBox {

    public AdminRegister() {
        init();
    }

    private void init() {
        VBox middleContent = new VBox(20);
        middleContent.setAlignment(Pos.CENTER);
        middleContent.setStyle("-fx-padding: 20px; -fx-background-color: #f0f0f0;");

        for (int i = 0; i < 20; i++) {
            Button contentItem = new Button("Content Item " + (i + 1));
            contentItem.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
            middleContent.getChildren().add(contentItem);
        }

        this.getChildren().addAll(middleContent);
    }
}
