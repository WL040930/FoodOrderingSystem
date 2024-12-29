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

    /**
     * Retrieves a list of all customers.
     * 
     * @return List<CustomerModel> - List of CustomerModel objects representing all
     *         customers.
     */
    public static List<CustomerModel> getCustomers() {
        return FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.CUSTOMER), CustomerModel.class);
    }

    /**
     * Retrieves a list of all runners.
     * 
     * @return List<RunnerModel> - List of RunnerModel objects representing all
     *         runners.
     */
    public static List<RunnerModel> getRunners() {
        return FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.RUNNER), RunnerModel.class);
    }

    /**
     * Retrieves a list of all vendors.
     * 
     * @return List<VendorModel> - List of VendorModel objects representing all
     *         vendors.
     */
    public static List<VendorModel> getVendors() {
        return FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.VENDOR), VendorModel.class);
    }

    /**
     * Retrieves a list of all users with the role of Admin.
     * 
     * @return List<User> - List of User objects representing all admins.
     */
    public static List<User> getAdmins() {
        return FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.USER), User.class);
    }

    /**
     * Retrieves a combined list of all users, including customers, runners,
     * vendors, and admins.
     * 
     * @return List<User> - List of User objects representing all types of users.
     */
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

    /**
     * Finds and returns a user by email and password.
     * 
     * @param email    - The email of the user to be found.
     * @param password - The password of the user to be found.
     * @return User - The User object if found, or null if not found.
     */
    public static User findUserByEmailAndPassword(String email, String password) {
        for (User user : getUsers()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Saves or updates a User object, specifically for users with ADMIN or MANAGER
     * roles.
     * 
     * @param user - The User object to be saved or updated.
     * @return User - The saved User object if successful, or null if the email
     *         already exists.
     */
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

    /**
     * Saves or updates a CustomerModel object.
     * 
     * @param customer - The CustomerModel object to be saved or updated.
     * @return CustomerModel - The saved CustomerModel object if successful, or null
     *         if the email already exists.
     */
    public static CustomerModel saveUser(CustomerModel customer) {
        List<CustomerModel> customers = getCustomers();

        if (customer.getId() == null) {
            if (isEmailExist(customer.getEmail()) != null) {
                return null;
            }
            customer.setId(Storage.generateNewId());
        } else {
            customers.removeIf(c -> c.getId().equals(customer.getId()));
        }

        customers.add(customer);
        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.CUSTOMER), customers);
        return customer;
    }

    /**
     * Saves or updates a RunnerModel object.
     * 
     * @param runner - The RunnerModel object to be saved or updated.
     * @return RunnerModel - The saved RunnerModel object if successful, or null if
     *         the email already exists.
     */
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

    /**
     * Saves or updates a VendorModel object.
     * 
     * @param vendor - The VendorModel object to be saved or updated.
     * @return VendorModel - The saved VendorModel object if successful, or null if
     *         the email already exists.
     */
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

    /**
     * Checks if an email already exists in the system across all user types.
     * 
     * @param email - The email to be checked.
     * @return User - The User object if the email exists, or null if it does not
     *         exist.
     */
    public static User isEmailExist(String email) {
        for (User user : getUsers()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    /**
     * 
     * @param Id 
     * @return CustomerModel
     * 
     */
    public static CustomerModel findCustomerById(String Id) {
        for (CustomerModel customer : getCustomers()) {
            if (customer.getId().equals(Id)) {
                return customer;
            }
        }
        return null;
    }
}
