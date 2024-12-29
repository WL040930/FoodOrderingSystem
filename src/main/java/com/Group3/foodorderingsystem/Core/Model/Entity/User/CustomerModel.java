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

    @Override
    public CustomerModel setId (String id) {
        super.setId(id);
        return this;
    }

    @Override
    public CustomerModel setName (String name) {
        super.setName(name);
        return this;
    }

    @Override
    public CustomerModel setEmail (String email) {
        super.setEmail(email);
        return this;
    }

    @Override
    public CustomerModel setPassword (String password) {
        super.setPassword(password);
        return this;
    }

    @Override
    public CustomerModel setRole (RoleEnum role) {
        super.setRole(role);
        return this;
    }

    public static Object builder() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'builder'");
    }
}
