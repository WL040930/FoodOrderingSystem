package com.Group3.foodorderingsystem;

import com.Group3.foodorderingsystem.Core.Storage.Storage;
import com.Group3.foodorderingsystem.Module.Common.Login.LoginPage;
import javafx.application.Application;

public class FoodOrderingSystem {

    public static void main(String[] args) {
        Storage.init();

        Application.launch(LoginPage.class, args);
    }
}
