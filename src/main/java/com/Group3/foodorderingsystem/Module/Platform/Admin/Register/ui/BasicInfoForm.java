package com.Group3.foodorderingsystem.Module.Platform.Admin.Register.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Core.Storage.Storage;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.assets.RegisterAssets;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.BottomButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.FileUploadType;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.FileUploadWidget;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.TitleTextField;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.TitleTextFieldEnum;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class BasicInfoForm extends BaseContentPanel {

    private TitleTextField fullNameField;
    private TitleTextField emailField;
    private TitleTextField passwordField;
    private TitleTextField confirmPasswordField;
    private FileUploadWidget profileImageField;

    Boolean isImageUploaded = false;

    private Label errorMessageLabel = new Label();

    public BasicInfoForm() {
        super();
        setHeader(header());
        setContent(content());
        setFooter(footer());
    }

    private Node header() {
        return new TitleBackButton(
                "Register as " + AdminViewModel.getRegisterViewModel().getSelectedRole().getRole(),
                () -> AdminViewModel.getRegisterViewModel()
                        .navigate(AdminViewModel.getRegisterViewModel().getRegisterRoleSelection()));
    }

    private Node content() {
        VBox content = new VBox(15);
        content.setStyle("-fx-padding: 10px;");

        // Initialize form fields
        profileImageField = new FileUploadWidget(FileUploadType.CIRCLE_AVATAR, User.getDefaultProfilePicture(
                getRoleEnum(AdminViewModel.getRegisterViewModel().getSelectedRole())));
        profileImageField.setOnFileSelectedCallback(selectedFile -> {
            isImageUploaded = false;
        });
        fullNameField = new TitleTextField("Full Name", "Enter your full name", null, TitleTextFieldEnum.TextField);
        emailField = new TitleTextField("Email Address", "Enter your email", null, TitleTextFieldEnum.EmailField);
        passwordField = new TitleTextField("Password", "Enter your password", null, TitleTextFieldEnum.PasswordField);
        confirmPasswordField = new TitleTextField("Confirm Password", "Re-enter your password", null,
                TitleTextFieldEnum.PasswordField);

        errorMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

        // Add fields to the content
        content.getChildren().addAll(
                profileImageField,
                fullNameField,
                emailField,
                passwordField,
                confirmPasswordField,
                errorMessageLabel);

        return content;
    }

    private Node footer() {
        return new BottomButton("Next", () -> handleSubmit());
    }

    private void handleSubmit() {
        String fullName = ((TextField) fullNameField.getChildren().get(1)).getText();
        String email = ((TextField) emailField.getChildren().get(1)).getText();
        String password = ((TextField) passwordField.getChildren().get(1)).getText();
        String confirmPassword = ((TextField) confirmPasswordField.getChildren().get(1)).getText();

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            errorMessageLabel.setText("All fields must be filled!");
            errorMessageLabel.setVisible(true);
            return;
        }

        if (!isValidEmail(email)) {
            errorMessageLabel.setText("Invalid email format!");
            errorMessageLabel.setVisible(true);
            return;
        }

        if (UserServices.isEmailExist(email) != null) {
            errorMessageLabel.setText("Email already exists!");
            errorMessageLabel.setVisible(true);
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorMessageLabel.setText("Passwords do not match!");
            errorMessageLabel.setVisible(true);
            return;
        }

        errorMessageLabel.setText("");
        errorMessageLabel.setVisible(false);

        User user = new User();
        user.setName(fullName);
        user.setEmail(email);
        user.setPassword(password);

        if (profileImageField.getSelectedFile() != null && !isImageUploaded) {
            user.setProfilePicture(Storage.saveFile(profileImageField.getSelectedFile()));
            isImageUploaded = true;
        }

        switch (AdminViewModel.getRegisterViewModel().getSelectedRole()) {
            case Customer:
                AdminViewModel.getRegisterViewModel().setCustomerRegistration(new CustomerRegistration(user));
                AdminViewModel.getRegisterViewModel()
                        .navigate(AdminViewModel.getRegisterViewModel().getCustomerRegistration());
                break;
            case Vendor:
                AdminViewModel.getRegisterViewModel().setVendorRegistration(new VendorRegistration(user));
                AdminViewModel.getRegisterViewModel()
                        .navigate(AdminViewModel.getRegisterViewModel().getVendorRegistration());
                break;
            case Runner:
                AdminViewModel.getRegisterViewModel().setRunnerRegistration(new RunnerRegistration(user));
                AdminViewModel.getRegisterViewModel()
                        .navigate(AdminViewModel.getRegisterViewModel().getRunnerRegistration());
                break;
            default:
                break;
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    private RoleEnum getRoleEnum(RegisterAssets registerAssets) {
        switch (registerAssets) {
            case Customer:
                return RoleEnum.CUSTOMER;
            case Vendor:
                return RoleEnum.VENDOR;
            case Runner:
                return RoleEnum.RUNNER;
            default:
                return RoleEnum.CUSTOMER;
        }
    }

}
