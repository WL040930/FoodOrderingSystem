package com.Group3.foodorderingsystem.Core.Services;

import java.util.ArrayList;
import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.RunnerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;

public class UserServices {

    public static List<User> getUsers() {
        List<User> returnList = new ArrayList<>(); 
        
        // Customer 
        for (CustomerModel customer : FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.CUSTOMER), CustomerModel.class)) {
            returnList.add(
                new User(
                    customer.getId(), 
                    customer.getName(), 
                    customer.getEmail(), 
                    customer.getPassword(), 
                    RoleEnum.CUSTOMER));
        }

        // runner
        for (RunnerModel runner : FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.RUNNER), RunnerModel.class)) {
            returnList.add(
                new User(
                    runner.getId(), 
                    runner.getName(), 
                    runner.getEmail(), 
                    runner.getPassword(), 
                    RoleEnum.RUNNER)); 
        }

        // Vendor
        for (RunnerModel runner : FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.VENDOR), RunnerModel.class)) {
            returnList.add(
                new User(
                    runner.getId(), 
                    runner.getName(), 
                    runner.getEmail(), 
                    runner.getPassword(), 
                    RoleEnum.VENDOR)); 
        }
    
        return returnList; 
    }
    
    public static User findUserByEmailAndPassword(String email, String password) {
        for (User user : getUsers()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
