package com.Group3.foodorderingsystem.Module.Platform.Admin.Register.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class BasicInfoForm extends VBox{
    
    public BasicInfoForm() {
        super();
        init();
    }

    public void init() {
        VBox basicInfo = new VBox(10);
        basicInfo.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Basic Information");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #4CAF50;");

        Button accountSettingsButton = new Button("Account Settings");

        accountSettingsButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        // Add action handlers for buttons (can be linked to actual actions later)
        accountSettingsButton.setOnAction(e -> System.out.println("Account Settings clicked"));

        // Add components to the VBox
        basicInfo.getChildren().addAll(titleLabel, accountSettingsButton);

        // Add the VBox to the current HBox
        this.getChildren().add(basicInfo);
    }
}
