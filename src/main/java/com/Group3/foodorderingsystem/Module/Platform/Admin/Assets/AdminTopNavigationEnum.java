package com.Group3.foodorderingsystem.Module.Platform.Admin.Assets;

import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;

public enum AdminTopNavigationEnum {

    Register(
            "Register",
            () -> {
                AdminViewModel.navigate(AdminViewModel.getAdminRegister());
            }),
    UserDatabase(
            "User Database",
            () -> {
                AdminViewModel.navigate(AdminViewModel.getAdminDatabase());
            }),
    Finance(
            "Finance",
            () -> {
                System.out.println(" finance ");
            }),
    Noti(
            "Notification",
            () -> {
                System.out.println("Noti");
            }),
    SendNoti(
            "semd noti",
            () -> {
                System.out.println("send noti");
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
