package com.Group3.foodorderingsystem.Module.Common.settings.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.RunnerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Core.Storage.Storage;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.BottomButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.FileUploadType;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.FileUploadWidget;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.PopupMessage;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.TitleTextField;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.TitleTextFieldEnum;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Runner.RunnerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Separator;

public class SettingsProfileManagement extends BaseContentPanel {

    private User user;

    private TitleTextField name;
    private TitleTextField password;
    private FileUploadWidget profilePicture;

    // for customer
    private TitleTextField address;

    // for customer and runner
    private TitleTextField phoneNumber;

    public SettingsProfileManagement(User user) {
        super();

        this.user = user;

        setHeader(header());
        setContent(content());
        setFooter(footer());
    }

    private Node header() {
        return new TitleBackButton("Profile Management", () -> {
            switch (user.getRole()) {
                case ADMIN:
                    AdminViewModel.getSettingsViewModel()
                            .navigate(AdminViewModel.getSettingsViewModel().getSettingsPage());
                    break;
                case CUSTOMER:
                    CustomerViewModel.getSettingsViewModel()
                            .navigate(CustomerViewModel.getSettingsViewModel().getSettingsPage());
                    break;
                case VENDOR:
                    VendorViewModel.getSettingsViewModel()
                            .navigate(VendorViewModel.getSettingsViewModel().getSettingsPage());
                    break;
                case RUNNER:
                    RunnerViewModel.getSettingsViewModel()
                            .navigate(RunnerViewModel.getSettingsViewModel().getSettingsPage());
                    break;
                default:
                    break;
            }
        });
    }

    private Node content() {
        VBox content = new VBox(10);
        content.setStyle("-fx-padding: 10px;");

        VBox fixedContent = new VBox(10);
        // Email Section
        Label title = new Label("Email");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        Label email = new Label(user.getEmail());

        // Role Section
        Label role = new Label("Role");
        role.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        Label roleValue = new Label(user.getRole().toString());

        fixedContent.getChildren().addAll(title, email, role, roleValue);

        // Profile Picture and Form Fields
        profilePicture = new FileUploadWidget(FileUploadType.CIRCLE_AVATAR, user.getProfilePicture());
        name = new TitleTextField("Name", user.getName(), TitleTextFieldEnum.TextField);
        password = new TitleTextField("Password", "******", TitleTextFieldEnum.PasswordField);

        HBox profileContent = new HBox(30);
        Separator separator = new Separator();
        separator.setOrientation(javafx.geometry.Orientation.VERTICAL);

        profileContent.getChildren().addAll(profilePicture, separator, fixedContent);

        // Add all elements to the VBox
        content.getChildren().addAll(
                profileContent,
                name, password);

        switch (user.getRole()) {
            case CUSTOMER:
                CustomerModel customer = UserServices.findCustomerById(user.getId());

                address = new TitleTextField("Address", customer.getAddress(), TitleTextFieldEnum.TextArea);
                phoneNumber = new TitleTextField("Phone Number", customer.getPhoneNumber(),
                        TitleTextFieldEnum.TextField);

                content.getChildren().addAll(address, phoneNumber);
                break;

            case RUNNER:
                RunnerModel runner = UserServices.findRunnerById(user.getId());

                phoneNumber = new TitleTextField("Phone Number", runner.getPhoneNumber(), TitleTextFieldEnum.TextField);

                content.getChildren().addAll(phoneNumber);
            default:
                break;
        }

        return content;
    }

    private Node footer() {
        return new BottomButton("Save", () -> {
            switch (user.getRole()) {
                case ADMIN:
                    User user = new User();

                    user.setId(this.user.getId());
                    user.setEmail(this.user.getEmail());
                    user.setName(name.getInputValue() != "" ? name.getInputValue() : this.user.getName());
                    user.setPassword(
                            password.getInputValue() != "" ? password.getInputValue() : this.user.getPassword());
                    user.setProfilePicture(profilePicture.getSelectedFile() != null
                            ? Storage.saveFile(profilePicture.getSelectedFile())
                            : this.user.getProfilePicture());
                    user.setRole(this.user.getRole());

                    User saveUser = UserServices.saveUser(user);
                    if (saveUser != null) {
                        PopupMessage.showMessage("Profile updated successfully!", "success", () -> {
                            AdminViewModel.initSettingsViewModel();
                            AdminViewModel.getSettingsViewModel()
                                    .navigate(AdminViewModel.getSettingsViewModel().getNode());
                        });
                    } else {
                        PopupMessage.showMessage("An error occured, please try again", "error", () -> {
                        });
                    }

                    break;
                case CUSTOMER:
                    CustomerModel customer = UserServices.findCustomerById(this.user.getId());

                    customer.setId(this.user.getId());
                    customer.setEmail(this.user.getEmail());
                    customer.setName(name.getInputValue() != "" ? name.getInputValue() : this.user.getName());
                    customer.setPassword(
                            password.getInputValue() != "" ? password.getInputValue() : this.user.getPassword());
                    customer.setProfilePicture(profilePicture.getSelectedFile() != null
                            ? Storage.saveFile(profilePicture.getSelectedFile())
                            : this.user.getProfilePicture());
                    customer.setRole(this.user.getRole());

                    customer.setAddress(
                            address.getInputValue() != "" ? address.getInputValue() : customer.getAddress());
                    customer.setPhoneNumber(
                            phoneNumber.getInputValue() != "" ? phoneNumber.getInputValue()
                                    : customer.getPhoneNumber());

                    CustomerModel saveCustomer = UserServices.saveUser(customer);
                    if (saveCustomer != null) {
                        PopupMessage.showMessage("Profile updated successfully!", "success", () -> {
                            CustomerViewModel.initSettingsViewModel();
                            CustomerViewModel.getSettingsViewModel()
                                    .navigate(CustomerViewModel.getSettingsViewModel().getNode());
                        });
                    } else {
                        PopupMessage.showMessage("An error occured, please try again", "error", () -> {
                        });
                    }
                    break;
                case VENDOR:
                    VendorModel vendor = UserServices.findVendorById(this.user.getId());
                    vendor.setId(this.user.getId());
                    vendor.setEmail(this.user.getEmail());
                    vendor.setName(name.getInputValue() != "" ? name.getInputValue() : this.user.getName());
                    vendor.setPassword(
                            password.getInputValue() != "" ? password.getInputValue() : this.user.getPassword());
                    vendor.setProfilePicture(profilePicture.getSelectedFile() != null
                            ? Storage.saveFile(profilePicture.getSelectedFile())
                            : this.user.getProfilePicture());
                    vendor.setRole(this.user.getRole());

                    VendorModel saveVendor = UserServices.saveUser(vendor);

                    if (saveVendor != null) {
                        PopupMessage.showMessage("Profile updated successfully!", "success", () -> {
                            VendorViewModel.initSettingsViewModel();
                            VendorViewModel.getSettingsViewModel()
                                    .navigate(VendorViewModel.getSettingsViewModel().getNode());
                        });
                    } else {
                        PopupMessage.showMessage("An error occured, please try again", "error", () -> {
                        });
                    }
                    break;
                case RUNNER:
                    RunnerModel runner = UserServices.findRunnerById(this.user.getId());
                    runner.setId(this.user.getId());
                    runner.setEmail(this.user.getEmail());
                    runner.setName(name.getInputValue() != "" ? name.getInputValue() : this.user.getName());
                    runner.setPassword(
                            password.getInputValue() != "" ? password.getInputValue() : this.user.getPassword());
                    runner.setProfilePicture(profilePicture.getSelectedFile() != null
                            ? Storage.saveFile(profilePicture.getSelectedFile())
                            : this.user.getProfilePicture());
                    runner.setRole(this.user.getRole());
                    runner.setPhoneNumber(
                            phoneNumber.getInputValue() != "" ? phoneNumber.getInputValue() : runner.getPhoneNumber());

                    RunnerModel saveRunner = UserServices.saveUser(runner);

                    if (saveRunner != null) {
                        PopupMessage.showMessage("Profile updated successfully!", "success", () -> {
                            RunnerViewModel.initSettingsViewModel();
                            RunnerViewModel.getSettingsViewModel()
                                    .navigate(RunnerViewModel.getSettingsViewModel().getNode());
                        });
                    } else {
                        PopupMessage.showMessage("An error occured, please try again", "error", () -> {
                        });
                    }

                    break;
                default:
                    break;
            }
        });
    }
}