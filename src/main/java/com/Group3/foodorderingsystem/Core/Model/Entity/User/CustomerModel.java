package com.Group3.foodorderingsystem.Core.Model.Entity.User;

import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
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

    public CustomerModel setBalance(double balance) {
        this.balance = balance;
        return this;
    }

    public CustomerModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public CustomerModel setAddress(String address) {
        this.address = address;
        return this;
    }
}
