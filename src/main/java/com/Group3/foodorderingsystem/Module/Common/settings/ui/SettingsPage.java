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
import com.Group3.foodorderingsystem.Module.Common.settings.widgets.IconLabelContainer;
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
        settingsOptions.setAlignment(Pos.TOP_CENTER);

        IconLabelContainer labelContainer = new IconLabelContainer();
        labelContainer.addOption("Account Settings", "manage_profile.png", () -> {
            System.out.println("Account Settings");
        });

        labelContainer.addOption("Logout", "logout.png", () -> {
            SessionUtil.clearSession();
            LoginPage loginPage = new LoginPage();
            
        });

        settingsOptions.getChildren().addAll(userCard, labelContainer);

        return settingsOptions;
    }

}
