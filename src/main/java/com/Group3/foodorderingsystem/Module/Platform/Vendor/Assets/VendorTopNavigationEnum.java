package com.Group3.foodorderingsystem.Module.Platform.Vendor.Assets;

import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;

public enum VendorTopNavigationEnum {

    Home(
            "Home",
            () -> {
                VendorViewModel.navigate(VendorViewModel.getHomeViewModel().getNode());
            }),
    CurrentOrder(
            "Current Order",
            () -> {
            }),
    Order(
            "Order",
            () -> {
            }),

    Notification(
            "Notification",
            () -> {
            }),
    Settings(
            "Settings",
            () -> {
                VendorViewModel.navigate(VendorViewModel.getSettingsPage());
            });

    private String title;
    private Runnable action;

    private VendorTopNavigationEnum(String title, Runnable action) {
        this.title = title;
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public Runnable getAction() {
        return action;
    }
}
