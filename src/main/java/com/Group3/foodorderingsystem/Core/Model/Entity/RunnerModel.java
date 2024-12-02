package com.Group3.foodorderingsystem.Core.Model.Entity;

import com.Group3.foodorderingsystem.Core.Model.Abstract.User;
import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;

public class RunnerModel extends User {
    

    public RunnerModel() {
        super(RoleEnum.RUNNER);
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
