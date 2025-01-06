package com.Group3.foodorderingsystem.Module.Platform.Runner.Assets;

public enum RunnerTopNavigationEnum {

    Home(
            "Home",
            () -> {
                // RunnerViewModel.navigate(CustomerViewModel.getHomeViewModel().getNode());
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
            }),
    Settings(
            "Settings",
            () -> {
                // CustomerViewModel.navigate(CustomerViewModel.getSettingsViewModel().getNode());
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
