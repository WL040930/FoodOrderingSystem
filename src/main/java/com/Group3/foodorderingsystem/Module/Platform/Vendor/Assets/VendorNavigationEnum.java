package com.Group3.foodorderingsystem.Module.Platform.Vendor.Assets;

import java.util.Arrays;
import java.util.List;

public enum VendorNavigationEnum {

    Home(
            "Home",
            "logo.png",
            Arrays.asList(
                    VendorTopNavigationEnum.CurrentOrder)),
    Shop(
            "Shop",
            "shop_icon.png",
            Arrays.asList(
                    VendorTopNavigationEnum.Menu, 
                    VendorTopNavigationEnum.Review)),
    Notification(
            "Notification",
            "logo.png",
            Arrays.asList(
                    VendorTopNavigationEnum.Notification)),
    Settings(
            "Settings",
            "settings.png",
            Arrays.asList(
                    VendorTopNavigationEnum.Settings, 
                    VendorTopNavigationEnum.Revenue));

    private final String title;
    private final String icon;
    private final List<VendorTopNavigationEnum> topNavigationItem;

    VendorNavigationEnum(String title, String icon, List<VendorTopNavigationEnum> topNavigationItem) {
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

    public List<VendorTopNavigationEnum> getTopNavigationItem() {
        return topNavigationItem;
    }
}
