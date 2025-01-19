package com.Group3.foodorderingsystem.Core.Model.Entity.User;

import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private RoleEnum role;
    private String profilePicture; 

    public User() {
    }

    public User (RoleEnum role) {
        this.role = role;
    }

    public User(String id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = RoleEnum.valueOf(role);
    }

    public User(String id, String name, String email, String password, String role, String fileName) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = RoleEnum.valueOf(role);
        this.profilePicture = fileName;
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

    public User setProfilePicture(String fileName) {
        this.profilePicture = fileName;
        return this;
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

    public String getProfilePicture() {
        return profilePicture;
    }

    public static String getDefaultProfilePicture(RoleEnum role) {
        switch (role) {
            case CUSTOMER:
                return "customer.png";
            case VENDOR:
                return "vendor.png";
            case RUNNER:
                return "runner.png";
            case ADMIN:
            case MANAGER:
                return "admin.png";
            default:
                return "customer.png";
        }
    }
}
