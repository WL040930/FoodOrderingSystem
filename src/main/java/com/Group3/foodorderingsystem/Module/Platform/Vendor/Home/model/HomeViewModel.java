package com.Group3.foodorderingsystem.Module.Platform.Vendor.Home.model;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.ui.HomeUI;

import javafx.scene.Node;

public class HomeViewModel extends ViewModelConfig {

    @Override
    protected void navigate(Node node) {
        // TODO Auto-generated method stub
    }

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
