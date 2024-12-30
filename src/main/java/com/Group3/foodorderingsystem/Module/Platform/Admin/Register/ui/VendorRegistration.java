package com.Group3.foodorderingsystem.Module.Platform.Admin.Register.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.BottomButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.FileUploadType;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.FileUploadWidget;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.TitleTextField;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.TitleTextFieldEnum;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class VendorRegistration extends BaseContentPanel {

    private User user; 

    private TitleTextField shopName; 
    private TitleTextField shopDescription; 
    private TitleTextField shopPhoneNumber;
    private TitleTextField shopAddress;
    private FileUploadWidget shopImage; 

    public VendorRegistration(User user) {
        super();

        this.user = user;
        setHeader(header());
        setContent(content());
        setFooter(footer());
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
        VBox content = new VBox(10);

        VBox container = new VBox(); 

        shopName = new TitleTextField("Shop Name", "Enter your shop name", TitleTextFieldEnum.TextField);
        shopDescription = new TitleTextField("Shop Description", "Enter your shop description", TitleTextFieldEnum.TextArea);
        shopPhoneNumber = new TitleTextField("Shop Phone Number", "Enter your shop phone number", TitleTextFieldEnum.TextField);
        shopAddress = new TitleTextField("Shop Address", "Enter your shop address", TitleTextFieldEnum.TextArea);
        // shopImage = new FileUploadWidget(FileUploadType.BUTTON, ); 

        content.getChildren().addAll(
                shopName,
                shopDescription,
                shopPhoneNumber,
                shopAddress
                // shopImage
        );

        return content;
    }

    private Node footer() {
        return new BottomButton("Register", () -> {
            System.out.println("Registering Vendor");
        });
    }

}
