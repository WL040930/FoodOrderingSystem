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

            }),
    Noti(
            "Notification",
            () -> {

            }),
    SendNoti(
            "semd noti",
            () -> {
            }),
    SelfSettings(
            "Self Settings",
            () -> {
                AdminViewModel.navigate(AdminViewModel.getSettingsViewModel().getNode());
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
