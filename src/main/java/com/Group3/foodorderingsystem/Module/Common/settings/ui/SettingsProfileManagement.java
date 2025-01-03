package com.Group3.foodorderingsystem.Module.Common.settings.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.BottomButton;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class SettingsProfileManagement extends BaseContentPanel {

    private User user;

    public SettingsProfileManagement(User user) {
        super();

        this.user = user;

        setHeader(header());
        setContent(content());
        setFooter(footer());
    }

    private Node header() {
        return new TitleBackButton("Profile Management", () -> {
            switch (user.getRole()) {
                case ADMIN:
                    AdminViewModel.getSettingsViewModel()
                            .navigate(AdminViewModel.getSettingsViewModel().getSettingsPage());
                    break;
                case CUSTOMER:
                    CustomerViewModel.getSettingsViewModel()
                            .navigate(CustomerViewModel.getSettingsViewModel().getSettingsPage());
                    break;
                case VENDOR:
                    VendorViewModel.getSettingsViewModel()
                            .navigate(VendorViewModel.getSettingsViewModel().getSettingsPage());
                    break;
                default:    
                    break;
            }
        });
    }

    private Node content() {
        return new Label("dd");
    }

    private Node footer() {
        return new BottomButton("Save", () -> {
            switch (user.getRole()) {
                case ADMIN:
                    AdminViewModel.initSettingsViewModel();
                    AdminViewModel.getSettingsViewModel().navigate(AdminViewModel.getSettingsViewModel().getNode());
                    break;
                case CUSTOMER:
                    CustomerViewModel.initSettingsViewModel(); 
                    CustomerViewModel.getSettingsViewModel().navigate(CustomerViewModel.getSettingsViewModel().getNode());
                    break;
                case VENDOR:
                    VendorViewModel.initSettingsViewModel();
                    VendorViewModel.getSettingsViewModel().navigate(VendorViewModel.getSettingsViewModel().getNode());
                    break;
                default:
                    break;
            }
        }); 
    }
}