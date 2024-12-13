package com.Group3.foodorderingsystem.Core.Model.Enum;

public enum SessionEnum {
    ITEMS("items"),
    CUSTOMER("customer"),
    ADMIN("admin"),
    VENDOR("vendor"),
    RIDER("rider");

    private final String key;

    SessionEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}