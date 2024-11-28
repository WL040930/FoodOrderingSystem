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

    public double getBalance() {
        return balance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
