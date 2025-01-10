package com.Group3.foodorderingsystem.Module.Platform.Admin.Assets;

import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;

public enum AdminTopNavigationEnum {

    Register(
            "Register",
            () -> {
                AdminViewModel.navigate(AdminViewModel.getRegisterViewModel().getNode());
            }),
    Database(
            "Database",
            () -> {
                AdminViewModel.navigate(AdminViewModel.getDatabaseViewModel().getNode());
            }),
    Finance(
            "Finance",
            () -> {
                AdminViewModel.navigate(AdminViewModel.getFinanceViewModel().getNode());
            }),
    Notification(
            "Notification",
            () -> {
                AdminViewModel.navigate(AdminViewModel.getNotificationViewModel().getNode());
            }),
    SendNoti(
            "semd noti",
            () -> {
            }),
    Settings(
            "Settings",
            () -> {
                AdminViewModel.navigate(AdminViewModel.getSettingsViewModel().getNode());
            }), 
    Requests(
            "Requests",
            () -> {
                AdminViewModel.navigate(AdminViewModel.getRequestsViewModel().getNode());
            });

    private String title;
    private Runnable action;

    private AdminTopNavigationEnum(String title, Runnable action) {
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
