package com.Group3.foodorderingsystem.Module.Common.settings;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class SettingsPage extends VBox {

    public SettingsPage() {
        super();
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        init();
    }

    public void init() {
        VBox settingsOptions = new VBox(10);
        settingsOptions.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Settings");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #4CAF50;");

        Button accountSettingsButton = new Button("Account Settings");
        Button notificationSettingsButton = new Button("Notification Settings");
        Button privacySettingsButton = new Button("Privacy Settings");

        accountSettingsButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        notificationSettingsButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        privacySettingsButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        // Add action handlers for buttons (can be linked to actual actions later)
        accountSettingsButton.setOnAction(e -> System.out.println("Account Settings clicked"));
        notificationSettingsButton.setOnAction(e -> System.out.println("Notification Settings clicked"));
        privacySettingsButton.setOnAction(e -> System.out.println("Privacy Settings clicked"));

        // Add components to the VBox
        settingsOptions.getChildren().addAll(titleLabel, accountSettingsButton, notificationSettingsButton,
                privacySettingsButton);

        // Add the VBox to the current HBox
        this.getChildren().add(settingsOptions);
    }
}
