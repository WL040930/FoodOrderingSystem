package com.Group3.foodorderingsystem.Core.Services;

import java.util.ArrayList;
import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.RunnerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;
import com.Group3.foodorderingsystem.Core.Storage.Storage;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;

public class UserServices {

    // read file
    public static List<CustomerModel> getCustomers() {
        return FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.CUSTOMER), CustomerModel.class);
    }

    public static List<RunnerModel> getRunners() {
        return FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.RUNNER), RunnerModel.class);
    }

    public static List<VendorModel> getVendors() {
        return FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.VENDOR), VendorModel.class);
    }

    public static List<User> getAdmins() {
        return FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.USER), User.class);
    }

    public static List<User> getUsers() {
        List<User> returnList = new ArrayList<>();

        for (CustomerModel customer : getCustomers()) {
            returnList.add(customer);
        }

        for (RunnerModel runner : getRunners()) {
            returnList.add(runner);
        }

        for (VendorModel vendor : getVendors()) {
            returnList.add(vendor);
        }

        for (User admin : getAdmins()) {
            returnList.add(admin);
        }

        return returnList;
    }

    // for login use
    public static User findUserByEmailAndPassword(String email, String password) {
        for (User user : getUsers()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static User saveUser(User user) {
        if (user.getRole() == RoleEnum.ADMIN || user.getRole() == RoleEnum.MANAGER) {
            List<User> users = getUsers();

            if (user.getId() == null) {
                if (isEmailExist(user.getEmail()) != null) {
                    return null;
                }
                user.setId(Storage.generateNewId());
            } else {
                users.removeIf(u -> u.getId().equals(user.getId()));
            }

            users.add(user);
            FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.USER), users);
            return user;
        }

        return null;
    }

    public static CustomerModel saveUser(CustomerModel customer) {
        // Retrieve the current list of customers
        List<CustomerModel> customers = getCustomers();

        // Check if the customer ID is null (indicating a new customer)
        if (customer.getId() == null) {
            // Check if the email already exists
            if (isEmailExist(customer.getEmail()) != null) {
                // Return null to indicate failure due to duplicate email
                return null;
            }
            // Generate a new ID for the customer
            customer.setId(Storage.generateNewId());
        } else {
            // Remove the existing customer with the same ID (for updates)
            customers.removeIf(c -> c.getId().equals(customer.getId()));
        }

        // Add the new or updated customer to the list
        customers.add(customer);

        // Save the updated list to a file
        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.CUSTOMER), customers);

        // Return the saved customer model
        return customer;
    }

    public static RunnerModel saveUser(RunnerModel runner) {
        List<RunnerModel> runners = getRunners();

        if (runner.getId() == null) {
            if (isEmailExist(runner.getEmail()) != null) {
                return null;
            }
            runner.setId(Storage.generateNewId());
        } else {
            runners.removeIf(r -> r.getId().equals(runner.getId()));
        }

        runners.add(runner);
        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.RUNNER), runners);
        return runner;
    }

    public static VendorModel saveUser(VendorModel vendor) {
        List<VendorModel> vendors = getVendors();

        if (vendor.getId() == null) {
            if (isEmailExist(vendor.getEmail()) != null) {
                return null;
            }
            vendor.setId(Storage.generateNewId());
        } else {
            vendors.removeIf(v -> v.getId().equals(vendor.getId()));
        }

        vendors.add(vendor);
        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.VENDOR), vendors);
        return vendor;
    }

    public static User isEmailExist(String email) {
        for (User user : getUsers()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
}
