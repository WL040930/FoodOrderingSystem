package com.Group3.foodorderingsystem;

import com.Group3.foodorderingsystem.Core.Storage.Storage;
import com.Group3.foodorderingsystem.Module.Common.Login.Login;

public class FoodOrderingSystem {

    public static void main(String[] args) {
        Storage.init();

        Login login = new Login();
        login.setVisible(true);
    }
}
