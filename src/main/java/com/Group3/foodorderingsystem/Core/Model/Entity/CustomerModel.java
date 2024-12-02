package com.Group3.foodorderingsystem.Core.Model.Entity;

import com.Group3.foodorderingsystem.Core.Model.Abstract.User;
import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;

public class CustomerModel extends User {

    public CustomerModel() {
        super(RoleEnum.CUSTOMER); 
    }

    private double balance;
    private String phoneNumber;
    private String address; 

    public double getBalance() {
        return balance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
