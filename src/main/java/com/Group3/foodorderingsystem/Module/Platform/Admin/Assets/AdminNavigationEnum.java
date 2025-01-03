package com.Group3.foodorderingsystem.Module.Platform.Admin.Assets;

import java.util.Arrays;
import java.util.List;

public enum AdminNavigationEnum {

        User(
                        "Users",
                        "logo.png",
                        Arrays.asList(
                                        AdminTopNavigationEnum.Register,
                                        AdminTopNavigationEnum.UserDatabase)),
        Finance(
                        "Finance",
                        "logo.png",
                        Arrays.asList(
                                        AdminTopNavigationEnum.Finance)),
        Notification(
                        "Notification",
                        "logo.png",
                        Arrays.asList(
                                        AdminTopNavigationEnum.Noti,
                                        AdminTopNavigationEnum.SendNoti)),
        Settings(
                        "Settings",
                        "settings.png",
                        Arrays.asList(
                                        AdminTopNavigationEnum.SelfSettings));

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
