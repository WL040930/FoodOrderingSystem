package com.Group3.foodorderingsystem.Module.Platform.Customer.Assets;

import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;

public enum CustomerTopNavigationEnum {

    Home(
            "Home",
            () -> {
                CustomerViewModel.navigate(CustomerViewModel.getHomeViewModel().getNode());
            }),
    Order(
            "Order",
            () -> {
                CustomerViewModel.navigate(CustomerViewModel.getOrderViewModel().getNode());
            }),

    Notification(
            "Notification",
            () -> {
                CustomerViewModel.navigate(CustomerViewModel.getNotificationViewModel().getNode());
            }),
    Settings(
            "Settings",
            () -> {
                CustomerViewModel.navigate(CustomerViewModel.getSettingsViewModel().getNode());
            }),
    Finance(
            "Finance",
            () -> {
                CustomerViewModel.navigate(CustomerViewModel.getTransactionViewModel().getNode());
            }
    );

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
