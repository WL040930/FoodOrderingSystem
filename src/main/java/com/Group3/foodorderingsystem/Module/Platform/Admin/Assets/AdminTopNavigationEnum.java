package com.Group3.foodorderingsystem.Module.Platform.Admin.Assets;

import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;

public enum AdminTopNavigationEnum {

    Register(
            "Register",
            () -> {
                AdminViewModel.navigate(AdminViewModel.getRegisterViewModel().getNode());
            }),
    UserDatabase(
            "User Database",
            () -> {
                AdminViewModel.navigate(AdminViewModel.getAdminDatabase());
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
                AdminViewModel.navigate(AdminViewModel.getSettingsPage());
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
