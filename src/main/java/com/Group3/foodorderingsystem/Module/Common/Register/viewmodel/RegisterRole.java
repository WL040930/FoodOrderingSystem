package com.Group3.foodorderingsystem.Module.Common.Register.viewmodel;

public enum RegisterRole {
    
    Customer(
        "customer.png", 
        "Customer is the one who orders food from the shop",
        "Customer"
    ), 
    Vendor(
        "vendor.png", 
        "Vendor is the one who owns the shop",
        "Vendor"
    ),
    Runner(
        "runner.png", 
        "Runner is the one who delivers the food to the customer",
        "Runner"
    ); 

    public String imagePath; 
    public String description; 
    public String roleName; 

    private RegisterRole(String imagePath, String description, String roleName) {
        this.imagePath = imagePath; 
        this.description = description; 
        this.roleName = roleName; 
    }
}
