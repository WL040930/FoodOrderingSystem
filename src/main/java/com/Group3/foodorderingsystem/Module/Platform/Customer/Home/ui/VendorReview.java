package com.Group3.foodorderingsystem.Module.Platform.Customer.Home.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class VendorReview extends BaseContentPanel {

    private VendorModel vendor;

    public VendorReview(String vendorId) {
        super();
        this.vendor = UserServices.findVendorById(vendorId);

        setHeader(header());
        setContent(content());

        setFooterHeight(0);
        setContentHeight(580);
    }

    private Node header() {
        return new TitleBackButton("Review - " + vendor.getShopName(), () -> {
            CustomerViewModel.getHomeViewModel().navigate(CustomerViewModel.getHomeViewModel().getMenuSelectionUI());
        });
    }

    private Node content() {
        return new HBox();
    }
}
