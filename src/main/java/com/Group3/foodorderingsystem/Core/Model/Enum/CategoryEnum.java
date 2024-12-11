package com.Group3.foodorderingsystem.Core.Model.Enum;

public enum CategoryEnum {
    
    FOOD,
    DRINK, 
    HALAL;
    
    public static CategoryEnum getCategory(String category) {
        switch (category) {
            case "FOOD":
                return FOOD;
            case "DRINK":
                return DRINK;
            case "HALAL":
                return HALAL;
            default:
                return null;
        }
    }
}
