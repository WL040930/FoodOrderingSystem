package com.Group3.foodorderingsystem.Module.Platform.Vendor.Menu.ui;

import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MenuList extends VBox {

    public MenuList() {
        super();
        init();
    }

    public void init() {
        Label welcomeLabel = new Label("Welcome to the Food Ordering System!");
        Button orderButton = new Button("mEMI");
        Button historyButton = new Button("Order History");

        orderButton.setOnAction(event -> {
            VendorViewModel.getMenuViewModel().setAddNewItem(new AddNewItem());
            VendorViewModel.getMenuViewModel().navigate(VendorViewModel.getMenuViewModel().getAddNewItem());
        });

        historyButton.setOnAction(event -> {
            // Handle history button click
        });

        this.getChildren().addAll(welcomeLabel, orderButton, historyButton);

    }
}
