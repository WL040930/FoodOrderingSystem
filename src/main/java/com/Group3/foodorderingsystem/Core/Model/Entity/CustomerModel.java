package com.Group3.foodorderingsystem.Core.Model.Entity;

import com.Group3.foodorderingsystem.Core.Model.Abstract.User;

public class CustomerModel extends User {

    public CustomerModel() {
    }

    public CustomerModel(String id, String name, String email, String password, double balance, String phoneNumber) {
        super(id, name, email, password);
        this.balance = balance;
        this.phoneNumber = phoneNumber;
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
