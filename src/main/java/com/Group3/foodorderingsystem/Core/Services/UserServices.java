package com.Group3.foodorderingsystem.Core.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<User> getBaseUsers() {
        List<User> returnList = new ArrayList<>();

        for (CustomerModel customer : getCustomers().stream().filter(c -> !c.isDeleted())
                .collect(Collectors.toList())) {
            User user = new User();
            user.setId(customer.getId());
            user.setName(customer.getName());
            user.setEmail(customer.getEmail());
            user.setRole(RoleEnum.CUSTOMER);
            user.setProfilePicture(customer.getProfilePicture());
            returnList.add(user);
        }

        for (RunnerModel runner : getRunners().stream().filter(r -> !r.isDeleted()).collect(Collectors.toList())) {
            User user = new User();
            user.setId(runner.getId());
            user.setName(runner.getName());
            user.setEmail(runner.getEmail());
            user.setRole(RoleEnum.RUNNER);
            user.setProfilePicture(runner.getProfilePicture());
            returnList.add(user);
        }

        for (VendorModel vendor : getVendors().stream().filter(v -> !v.isDeleted()).collect(Collectors.toList())) {
            User user = new User();
            user.setId(vendor.getId());
            user.setName(vendor.getName());
            user.setEmail(vendor.getEmail());
            user.setRole(RoleEnum.VENDOR);
            user.setProfilePicture(vendor.getProfilePicture());
            returnList.add(user);
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
            List<User> users = getAdmins();

            if (user.getId() == null) {
                if (isEmailExist(user.getEmail()) != null) {
                    return null;
                }
                user.setId(Storage.generateNewId());
            } else {
                int index = -1;
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getId().equals(user.getId())) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    users.set(index, user); // Replace at the specific index
                }
            }

            if (user.getProfilePicture() == null) {
                user.setProfilePicture(User.getDefaultProfilePicture(user.getRole()));
            }

            if (!users.contains(user)) {
                users.add(user); // Add new user
            }

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
            int index = -1;
            for (int i = 0; i < customers.size(); i++) {
                if (customers.get(i).getId().equals(customer.getId())) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                customers.set(index, customer); // Replace at the specific index
            }
        }

        if (customer.getProfilePicture() == null) {
            customer.setProfilePicture(User.getDefaultProfilePicture(RoleEnum.CUSTOMER));
        }

        if (!customers.contains(customer)) {
            customers.add(customer); // Add new customer
        }

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
            int index = -1;
            for (int i = 0; i < runners.size(); i++) {
                if (runners.get(i).getId().equals(runner.getId())) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                runners.set(index, runner); // Replace at the specific index
            }
        }

        if (runner.getProfilePicture() == null) {
            runner.setProfilePicture(User.getDefaultProfilePicture(RoleEnum.RUNNER));
        }

        if (!runners.contains(runner)) {
            runners.add(runner); // Add new runner
        }

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
            int index = -1;
            for (int i = 0; i < vendors.size(); i++) {
                if (vendors.get(i).getId().equals(vendor.getId())) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                vendors.set(index, vendor); // Replace at the specific index
            }
        }

        if (vendor.getProfilePicture() == null) {
            vendor.setProfilePicture(User.getDefaultProfilePicture(RoleEnum.VENDOR));
        }

        if (!vendors.contains(vendor)) {
            vendors.add(vendor); // Add new vendor
        }

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

    /**
     * 
     * @param Id
     * @return VendorModel
     * 
     */
    public static VendorModel findVendorById(String Id) {
        for (VendorModel vendor : getVendors()) {
            if (vendor.getId().equals(Id)) {
                return vendor;
            }
        }
        return null;
    }

    /**
     * 
     * @param Id
     * @return RunnerModel
     */
    public static RunnerModel findRunnerById(String Id) {
        for (RunnerModel runner : getRunners()) {
            if (runner.getId().equals(Id)) {
                return runner;
            }
        }
        return null;
    }

    /**
     * 
     * @param Id
     * @return User
     * 
     */
    public static User findUserById(String Id) {
        for (User user : getUsers()) {
            if (user.getId().equals(Id)) {
                return user;
            }
        }
        return null;
    }

    public static void deleteUser(String Id) {
        // Load the lists of customers, runners, and vendors
        List<CustomerModel> customers = getCustomers();
        List<RunnerModel> runners = getRunners();
        List<VendorModel> vendors = getVendors();

        // Iterate through customers and mark as deleted if the Id matches
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId().equals(Id)) {
                CustomerModel customer = customers.get(i);
                customer.setDeleted(true); // Mark as deleted
                customers.set(i, customer); // Update the list
                break; // Exit the loop once the customer is found and updated
            }
        }

        // Iterate through runners and mark as deleted if the Id matches
        for (int i = 0; i < runners.size(); i++) {
            if (runners.get(i).getId().equals(Id)) {
                RunnerModel runner = runners.get(i);
                runner.setDeleted(true); // Mark as deleted
                runners.set(i, runner); // Update the list
                break; // Exit the loop once the runner is found and updated
            }
        }

        // Iterate through vendors and mark as deleted if the Id matches
        for (int i = 0; i < vendors.size(); i++) {
            if (vendors.get(i).getId().equals(Id)) {
                VendorModel vendor = vendors.get(i);
                vendor.setDeleted(true); // Mark as deleted
                vendors.set(i, vendor); // Update the list
                break; // Exit the loop once the vendor is found and updated
            }
        }

        // Save the updated lists back to their respective files
        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.CUSTOMER), customers);
        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.RUNNER), runners);
        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.VENDOR), vendors);
    }

}
