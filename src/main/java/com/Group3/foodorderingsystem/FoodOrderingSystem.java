package com.Group3.foodorderingsystem;

import com.Group3.foodorderingsystem.Core.Storage.Storage;
import com.Group3.foodorderingsystem.Module.Register.RegisterRoleSelection;

public class FoodOrderingSystem {

    public static void main(String[] args) {
        Storage.init();
        
        RegisterRoleSelection registerRoleSelection = new RegisterRoleSelection();
        registerRoleSelection.setVisible(true);
    }
}
