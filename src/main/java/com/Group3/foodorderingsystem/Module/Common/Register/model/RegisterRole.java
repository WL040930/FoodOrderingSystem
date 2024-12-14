package com.Group3.foodorderingsystem.Module.Common.Register.model;

public enum RegisterRole {
    
    Customer(
        "customer.png", 
        "Customer is the one who orders food from the shop",
        "Customer", 
        3
    ), 
    Vendor(
        "vendor.png", 
        "Vendor is the one who owns the shop",
        "Vendor", 
        7
    ),
    Runner(
        "runner.png", 
        "Runner is the one who delivers the food to the customer",
        "Runner", 
        5
    ); 

    public String imagePath; 
    public String description; 
    public String roleName; 
    public int maxVal;

    private RegisterRole(String imagePath, String description, String roleName, int maxVal) {
        this.imagePath = imagePath; 
        this.description = description; 
        this.roleName = roleName; 
        this.maxVal = maxVal;
    }
}
