package com.Group3.foodorderingsystem.Module.Platform.Manager.Settings.ui;

import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Common.Login.LoginPage;
import com.Group3.foodorderingsystem.Module.Common.settings.widgets.IconLabelContainer;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Manager.ManagerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Runner.RunnerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;

import javafx.scene.Node;
import javafx.stage.Stage;

public class ManagerSettingsUI extends BaseContentPanel {

    public ManagerSettingsUI() {
        super();

        setHeader(header());
        setContent(content());
    }

    private Node header() {
        return new TitleBackButton("Settings");
    }

    private Node content() {
        IconLabelContainer labelContainer = new IconLabelContainer();

        labelContainer.addOption("Logout", "logout.png", () -> {
            SessionUtil.clearSession();
            LoginPage loginPage = new LoginPage();
            Stage stage = new Stage();
            loginPage.start(stage);

            ManagerViewModel.getMainFrame().dispose();
            ManagerViewModel.setInstance(null);
        });

        return labelContainer;
    }

}
