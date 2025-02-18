package com.Group3.foodorderingsystem.Module.Platform.Manager.Assets;

import com.Group3.foodorderingsystem.Module.Platform.Manager.ManagerViewModel;

public enum ManagerTopNavigationEnum {

    Complain(
            "Complain",
            () -> {
                ManagerViewModel.navigate(ManagerViewModel.getComplainViewModel().getNode());
            }),
    Order(
            "Order",
            () -> {
                // CustomerViewModel.navigate(CustomerViewModel.getOrderViewModel().getNode());
            }),

    Notification(
            "Notification",
            () -> {
            }),
    Settings(
            "Settings",
            () -> {
                // RunnerViewModel.navigate(RunnerViewModel.getSettingsViewModel().getNode());
            }),
    Finance(
            "Finance",
            () -> {
                // RunnerViewModel.navigate(RunnerViewModel.getTransactionViewModel().getNode());
            });

    private String title;
    private Runnable action;

    private ManagerTopNavigationEnum(String title, Runnable action) {
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
