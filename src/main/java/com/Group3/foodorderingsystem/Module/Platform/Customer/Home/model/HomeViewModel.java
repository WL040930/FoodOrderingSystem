package com.Group3.foodorderingsystem.Module.Platform.Customer.Home.model;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.ui.HomeUI;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.ui.ShopSelection;

import javafx.scene.Node;

public class HomeViewModel extends ViewModelConfig {

    @Override
    protected void navigate(Node node) {
        
    }
    
    public HomeViewModel() {
        super();
        shopSelection = new ShopSelection();
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

    private ShopSelection shopSelection;

    public ShopSelection getShopSelection() {
        return shopSelection;
    }

    public void setShopSelection(ShopSelection shopSelection) {
        this.shopSelection = shopSelection;
    }
}
