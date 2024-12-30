package com.Group3.foodorderingsystem.Module.Platform.Admin.Register.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.TitleTextField;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.TitleTextFieldEnum;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class BasicInfoForm extends VBox {

    private TitleTextField fullNameField;
    private TitleTextField emailField;
    private TitleTextField passwordField;
    private TitleTextField confirmPasswordField;

    public BasicInfoForm() {
        super();
        init();
    }

    private void init() {
        this.getChildren().addAll(buildHeader(), buildContent(), buildSubmitButton());
    }

    private Node buildHeader() {
        return new TitleBackButton(
                "Register as " + AdminViewModel.getRegisterViewModel().getSelectedRole().getRole(),
                () -> AdminViewModel.getRegisterViewModel()
                        .navigate(AdminViewModel.getRegisterViewModel().getRegisterRoleSelection()));
    }

    private Node buildContent() {
        VBox content = new VBox(10);
        content.setStyle("-fx-padding: 10px;");

        // Initialize form fields
        fullNameField = new TitleTextField("Full Name", "Enter your full name", TitleTextFieldEnum.TextField);
        emailField = new TitleTextField("Email Address", "Enter your email", TitleTextFieldEnum.EmailField);
        passwordField = new TitleTextField("Password", "Enter your password", TitleTextFieldEnum.PasswordField);
        confirmPasswordField = new TitleTextField("Confirm Password", "Re-enter your password",
                TitleTextFieldEnum.PasswordField);

        // Add fields to the content
        content.getChildren().addAll(
                new Label("Basic Information"),
                fullNameField,
                emailField,
                passwordField,
                confirmPasswordField);

        return content;
    }

    private Node buildSubmitButton() {
        Label errorMessageLabel = new Label();
        errorMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        errorMessageLabel.setVisible(false);

        Button submitButton = new Button("Submit");

        // Style the button
        submitButton.setStyle(
                "-fx-padding: 10px; " +
                        "-fx-font-size: 14px; " +
                        "-fx-background-color: #0078D7; " +
                        "-fx-text-fill: white; ");

        submitButton.setMaxWidth(Double.MAX_VALUE);

        VBox container = new VBox(5);
        container.setStyle("-fx-padding: 10px;");
        container.getChildren().addAll(errorMessageLabel, submitButton);

        submitButton.setOnAction(event -> handleSubmit(errorMessageLabel));
        return container;
    }

    private void handleSubmit(Label errorMessageLabel) {
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

        switch (AdminViewModel.getRegisterViewModel().getSelectedRole()) {
            case Customer:
                AdminViewModel.getRegisterViewModel().setCustomerRegistration(new CustomerRegistration(user));
                AdminViewModel.getRegisterViewModel().navigate(AdminViewModel.getRegisterViewModel().getCustomerRegistration());
                break;
            case Vendor:
                
                break;
            case Runner:
                
                break;
            default:
                break;
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

}
