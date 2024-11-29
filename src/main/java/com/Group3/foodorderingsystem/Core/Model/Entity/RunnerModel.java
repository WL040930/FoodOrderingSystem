package com.Group3.foodorderingsystem.Core.Model.Entity;

import com.Group3.foodorderingsystem.Core.Model.Abstract.User;

public class RunnerModel extends User {
    

    public RunnerModel() {
    }

    private String phoneNumber;
    private String revenue; 

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

}
