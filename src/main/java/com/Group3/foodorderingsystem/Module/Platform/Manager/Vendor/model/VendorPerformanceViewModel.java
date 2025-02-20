package com.Group3.foodorderingsystem.Module.Platform.Manager.Vendor.model;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Module.Platform.Manager.ManagerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Manager.Vendor.ui.MenuVendor;
import com.Group3.foodorderingsystem.Module.Platform.Manager.Vendor.ui.PerformanceVendor;
import com.Group3.foodorderingsystem.Module.Platform.Manager.Vendor.ui.VendorListUI;

import javafx.scene.Node;

public class VendorPerformanceViewModel extends ViewModelConfig {

    @Override
    public void navigate(Node node) {
        setNode(node);
        ManagerViewModel.navigate(node);
    }

    public VendorPerformanceViewModel() {
        super();
        vendorListUI = new VendorListUI();
        setNode(vendorListUI);
    }

    private VendorListUI vendorListUI;

    public VendorListUI getVendorListUI() {
        return vendorListUI;
    }

    public void setVendorListUI(VendorListUI vendorListUI) {
        this.vendorListUI = vendorListUI;
    }

    private MenuVendor menuVendor;

    public MenuVendor getMenuVendor() {
        return menuVendor;
    }

    public void setMenuVendor(MenuVendor menuVendor) {
        this.menuVendor = menuVendor;
    }

    private PerformanceVendor performanceVendor;

    public PerformanceVendor getPerformanceVendor() {
        return performanceVendor;
    }

    public void setPerformanceVendor(PerformanceVendor vendorPerformance) {
        this.performanceVendor = vendorPerformance;
    }

}
