package com.Group3.foodorderingsystem.Core.Model.Entity.User;

import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;

public class RunnerModel extends User {
    

    public RunnerModel() {
        super(RoleEnum.RUNNER);
    }

    private String phoneNumber;
    private double revenue; 

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

}
