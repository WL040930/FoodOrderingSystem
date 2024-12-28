package com.Group3.foodorderingsystem.Module.Platform.Admin.Register.ui;

import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class RegisterRoleSelection extends VBox {

    public RegisterRoleSelection() {
        super();
        init();
    }

    public void init() {
        VBox settingsOptions = new VBox(10);
        settingsOptions.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Role");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #4CAF50;");

        Button accountSettingsButton = new Button("Account Settings");

        accountSettingsButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        // Add action handlers for buttons (can be linked to actual actions later)
        accountSettingsButton.setOnAction(e -> {
            AdminViewModel.getRegisterViewModel().setBasicInfoForm(new BasicInfoForm());
            AdminViewModel.navigate(
                    AdminViewModel.getRegisterViewModel().getBasicInfoForm());
            AdminViewModel.getRegisterViewModel().setNode(
                    AdminViewModel.getRegisterViewModel().getBasicInfoForm());
        });

        // Add components to the VBox
        settingsOptions.getChildren().addAll(titleLabel, accountSettingsButton);

        // Add the VBox to the current HBox
        this.getChildren().add(settingsOptions);
    }
}
