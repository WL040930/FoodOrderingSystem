package com.Group3.foodorderingsystem.Module.Common.settings.model;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Module.Common.settings.ui.SettingsPage;
import com.Group3.foodorderingsystem.Module.Common.settings.ui.SettingsProfileManagement;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;

import javafx.scene.Node;

public class SettingsViewModel extends ViewModelConfig {

    private User user;

    @Override
    public void navigate(Node node) {
        switch (this.user.getRole()) {
            case ADMIN:
                AdminViewModel.getSettingsViewModel().setNode(node);
                AdminViewModel.navigate(AdminViewModel.getSettingsViewModel().getNode());
                break;
            case CUSTOMER:
                CustomerViewModel.getSettingsViewModel().setNode(node);
                CustomerViewModel.navigate(CustomerViewModel.getSettingsViewModel().getNode());
                break;
            case VENDOR:
                VendorViewModel.getSettingsViewModel().setNode(node);
                VendorViewModel.navigate(VendorViewModel.getSettingsViewModel().getNode());
                break;
            default:
                break;
        }
    }

    public SettingsViewModel(String userId) {
        super();
        this.user = UserServices.findUserById(userId);
    }

    public void init() {
        this.settingsPage = new SettingsPage(user);
        setNode(settingsPage);
    }

    private SettingsPage settingsPage;

    public SettingsPage getSettingsPage() {
        return settingsPage;
    }

    private SettingsProfileManagement settingsProfileManagement;

    public SettingsProfileManagement getSettingsProfileManagement() {
        return settingsProfileManagement;
    }

    public void setSettingsProfileManagement(SettingsProfileManagement settingsProfileManagement) {
        this.settingsProfileManagement = settingsProfileManagement;
    }
}
