package com.Group3.foodorderingsystem.Module.Platform.Admin.Database.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.RunnerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.BottomButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.PopupMessage;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.TitleTextField;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.TitleTextFieldEnum;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Separator;

public class EditUserInformation extends BaseContentPanel {

    private User user;
    private CustomerModel customerModel;
    private VendorModel vendorModel;
    private RunnerModel runnerModel;

    private TitleTextField nameField;
    private TitleTextField passwordField;

    // for customer
    private TitleTextField phoneNumber;
    private TitleTextField address;

    // for vendor - shop
    private TitleTextField shopName;
    private TitleTextField shopDescription;
    private TitleTextField shopPhoneNumber;
    private TitleTextField shopAddress;

    public EditUserInformation(User user) {
        super();
        this.user = user;

        initUser();
        setHeader(createHeader());
        setContent(createContent());
        setFooter(createFooter());
    }

    private void initUser() {
        customerModel = UserServices.findCustomerById(user.getId());
        vendorModel = UserServices.findVendorById(user.getId());
        runnerModel = UserServices.findRunnerById(user.getId());
    }

    private Node createHeader() {
        return new TitleBackButton("Edit User Information - " + user.getName(), () -> {
            AdminViewModel.getDatabaseViewModel().navigate(AdminViewModel.getDatabaseViewModel().getAdminDatabase());
        });
    }

    private Node createContent() {
        VBox vbox = new VBox(10);

        ImageView image = Images.getImageView(user.getProfilePicture(), 70, 70);
        Label email = new Label(user.getEmail());

        nameField = new TitleTextField("Name", user.getName(), TitleTextFieldEnum.TextField);
        passwordField = new TitleTextField("Password", "******", TitleTextFieldEnum.PasswordField);

        javafx.scene.control.Separator divider = new Separator();

        vbox.getChildren().addAll(image, email, nameField, passwordField, divider);

        switch (user.getRole()) {
            case CUSTOMER:
                phoneNumber = new TitleTextField("Phone Number", customerModel.getPhoneNumber(),
                        TitleTextFieldEnum.TextField);
                address = new TitleTextField("Address", customerModel.getAddress(), TitleTextFieldEnum.TextArea);
                vbox.getChildren().addAll(phoneNumber, address);
                break;
            case VENDOR:
                ImageView shopImage = Images.getImageView(vendorModel.getShopImage(), 70, 70);
                shopName = new TitleTextField("Shop Name", vendorModel.getShopName(), TitleTextFieldEnum.TextField);
                shopDescription = new TitleTextField("Shop Description", vendorModel.getShopDescription(),
                        TitleTextFieldEnum.TextArea);
                shopPhoneNumber = new TitleTextField("Shop Phone Number", vendorModel.getShopPhoneNumber(),
                        TitleTextFieldEnum.TextField);
                shopAddress = new TitleTextField("Shop Address", vendorModel.getAddress(), TitleTextFieldEnum.TextArea);
                vbox.getChildren().addAll(shopImage, shopName, shopDescription, shopPhoneNumber, shopAddress);
                break;
            case RUNNER:
                phoneNumber = new TitleTextField("Phone Number", runnerModel.getPhoneNumber(),
                        TitleTextFieldEnum.TextField);
                vbox.getChildren().addAll(phoneNumber);
                break;
            default:
                break;
        }
        return vbox;
    }

    private Node createFooter() {
        return new BottomButton("Save", () -> {
            switch (user.getRole()) {
                case CUSTOMER:
                    customerModel.setName(
                            nameField.getInputValue() != "" ? nameField.getInputValue() : customerModel.getName());
                    customerModel.setPassword(passwordField.getInputValue() != "" ? passwordField.getInputValue()
                            : customerModel.getPassword());
                    customerModel.setPhoneNumber(phoneNumber.getInputValue() != "" ? phoneNumber.getInputValue()
                            : customerModel.getPhoneNumber());
                    customerModel.setAddress(
                            address.getInputValue() != "" ? address.getInputValue() : customerModel.getAddress());

                    if (UserServices.saveUser(customerModel) != null) {
                        PopupMessage.showMessage("Customer Profile Updated Successfully!", "success",
                                () -> {
                                    AdminViewModel.initDatabaseViewModel();
                                    AdminViewModel.getDatabaseViewModel().navigate(
                                            AdminViewModel.getDatabaseViewModel().getNode());
                                });
                    } else {
                        PopupMessage.showMessage("Failed to update customer profile!", "error", () -> {
                        });
                    }
                    break;

                case VENDOR:
                    vendorModel.setName(
                            nameField.getInputValue() != "" ? nameField.getInputValue() : vendorModel.getName());
                    vendorModel.setPassword(passwordField.getInputValue() != "" ? passwordField.getInputValue()
                            : vendorModel.getPassword());
                    vendorModel.setShopName(
                            shopName.getInputValue() != "" ? shopName.getInputValue() : vendorModel.getShopName());
                    vendorModel.setShopDescription(shopDescription.getInputValue() != "" ? shopDescription.getInputValue()
                            : vendorModel.getShopDescription());
                    vendorModel.setShopPhoneNumber(shopPhoneNumber.getInputValue() != "" ? shopPhoneNumber.getInputValue()
                            : vendorModel.getShopPhoneNumber());
                    vendorModel.setAddress(
                            shopAddress.getInputValue() != "" ? shopAddress.getInputValue() : vendorModel.getAddress());
                    
                    if (UserServices.saveUser(vendorModel) != null) {
                        PopupMessage.showMessage("Vendor Profile Updated Successfully!", "success",
                                () -> {
                                    AdminViewModel.initDatabaseViewModel();
                                    AdminViewModel.getDatabaseViewModel().navigate(
                                            AdminViewModel.getDatabaseViewModel().getNode());
                                });
                    } else {
                        PopupMessage.showMessage("Failed to update vendor profile!", "error", () -> {
                        });
                    }
                    break; 
                case RUNNER:
                    runnerModel.setName(
                            nameField.getInputValue() != "" ? nameField.getInputValue() : runnerModel.getName());
                    runnerModel.setPassword(passwordField.getInputValue() != "" ? passwordField.getInputValue()
                            : runnerModel.getPassword());
                    runnerModel.setPhoneNumber(phoneNumber.getInputValue() != "" ? phoneNumber.getInputValue()
                            : runnerModel.getPhoneNumber());

                    if (UserServices.saveUser(runnerModel) != null) {
                        PopupMessage.showMessage("Runner Profile Updated Successfully!", "success",
                                () -> {
                                    AdminViewModel.initDatabaseViewModel();
                                    AdminViewModel.getDatabaseViewModel().navigate(
                                            AdminViewModel.getDatabaseViewModel().getNode());
                                });
                    } else {
                        PopupMessage.showMessage("Failed to update runner profile!", "error", () -> {
                        });
                    }
                default:
                    break;
            }
        });
    }
}
