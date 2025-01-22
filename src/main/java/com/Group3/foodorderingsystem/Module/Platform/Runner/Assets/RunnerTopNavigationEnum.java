package com.Group3.foodorderingsystem.Module.Platform.Runner.Assets;

import com.Group3.foodorderingsystem.Module.Platform.Runner.RunnerViewModel;

public enum RunnerTopNavigationEnum {

    Home(
            "Home",
            () -> {
                RunnerViewModel.navigate(RunnerViewModel.getHomeViewModel().getNode());
            }),
    CurrentOrder(
            "Current Order",
            () -> {
            }),
    Order(
            "Order",
            () -> {
                // CustomerViewModel.navigate(CustomerViewModel.getOrderViewModel().getNode());
            }),

    Notification(
            "Notification",
            () -> {
                RunnerViewModel.navigate(RunnerViewModel.getNotificationViewModel().getNode());
            }),
    Settings(
            "Settings",
            () -> {
                RunnerViewModel.navigate(RunnerViewModel.getSettingsViewModel().getNode());
            }), 
    Finance(
            "Finance",
            () -> {
                RunnerViewModel.navigate(RunnerViewModel.getTransactionViewModel().getNode());
            });

    private String title;
    private Runnable action;

    private RunnerTopNavigationEnum(String title, Runnable action) {
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
