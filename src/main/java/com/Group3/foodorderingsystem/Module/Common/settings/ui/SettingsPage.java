package com.Group3.foodorderingsystem.Module.Common.settings.ui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Common.Login.LoginPage;
import com.Group3.foodorderingsystem.Module.Common.settings.widgets.IconLabelContainer;
import com.Group3.foodorderingsystem.Module.Common.settings.widgets.UserCard;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;

import javafx.geometry.Pos;

public class SettingsPage extends BaseContentPanel {

    private User user; 
    public SettingsPage(User user) {
        super();

        this.user = user;

        setFooterHeight(0);
        setContentHeight(550);
        setHeader(header());
        setContent(content());
    }

    public Node header() {
        return new TitleBackButton("Settings");
    }

    public Node content() {
        UserCard userCard = new UserCard(user.getId());

        VBox settingsOptions = new VBox(10);
        settingsOptions.setAlignment(Pos.TOP_CENTER);

        IconLabelContainer labelContainer = new IconLabelContainer();
        labelContainer.addOption("Account Settings", "manage_profile.png", () -> {
            switch (user.getRole()) {
                case ADMIN:
                    AdminViewModel.getSettingsViewModel().setSettingsProfileManagement(new SettingsProfileManagement(user));
                    AdminViewModel.getSettingsViewModel().navigate(AdminViewModel.getSettingsViewModel().getSettingsProfileManagement());
                    break;
                case CUSTOMER: 
                    CustomerViewModel.getSettingsViewModel().setSettingsProfileManagement(new SettingsProfileManagement(user));
                    CustomerViewModel.getSettingsViewModel().navigate(CustomerViewModel.getSettingsViewModel().getSettingsProfileManagement());
                    break;
                case VENDOR:
                    VendorViewModel.getSettingsViewModel().setSettingsProfileManagement(new SettingsProfileManagement(user));
                    VendorViewModel.getSettingsViewModel().navigate(VendorViewModel.getSettingsViewModel().getSettingsProfileManagement());
                    break;
                default:
                    break;
            }
        });

        labelContainer.addOption("Logout", "logout.png", () -> {
            SessionUtil.clearSession();
            LoginPage loginPage = new LoginPage();
            Stage stage = new Stage(); 
            loginPage.start(stage);

            switch (user.getRole()) {
                case ADMIN:
                    AdminViewModel.getAdminMainFrame().dispose();
                    AdminViewModel.setInstance(null);
                    break;

                case VENDOR:
                    VendorViewModel.getMainFrame().dispose();
                    VendorViewModel.setInstance(null);
                    break;

                case CUSTOMER: 
                    CustomerViewModel.getCustomerMainFrame().dispose();
                    CustomerViewModel.setInstance(null);
                default:
                    break;
            }
        });

        settingsOptions.getChildren().addAll(userCard, labelContainer);

        return settingsOptions;
    }

}
