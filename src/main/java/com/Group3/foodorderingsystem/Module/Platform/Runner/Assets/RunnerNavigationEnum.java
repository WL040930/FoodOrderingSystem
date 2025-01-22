package com.Group3.foodorderingsystem.Module.Platform.Runner.Assets;

import java.util.Arrays;
import java.util.List;

public enum RunnerNavigationEnum {

    Home(
            "Home",
            "home.png",
            Arrays.asList(
                    RunnerTopNavigationEnum.Home)),
    Notification(
            "Notification",
            "notification.png",
            Arrays.asList(
                    RunnerTopNavigationEnum.Notification)),
    Settings(
            "Settings",
            "settings.png",
            Arrays.asList(
                    RunnerTopNavigationEnum.Settings, 
                    RunnerTopNavigationEnum.Finance));

    private final String title;
    private final String icon;
    private final List<RunnerTopNavigationEnum> topNavigationItem;

    RunnerNavigationEnum(String title, String icon, List<RunnerTopNavigationEnum> topNavigationItem) {
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

    public List<RunnerTopNavigationEnum> getTopNavigationItem() {
        return topNavigationItem;
    }
}
