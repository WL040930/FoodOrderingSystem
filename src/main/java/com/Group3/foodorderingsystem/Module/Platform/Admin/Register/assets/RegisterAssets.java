package com.Group3.foodorderingsystem.Module.Platform.Admin.Register.assets;

public enum RegisterAssets {
    
    Customer(
        "customer.png", 
        "Customer", 
        "Customer can order food from the vendors and track the order status."
    ), 
    Vendor(
        "vendor.png", 
        "Vendor", 
        "Vendor can add, update, and delete food items and manage orders."
    ),
    Runner(
        "runner.png", 
        "Runner", 
        "Runner can accept the delivery request and deliver the food to the customers."
    );

    private final String image;
    private final String role;
    private final String description;

    RegisterAssets(String image, String role, String description) {
        this.image = image;
        this.role = role;
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public String getRole() {
        return role;
    }

    public String getDescription() {
        return description;
    }
}
