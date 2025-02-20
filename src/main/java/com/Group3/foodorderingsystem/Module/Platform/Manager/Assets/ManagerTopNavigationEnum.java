package com.Group3.foodorderingsystem.Module.Platform.Manager.Assets;

import com.Group3.foodorderingsystem.Module.Platform.Manager.ManagerViewModel;

public enum ManagerTopNavigationEnum {

    Complain(
            "Complain",
            () -> {
                ManagerViewModel.navigate(ManagerViewModel.getComplainViewModel().getNode());
            }),
    Vendor(
            "Vendor",
            () -> {
                ManagerViewModel.navigate(ManagerViewModel.getVendorPerformanceViewModel().getNode());
            }),
    Runner(
            "Runner",
            () -> {
                ManagerViewModel.navigate(ManagerViewModel.getRunnerPerformanceViewModel().getNode());
            }),
    Settings(
            "Settings",
            () -> {
                ManagerViewModel.navigate(ManagerViewModel.getManagerSettingsViewModel().getNode());
            });

    private String title;
    private Runnable action;

    private ManagerTopNavigationEnum(String title, Runnable action) {
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
