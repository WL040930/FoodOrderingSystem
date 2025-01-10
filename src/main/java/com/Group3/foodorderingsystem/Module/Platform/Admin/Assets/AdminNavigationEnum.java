package com.Group3.foodorderingsystem.Module.Platform.Admin.Assets;

import java.util.Arrays;
import java.util.List;

public enum AdminNavigationEnum {

        User(
                        "Users",
                        "user.png",
                        Arrays.asList(
                                        AdminTopNavigationEnum.Database,
                                        AdminTopNavigationEnum.Register)),
        Finance(
                        "Finance",
                        "financial.png",
                        Arrays.asList(
                                        AdminTopNavigationEnum.Finance, 
                                        AdminTopNavigationEnum.Requests)),
        Notification(
                        "Notification",
                        "notification.png",
                        Arrays.asList(
                                        AdminTopNavigationEnum.Notification)),
        Settings(
                        "Settings",
                        "settings.png",
                        Arrays.asList(
                                        AdminTopNavigationEnum.Settings));

        private final String title;
        private final String icon;
        private final List<AdminTopNavigationEnum> topNavigationItem;

        AdminNavigationEnum(String title, String icon, List<AdminTopNavigationEnum> topNavigationItem) {
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

        public List<AdminTopNavigationEnum> getTopNavigationItem() {
                return topNavigationItem;
        }

}
