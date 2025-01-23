package com.Group3.foodorderingsystem.Core.Model.Entity.User;

import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;

public class VendorModel extends User {

    public VendorModel() {
        super(RoleEnum.VENDOR);
        isDeleted = false;
    }

    private double revenue;
    private boolean isDeleted; 

    // for shop
    private String shopName;
    private String shopDescription;
    private String shopPhoneNumber;
    private String shopImage;
    private String address;

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public double getRevenue() {
        return revenue;
    }

    public String getAddress() {
        return address;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopDescription() {
        return shopDescription;
    }

    public String getShopPhoneNumber() {
        return shopPhoneNumber;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
    }

    public void setShopPhoneNumber(String shopPhoneNumber) {
        this.shopPhoneNumber = shopPhoneNumber;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
