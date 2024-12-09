package com.Group3.foodorderingsystem.Core.Model.Entity;

import com.Group3.foodorderingsystem.Core.Model.Abstract.User;
import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;

public class RunnerModel extends User {
    

    public RunnerModel() {
        super(RoleEnum.RUNNER);
    }

    private String phoneNumber;
    private String revenue; 
    private StatusEnum status; // NOTE: CHECK NEED THIS OR NOT, ADMIN WILL NEED TO APPROVE THE RUNNER DOCS/LICENSE BEFORE APPROVAL

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
