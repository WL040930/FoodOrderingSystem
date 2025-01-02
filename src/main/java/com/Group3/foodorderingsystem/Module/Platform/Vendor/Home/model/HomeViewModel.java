package com.Group3.foodorderingsystem.Module.Platform.Vendor.Home.model;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.ui.HomeUI;

public class HomeViewModel extends ViewModelConfig {

    public HomeViewModel() {
        super();
    }

    public void init() {
        this.homeUI = new HomeUI();
        setNode(homeUI);
    }

    private HomeUI homeUI;

    public HomeUI getHomeUI() {
        return homeUI;
    }

    public void setHomeUI(HomeUI homeUI) {
        this.homeUI = homeUI;
    }
}
