package com.Group3.foodorderingsystem.Module.Platform.Manager.Assets;

import java.util.Arrays;
import java.util.List;

public enum ManagerNavigationEnum {

    Home(
            "Home",
            "home.png",
            Arrays.asList(
                    ManagerTopNavigationEnum.Complain)),
    Order(
            "Order",
            "logo.png",
            Arrays.asList(
                    ManagerTopNavigationEnum.Order)),
    Notification(
            "Notification",
            "logo.png",
            Arrays.asList(
                    ManagerTopNavigationEnum.Notification)),
    Settings(
            "Settings",
            "settings.png",
            Arrays.asList(
                    ManagerTopNavigationEnum.Settings,
                    ManagerTopNavigationEnum.Finance));

    private final String title;
    private final String icon;
    private final List<ManagerTopNavigationEnum> topNavigationItem;

    ManagerNavigationEnum(String title, String icon, List<ManagerTopNavigationEnum> topNavigationItem) {
        this.title = title;
        this.icon = icon;
        this.topNavigationItem = topNavigationItem;
    }

    public String getTitle() {
        return title;
    }

    public String getIcon() {
        return icon;
    }

    public List<ManagerTopNavigationEnum> getTopNavigationItem() {
        return topNavigationItem;
    }
}
