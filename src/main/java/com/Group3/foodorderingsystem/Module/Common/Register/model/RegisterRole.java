package com.Group3.foodorderingsystem.Module.Common.Register.model;

import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;

public enum RegisterRole {
    
    Customer(
        "customer.png", 
        "Customer is the one who orders food from the shop",
        RoleEnum.CUSTOMER, 
        3
    ), 
    Vendor(
        "vendor.png", 
        "Vendor is the one who owns the shop",
        RoleEnum.VENDOR, 
        7
    ),
    Runner(
        "runner.png", 
        "Runner is the one who delivers the food to the customer",
        RoleEnum.RUNNER, 
        5
    ); 

    public String imagePath; 
    public String description; 
    public RoleEnum roleName; 
    public int maxVal;

    private RegisterRole(String imagePath, String description, RoleEnum roleName, int maxVal) {
        this.imagePath = imagePath; 
        this.description = description; 
        this.roleName = roleName; 
        this.maxVal = maxVal;
    }
}
