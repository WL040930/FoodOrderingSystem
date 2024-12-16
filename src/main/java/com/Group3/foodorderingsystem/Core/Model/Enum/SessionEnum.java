package com.Group3.foodorderingsystem.Core.Model.Enum;

public enum SessionEnum {
    ITEMS("items"),
    CUSTOMER("customer"),
    ADMIN("admin"),
    VENDOR("vendor"),
    RIDER("rider"),
    SELECTED_ORDER("selected order"),
    SELECTED_ORDER_TAB("selected order tab"),;

    private final String key;

    SessionEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}