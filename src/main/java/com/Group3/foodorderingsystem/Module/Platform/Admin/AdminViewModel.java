package com.Group3.foodorderingsystem.Module.Platform.Admin;

import com.Group3.foodorderingsystem.Core.Widget.AdminNavigator;

public class AdminViewModel {
    public static AdminViewModel instance;

    public AdminNavigator adminNavigator; 
    public AdminPanel adminPanel = AdminPanel.ACCOUNT_MANAGEMENT; 

    public void init() {
        adminPanel = AdminPanel.ACCOUNT_MANAGEMENT;
        instance = this; 

        adminNavigator = new AdminNavigator();

    }
}