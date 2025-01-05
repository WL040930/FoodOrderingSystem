package com.Group3.foodorderingsystem.Module.Platform.Customer.Home.model;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.ui.HomeUI;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.ui.MenuSelectionUI;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.ui.ShopSelection;

import javafx.scene.Node;

public class HomeViewModel extends ViewModelConfig {

    @Override
    public void navigate(Node node) {
        setNode(node);
        CustomerViewModel.navigate(CustomerViewModel.getHomeViewModel().getNode());
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

    private MenuSelectionUI menuSelectionUI;

    public MenuSelectionUI getMenuSelectionUI() {
        return menuSelectionUI;
    }

    public void setMenuSelectionUI(MenuSelectionUI menuSelectionUI) {
        this.menuSelectionUI = menuSelectionUI;
    }

    public void initMenuSelection(String vendorId) {
        this.menuSelectionUI = new MenuSelectionUI(vendorId);
        navigate(menuSelectionUI);
    }
}
