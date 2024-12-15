package com.Group3.foodorderingsystem.Core.Model.Entity;

import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;

public class VendorModel extends User {
    
    public VendorModel() {
        super(RoleEnum.VENDOR);
    }

    private double revenue; 
    private String address;
    private StatusEnum status;
    private String shopImage;
    private String shopName;
     // NOTE: CHECK NEED THIS OR NOT, ADMIN WILL NEED TO APPROVE THE VENDOR DOCS/LICENSE BEFORE APPROVAL

    public double getRevenue() {
        return revenue;
    }

    public String getAddress() {
        return address;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public String getShopImage() {
        return shopImage;
    }

    public String getShopName() {
        return shopName;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

}
