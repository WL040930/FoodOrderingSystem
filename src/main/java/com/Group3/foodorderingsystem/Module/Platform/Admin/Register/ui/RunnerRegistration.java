package com.Group3.foodorderingsystem.Module.Platform.Admin.Register.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.RunnerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.BottomButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.PopupMessage;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.TitleTextField;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.TitleTextFieldEnum;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class RunnerRegistration extends BaseContentPanel {

    private User user;
    private TitleTextField phoneNumberField;

    public RunnerRegistration(User user) {
        super();
        this.user = user;

        setHeader(header());
        setContent(content());
        setFooter(footer());
    }

    private Node header() {
        return new TitleBackButton(
                "Register as " + AdminViewModel.getRegisterViewModel().getSelectedRole().getRole(),
                () -> AdminViewModel.getRegisterViewModel()
                        .navigate(AdminViewModel.getRegisterViewModel().getBasicInfoForm()));
    }

    private Node content() {
        VBox content = new VBox(10);

        phoneNumberField = new TitleTextField("Phone Number", "Enter your phone number", "",
                TitleTextFieldEnum.TextField);

        content.getChildren().addAll(
                phoneNumberField);
        return content;
    }

    private Node footer() {
        return new BottomButton("Register", () -> {
            if (phoneNumberField.getInputValue() == null || phoneNumberField.getInputValue().isEmpty()) {
                PopupMessage.showMessage("Phone number is required", "error", () -> {
                });
                return;
            }

            RunnerModel runner = new RunnerModel();

            runner.setName(user.getName());
            runner.setEmail(user.getEmail());
            runner.setPassword(user.getPassword());
            runner.setProfilePicture(user.getProfilePicture());
            runner.setPhoneNumber(phoneNumberField.getInputValue());
            runner.setRevenue(0.0);

            RunnerModel registerRunner = UserServices.saveUser(runner);
            if (registerRunner != null) {
                PopupMessage.showMessage("Runner Created Successful!", "success", () -> {
                    AdminViewModel.initRegisterViewModel();
                    AdminViewModel.initDatabaseViewModel();
                    AdminViewModel.initFinanceViewModel();
                    AdminViewModel.getRegisterViewModel().navigate(AdminViewModel.getRegisterViewModel().getNode());
                });
            } else {
                PopupMessage.showMessage("An error occured, please try again", "error", () -> {
                });
            }
        });
    }
}
