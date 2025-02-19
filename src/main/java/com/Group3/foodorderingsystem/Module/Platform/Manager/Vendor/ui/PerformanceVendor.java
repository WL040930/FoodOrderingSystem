package com.Group3.foodorderingsystem.Module.Platform.Manager.Vendor.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Manager.ManagerViewModel;

import javafx.scene.Node;

public class PerformanceVendor extends BaseContentPanel {

    private VendorModel vendor;

    public PerformanceVendor(VendorModel vendor) {
        super();
        this.vendor = vendor;

        setHeader(header());
    }

    private Node header() {
        return new TitleBackButton("Vendor Performance", () -> {
            ManagerViewModel.getVendorPerformanceViewModel()
                    .navigate(ManagerViewModel.getVendorPerformanceViewModel().getVendorListUI());
        });
    }
}
