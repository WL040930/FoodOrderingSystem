package com.Group3.foodorderingsystem.Module.Platform.Admin;

import com.Group3.foodorderingsystem.Core.Widget.AdminNavigator;
import com.Group3.foodorderingsystem.Module.Common.Register.model.RegisterViewModel;
import com.Group3.foodorderingsystem.Module.Common.Settings.SettingsPanel;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Account.AccountManagementPanel;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Account.Register.AccountRegistration;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Account.Register.Widget.CustomerRegistration;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Account.Register.Widget.VendorRegistration;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Account.Register.Widget.VendorShopRegistration;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Account.Register.Widget.VendorShopRegistrationField;

import java.awt.Component;

public class AdminViewModel {
    public static AdminViewModel instance;

    // main frame 
    public AdminMainFrame adminMainFrame;
    public Component currentPanel; 

    // bottom navigator
    public AdminNavigator adminNavigator; 
    public AdminPanel adminPanel = AdminPanel.ACCOUNT_MANAGEMENT; 

    // Account Management Panel
    public AccountManagementPanel accountManagementPanel;
    public NewFeaturePanel newFeaturePanel;

    //// Account Registration 
    public AccountRegistration accountRegistrationPanel; 
    public RegisterViewModel registerViewModel;
    public CustomerRegistration customerRegistration;
    public VendorRegistration vendorRegistration;
    public VendorShopRegistration vendorShopRegistration;
    public VendorShopRegistrationField vendorShopRegistrationField;
    
    // Settings Panel
    public SettingsPanel settingsPanel;

    public void init() {
        adminPanel = AdminPanel.ACCOUNT_MANAGEMENT;
        instance = this; 

        adminNavigator = new AdminNavigator();
        accountManagementPanel = new AccountManagementPanel();
        newFeaturePanel = new NewFeaturePanel();
        settingsPanel = new SettingsPanel();
        adminMainFrame = new AdminMainFrame();

        instance = this;
    }

    public void initAccountRegistration() {
        registerViewModel = new RegisterViewModel();
        registerViewModel.init();
        accountRegistrationPanel = new AccountRegistration();

    }

    public void refresh() {
        adminMainFrame.panelController();
    }

    public static void navigate(Component panel) {
        AdminViewModel.instance.adminMainFrame.panelController(panel);
    }
}