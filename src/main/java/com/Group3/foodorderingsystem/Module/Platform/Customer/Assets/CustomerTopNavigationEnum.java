package com.Group3.foodorderingsystem.Module.Platform.Customer.Assets;

import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;

public enum CustomerTopNavigationEnum {

    Home(
            "Home",
            () -> {
                CustomerViewModel.navigate(CustomerViewModel.getHomeViewModel().getNode());
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
                CustomerViewModel.navigate(CustomerViewModel.getSettingsPage());
            });

    private String title;
    private Runnable action;

    private CustomerTopNavigationEnum(String title, Runnable action) {
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
