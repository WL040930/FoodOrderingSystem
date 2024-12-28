package com.Group3.foodorderingsystem.Module.Platform.Admin.Assets;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;

public enum AdminNavigationEnum {

    User(
            "Users",
            "logo.png",
            Arrays.asList(
                    AdminTopNavigationEnum.Register,
                    AdminTopNavigationEnum.UserDatabase),
            () -> AdminViewModel.navigate(AdminViewModel.getRegisterViewModel().getRegisterRoleSelection())),
    Finance(
            "Finance",
            "logo.png",
            Arrays.asList(
                    AdminTopNavigationEnum.Finance),
            () -> {
                
            }),
    Notification(
            "Notification",
            "logo.png",
            Arrays.asList(
                    AdminTopNavigationEnum.Noti, 
                    AdminTopNavigationEnum.SendNoti),
            () -> {
                
            }),
    Settings(
            "Settings",
            "logo.png",
            Arrays.asList(
                    AdminTopNavigationEnum.SelfSettings),
            () -> AdminViewModel.navigate(AdminViewModel.getSettingsPage()));

    private final String title;
    private final String icon;
    private final List<AdminTopNavigationEnum> topNavigationItem;
    private final Runnable action;

    AdminNavigationEnum(String title, String icon, List<AdminTopNavigationEnum> topNavigationItem, Runnable action) {
        this.title = title;
        this.icon = icon;
        this.topNavigationItem = topNavigationItem;
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public String getIcon() {
        return icon;
    }

    public List<AdminTopNavigationEnum> getTopNavigationItem() {
        return topNavigationItem;
    }

    public Runnable getAction() {
        return action;
    }
}
