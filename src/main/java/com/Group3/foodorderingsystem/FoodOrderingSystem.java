package com.Group3.foodorderingsystem;

import com.Group3.foodorderingsystem.Core.Storage.Storage;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.AddNewItem;

public class FoodOrderingSystem {

    public static void main(String[] args) {
        Storage.init();

        AddNewItem addNewItem = new AddNewItem();
        addNewItem.setVisible(true);
    }
}
