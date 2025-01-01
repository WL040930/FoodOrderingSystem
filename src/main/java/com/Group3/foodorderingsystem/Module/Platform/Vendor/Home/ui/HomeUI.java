package com.Group3.foodorderingsystem.Module.Platform.Vendor.Home.ui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HomeUI extends VBox {

    public HomeUI() {
        super();
        init();
    }

    public void init() {
        Label welcomeLabel = new Label("Welcome to the Food Ordering System!");
        Button orderButton = new Button("Place an Order");
        Button historyButton = new Button("Order History");

        orderButton.setOnAction(event -> {
            // Handle order button click
        });

        historyButton.setOnAction(event -> {
            // Handle history button click
        });

        this.getChildren().addAll(welcomeLabel, orderButton, historyButton);

    }
}
