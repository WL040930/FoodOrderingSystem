package com.Group3.foodorderingsystem.Module.Platform.Admin.Register.ui;

import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.PopupMessage;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.TitleTextField;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.TitleTextFieldEnum;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class CustomerRegistration extends VBox {

    private TitleTextField phoneNumberField;
    private TitleTextField addressField;
    private User user;
    private Label errorMessageLabel; // Label for error messages

    public CustomerRegistration(User user) {
        super();
        this.user = user;
        init();
    }

    private void init() {
        this.setSpacing(10); // Space between elements
        this.setStyle("-fx-padding: 10px;");

        this.getChildren().addAll(buildHeader(), buildContent(), buildSubmitButton());
    }

    private Node buildHeader() {
        return new TitleBackButton(
                "Register As Customer",
                () -> AdminViewModel.getRegisterViewModel()
                        .navigate(AdminViewModel.getRegisterViewModel().getBasicInfoForm()));
    }

    private Node buildContent() {
        VBox content = new VBox(10);
        content.setStyle("-fx-padding: 10px;");

        // Create the input fields
        phoneNumberField = new TitleTextField("Phone Number", "Enter your phone number", TitleTextFieldEnum.TextField);
        addressField = new TitleTextField("Address", "Enter your address", TitleTextFieldEnum.TextArea);

        errorMessageLabel = new Label();
        errorMessageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        errorMessageLabel.setVisible(false); 

        content.getChildren().addAll(
                new Label("Customer Information"),
                phoneNumberField,
                addressField,
                errorMessageLabel);

        // Allow content to expand and push the submit button to the bottom
        VBox.setVgrow(content, javafx.scene.layout.Priority.ALWAYS);

        return content;
    }

    private Node buildSubmitButton() {
        Button submitButton = new Button("Submit");

        submitButton.setStyle(
                "-fx-padding: 10px; " +
                        "-fx-font-size: 14px; " +
                        "-fx-background-color: #0078D7; " +
                        "-fx-text-fill: white;");

        submitButton.setMaxWidth(Double.MAX_VALUE);

        submitButton.setOnAction(e -> {
            submitForm();
        });

        VBox container = new VBox(10);
        container.setStyle("-fx-padding: 10px;");
        container.getChildren().add(submitButton);

        return container;
    }

    private void submitForm() {
        // Retrieve input values
        String phoneNumber = phoneNumberField.getInputValue();
        String address = addressField.getInputValue();

        // Validation checks
        if (phoneNumber.isEmpty() || address.isEmpty()) {
            errorMessageLabel.setText("Please fill in all fields.");
            errorMessageLabel.setVisible(true);
            return;
        }

        errorMessageLabel.setVisible(false);

        CustomerModel customer = new CustomerModel();
        customer.setName(user.getName());
        customer.setEmail(user.getEmail());
        customer.setPassword(user.getPassword());
        customer.setPhoneNumber(phoneNumber);
        customer.setAddress(address);

        CustomerModel registerCustomer = UserServices.saveUser(customer); 

        if (registerCustomer != null) {
            PopupMessage.showMessage("Customer registered successfully!", "success", () -> {
                AdminViewModel.initRegisterViewModel();
                AdminViewModel.getRegisterViewModel().navigate(AdminViewModel.getRegisterViewModel().getNode());
            });
        } else {
            PopupMessage.showMessage("An error occured, please try again", "error", ()->{});
        }
    }
}
