package com.Group3.foodorderingsystem.Module.Platform.Vendor.Assets;

import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;

public enum VendorTopNavigationEnum {

        Home(
                        "Home",
                        () -> {
                                VendorViewModel.navigate(VendorViewModel.getHomeViewModel().getNode());
                        }),
        CurrentOrder(
                        "Current Order",
                        () -> {
                                // Action for Current Order
                        }),
        Menu(
                        "Menu",
                        () -> {
                                VendorViewModel.navigate(VendorViewModel.getMenuViewModel().getNode());
                        }),
        Review(
                        "Review",
                        () -> {
                                // Action for Review
                        }),
        Notification(
                        "Notification",
                        () -> {
                                // Action for Notification
                        }),
        Settings(
                        "Settings",
                        () -> {
                                VendorViewModel.navigate(VendorViewModel.getSettingsViewModel().getNode());
                        }),
        Revenue(
                        "Revenue",
                        () -> {
                                // Action for Revenue
                        });

        private String title;
        private Runnable action;

        private VendorTopNavigationEnum(String title, Runnable action) {
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
