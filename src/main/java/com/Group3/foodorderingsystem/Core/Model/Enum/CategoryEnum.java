package com.Group3.foodorderingsystem.Core.Model.Enum;

public enum CategoryEnum {
    
    FOOD,
    DRINK; 

    public static CategoryEnum getCategory(String category) {
        switch (category) {
            case "FOOD":
                return FOOD;
            case "DRINK":
                return DRINK;
            default:
                return null;
        }
    }
}
