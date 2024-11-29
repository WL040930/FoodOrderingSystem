package com.Group3.foodorderingsystem.Core.Storage;

public enum StorageEnum {
    
    USER(
        "user.txt"
    ), 
    FOOD(
        "food.txt"
    ),
    ORDER(
        "order.txt"
    );

    private final String fileName;

    StorageEnum(String fileName) {
        this.fileName = fileName;
    }

    
}
