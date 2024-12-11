package com.Group3.foodorderingsystem.Core.Model.Entity;

public class ShopModel {
    
    private String shopId;
    private String shopName;
    private String shopDescription;
    private String shopAddress;
    private String shopPhoneNumber;
    private String shopImage;
    // consider to add Vendor, since Vendor oversees the shop 

    public String getShopId() {
        return shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopDescription() {
        return shopDescription;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public String getShopPhoneNumber() {
        return shopPhoneNumber;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public void setShopPhoneNumber(String shopPhoneNumber) {
        this.shopPhoneNumber = shopPhoneNumber;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

}
