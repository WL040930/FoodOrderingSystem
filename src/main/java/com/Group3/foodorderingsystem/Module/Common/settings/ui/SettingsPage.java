package com.Group3.foodorderingsystem.Module.Common.settings.ui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Common.Login.LoginPage;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;

import javafx.geometry.Pos;

public class SettingsPage extends BaseContentPanel {

    public SettingsPage() {
        super();

        setFooterHeight(0);
        setContentHeight(550);
        setHeader(header());
        setContent(content());
    }

    public Node header() {
        return new TitleBackButton("Settings");
    }

    public Node content() {
        UserCard userCard = new UserCard("f617bf0e-ee06-444c-ac5c-31f0415acca1");

        VBox settingsOptions = new VBox(10);
        settingsOptions.setAlignment(Pos.TOP_CENTER); // Change alignment to start from top

        Label titleLabel = new Label("Settings");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #4CAF50;");

        Button accountSettingsButton = new Button("Account Settings");
        Button notificationSettingsButton = new Button("Notification Settings");
        Button privacySettingsButton = new Button("Logout");

        accountSettingsButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        notificationSettingsButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        privacySettingsButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        // Add action handlers for buttons
        accountSettingsButton.setOnAction(e -> System.out.println("Account Settings clicked"));
        notificationSettingsButton.setOnAction(e -> System.out.println("Notification Settings clicked"));
        privacySettingsButton.setOnAction(
                e -> {
                    SessionUtil.setAdminInSession(null);
                    SessionUtil.setCustomerInSession(null);
                    SessionUtil.setVendorInSession(null);
                    SessionUtil.setRiderInSession(null);

                    try {
                        LoginPage loginPage = new LoginPage();
                        Stage loginStage = new Stage();
                        loginPage.start(loginStage);
                        VendorViewModel.getMainFrame().dispose();
                        VendorViewModel.clear();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

        // Add components to the VBox
        settingsOptions.getChildren().addAll(userCard, titleLabel,
                accountSettingsButton,
                notificationSettingsButton,
                privacySettingsButton);

        return settingsOptions;
    }

}
