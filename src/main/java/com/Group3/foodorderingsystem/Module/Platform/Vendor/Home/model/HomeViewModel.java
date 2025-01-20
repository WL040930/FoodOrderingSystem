package com.Group3.foodorderingsystem.Module.Platform.Vendor.Home.model;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Home.ui.VendorOrderDetailsUI;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Home.ui.VendorOrderListUI;

import javafx.scene.Node;

public class HomeViewModel extends ViewModelConfig {

    private VendorOrderListUI vendorOrderListUI;
    private VendorOrderDetailsUI vendorOrderDetailsUI;

    public void navigate(Node node) {
        setNode(node);

        VendorViewModel.navigate(node);
    }

    public void init() {
        vendorOrderListUI = new VendorOrderListUI();

        setNode(vendorOrderListUI);

    }

    public HomeViewModel() {
        vendorOrderListUI = new VendorOrderListUI();
        
    }

    public VendorOrderListUI getVendorOrderListUI() {
        return vendorOrderListUI;
    }

    public VendorOrderListUI getVendorOrderListUI(String tab) {
        vendorOrderListUI = new VendorOrderListUI(tab);
        return vendorOrderListUI;
    }

    public void setVendorOrderListUI(VendorOrderListUI vendorOrderListUI) {
        this.vendorOrderListUI = vendorOrderListUI;
    }

    public VendorOrderDetailsUI getVendorOrderDetailsUI() {
        return this.vendorOrderDetailsUI;
    }

    public void setVendorOrderDetailsUI(VendorOrderDetailsUI vendorOrderDetailsUI) {
        this.vendorOrderDetailsUI = vendorOrderDetailsUI;
    }

}
