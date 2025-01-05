package com.Group3.foodorderingsystem.Module.Platform.Customer.Home.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MenuSelectionUI extends BaseContentPanel {

    private VendorModel vendorModel;

    public MenuSelectionUI(String vendorId) {
        super();
        this.vendorModel = UserServices.findVendorById(vendorId);

        setHeader(header());
    }

    private Node header() {
        return new TitleBackButton(vendorModel.getShopName(), () -> {
            CustomerViewModel.getHomeViewModel().navigate(CustomerViewModel.getHomeViewModel().getHomeUI());
        });
    }

}
