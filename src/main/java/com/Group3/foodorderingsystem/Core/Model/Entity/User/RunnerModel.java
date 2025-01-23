package com.Group3.foodorderingsystem.Core.Model.Entity.User;

import java.util.ArrayList;
import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.RunnerStatusEnum;

public class RunnerModel extends User {

    public RunnerModel() {
        super(RoleEnum.RUNNER);
        this.status = RunnerStatusEnum.UNAVAILABLE;
        this.isDeleted = false;
    }

    private String phoneNumber;
    private double revenue;
    private RunnerStatusEnum status;
    private boolean isDeleted;
    private List<String> orderIDs = new ArrayList<>();

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getRevenue() {
        return revenue;
    }

    public RunnerStatusEnum getStatus() {
        return status;
    }

    public List<String> getOrderIDs() {
        return orderIDs;
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

    public void setorderIDs(List<String> orderIDs) {
        this.orderIDs = orderIDs;
    }
}
