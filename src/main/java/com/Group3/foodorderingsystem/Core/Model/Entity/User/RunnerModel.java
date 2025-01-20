package com.Group3.foodorderingsystem.Core.Model.Entity.User;

import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.RunnerStatusEnum;

public class RunnerModel extends User {
    

    public RunnerModel() {
        super(RoleEnum.RUNNER);
        this.status = RunnerStatusEnum.UNAVAILABLE;
    }

    private String phoneNumber;
    private double revenue; 
    private RunnerStatusEnum status;
    

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getRevenue() {
        return revenue;
    }

    public RunnerStatusEnum getStatus() {
        return status;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public void setStatus(RunnerStatusEnum status) {
        this.status = status;
    }

}
