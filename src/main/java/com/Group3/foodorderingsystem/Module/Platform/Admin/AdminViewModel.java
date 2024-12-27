package com.Group3.foodorderingsystem.Module.Platform.Admin;

public class AdminViewModel {
    public static AdminViewModel instance;

    public AdminMainFrame adminMainFrame; 

    public AdminViewModel() {
        instance = this;
        instance.adminMainFrame = new AdminMainFrame();
    }
}
