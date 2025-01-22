package com.Group3.foodorderingsystem.Module.Platform.Vendor.Assets;

import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;

public enum VendorTopNavigationEnum {

        Home(
                        "Home",
                        () -> {
                                VendorViewModel.navigate(VendorViewModel.getHomeViewModel().getNode());
                        }),
        Menu(
                        "Menu",
                        () -> {
                                VendorViewModel.navigate(VendorViewModel.getMenuViewModel().getNode());
                        }),
        Review(
                        "Review",
                        () -> {
                                VendorViewModel.navigate(VendorViewModel.getReviewViewModel().getNode());
                        }),
        Notification(
                        "Notification",
                        () -> {
                                VendorViewModel.navigate(VendorViewModel.getNotificationViewModel().getNode());
                        }),
        Settings(
                        "Settings",
                        () -> {
                                VendorViewModel.navigate(VendorViewModel.getSettingsViewModel().getNode());
                        }),
        Revenue(
                        "Revenue",
                        () -> {
                                VendorViewModel.navigate(VendorViewModel.getTransactionViewModel().getNode());
                        }),
        Vouchers(
                        "Vouchers",
                        () -> {
                                VendorViewModel.navigate(VendorViewModel.getVoucherViewModel().getNode());
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
