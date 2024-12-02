package com.Group3.foodorderingsystem.Core.Storage;

public enum StorageEnum {
    
    CUSTOMER(
        "customer.txt"
    ), 
    RUNNER(
        "runner.txt"
    ),
    VENDOR(
        "vendor.txt"
    );

    final String fileName;

    StorageEnum(String fileName) {
        this.fileName = fileName;
    }   
}
