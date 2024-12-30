package com.Group3.foodorderingsystem.Module.Platform.Admin.Register.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class VendorRegistration extends BaseContentPanel {

    private User user; 
    public VendorRegistration(User user) {
        super();

        this.user = user;
        setHeader(header());
        setContent(content());
        setFooter(footer());

        setContentHeight(480);
    }

    private Node header() {
        return new TitleBackButton(
                "Register as Vendor",
                () -> {
                    AdminViewModel.getRegisterViewModel()
                            .navigate(AdminViewModel.getRegisterViewModel().getBasicInfoForm());
                });
    }

    private Node content() {
        return new Label(
                "Vendor Registration Form");
    }

    private Node footer() {
        Button actionButton = new Button("Register");
        actionButton.setMaxWidth(Double.MAX_VALUE);

        actionButton.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; "
                        + "-fx-padding: 10px 30px 10px 30px; -fx-border-radius: 5px;");

        actionButton.setOnMouseClicked(event -> {
            System.out.println("Register as Vendor");
        });

        actionButton.setOnMouseEntered((MouseEvent e) -> actionButton.setStyle(
                "-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 16px; "
                        + "-fx-padding: 10px 30px 10px 30px; -fx-border-radius: 5px;"));

        actionButton.setOnMouseExited((MouseEvent e) -> actionButton.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; "
                        + "-fx-padding: 10px 30px 10px 30px; -fx-border-radius: 5px;"));

        return actionButton;
    }

}
