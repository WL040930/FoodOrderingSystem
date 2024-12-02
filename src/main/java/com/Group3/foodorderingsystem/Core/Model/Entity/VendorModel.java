package com.Group3.foodorderingsystem.Core.Model.Entity;

import com.Group3.foodorderingsystem.Core.Model.Abstract.User;
import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;

public class VendorModel extends User {
    
    public VendorModel() {
        super(RoleEnum.VENDOR);
    }

    private double revenue; 
    private String address;

    public double getRevenue() {
        return revenue;
    }

    public String getAddress() {
        return address;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
