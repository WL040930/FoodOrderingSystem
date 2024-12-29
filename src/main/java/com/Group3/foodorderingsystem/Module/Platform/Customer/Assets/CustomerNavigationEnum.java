package com.Group3.foodorderingsystem.Module.Platform.Customer.Assets;

import java.util.Arrays;
import java.util.List;

public enum CustomerNavigationEnum {

    Home(
            "Home",
            "logo.png",
            Arrays.asList(
                    CustomerTopNavigationEnum.Home, 
                    CustomerTopNavigationEnum.CurrentOrder)),
    Order(
            "Order",
            "logo.png",
            Arrays.asList(
                    CustomerTopNavigationEnum.Order)),
    Notification(
            "Notification",
            "logo.png",
            Arrays.asList(
                    CustomerTopNavigationEnum.Notification)),
    Settings(
            "Settings",
            "logo.png",
            Arrays.asList(
                    CustomerTopNavigationEnum.Settings));

    private final String title;
    private final String icon;
    private final List<CustomerTopNavigationEnum> topNavigationItem;

    CustomerNavigationEnum(String title, String icon, List<CustomerTopNavigationEnum> topNavigationItem) {
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

    public List<CustomerTopNavigationEnum> getTopNavigationItem() {
        return topNavigationItem;
    }
}
