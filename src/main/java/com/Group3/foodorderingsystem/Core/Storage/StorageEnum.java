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
    ),
    ITEM(
        "item.txt"
    ), 
    USER(
        "user.txt"
    ),
    ORDER(
        "order.txt"
    ),
    VOUCHER(
        "voucher.txt"
    ), 
    TRANSACTION(
        "transaction.txt"
    ), 
    TOPUPWITHDRAW(
        "topupwithdraw.txt"
    ), 
    NOTIFICATION(
        "notification.txt"
    ),
    COMPLAIN(
        "complain.txt"
    );

    final String fileName;

    StorageEnum(String fileName) {
        this.fileName = fileName;
    }   

    public static String getFileName(StorageEnum storageEnum) {
        return Storage.DIRECTORY_PATH + '/' + storageEnum.fileName;
    }
}
