package com.Group3.foodorderingsystem.Core.Model.Abstract;

import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;

public abstract class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private RoleEnum role;

    public User() {
    }

    public User (RoleEnum role) {
        this.role = role;
    }

    public User(String id, String name, String email, String password, RoleEnum role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public Boolean isCustomer() {
        return role == RoleEnum.CUSTOMER;
    }

    public Boolean isRunner() {
        return role == RoleEnum.RUNNER;
    }

    public Boolean isVendor() {
        return role == RoleEnum.VENDOR;
    }

    public Boolean isAdmin() {
        return role == RoleEnum.ADMIN;
    }

    public Boolean isManager() {
        return role == RoleEnum.MANAGER;
    }
}
