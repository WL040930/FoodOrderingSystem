package com.Group3.foodorderingsystem.Module.Platform.Admin.Register.ui;

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
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.VendorCard;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class VendorRegistration extends BaseContentPanel {

    private User user;

    private TitleTextField shopName;
    private TitleTextField shopDescription;
    private TitleTextField shopPhoneNumber;
    private TitleTextField shopAddress;
    private FileUploadWidget shopImage;

    private StringProperty cardShopName = new SimpleStringProperty("");
    private StringProperty cardShopDescription = new SimpleStringProperty("");
    private StringProperty cardShopPhoneNumber = new SimpleStringProperty("");
    private StringProperty cardShopAddress = new SimpleStringProperty("");

    private Label errorText;

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

        VBox formContainer = new VBox();

        shopName = new TitleTextField("Shop Name", "Enter your shop name", null, TitleTextFieldEnum.TextField);
        shopDescription = new TitleTextField("Shop Description", "Enter your shop description", null,
                TitleTextFieldEnum.TextArea);
        shopPhoneNumber = new TitleTextField("Shop Phone Number", "Enter your shop phone number", null,
                TitleTextFieldEnum.TextField);
        shopAddress = new TitleTextField("Shop Address", "Enter your shop address", null, TitleTextFieldEnum.TextArea);
        shopImage = new FileUploadWidget(FileUploadType.BUTTON, "admin.png");

        errorText = new Label();
        errorText.setStyle("-fx-text-fill: red; -fx-font-size: 12px; -fx-font-weight: bold;");

        cardShopName.bind(shopName.textProperty());
        cardShopDescription.bind(shopDescription.textProperty());
        cardShopPhoneNumber.bind(shopPhoneNumber.textProperty());
        cardShopAddress.bind(shopAddress.textProperty());

        formContainer.getChildren().addAll(shopName, shopDescription, shopPhoneNumber, shopAddress, shopImage,
                errorText);

        // Adding VendorCard to the content (uncommented the previous VendorCard line)
        VendorCard previewCard = new VendorCard("Sample Shop", 4, "admin.png");
        content.getChildren().addAll(previewCard, formContainer);

        return content;
    }

    private Node footer() {
        return new BottomButton("Register", () -> {
            if (shopName.getInputValue().isEmpty() || shopDescription.getInputValue().isEmpty()
                    || shopPhoneNumber.getInputValue().isEmpty()
                    || shopAddress.getInputValue().isEmpty()) {
                errorText.setText("Please fill all the fields");
                return;
            }

            VendorModel vendor = new VendorModel();
            vendor.setName(user.getName());
            vendor.setEmail(user.getEmail());
            vendor.setPassword(user.getPassword());
            vendor.setShopName(shopName.getInputValue());
            vendor.setShopDescription(shopDescription.getInputValue());
            vendor.setShopPhoneNumber(shopPhoneNumber.getInputValue());
            vendor.setAddress(shopAddress.getInputValue());
            if (shopImage.getSelectedFile() != null) {
                vendor.setShopImage(Storage.saveFile(shopImage.getSelectedFile()));
            } else {
                vendor.setShopImage("admin.png"); // TODO: Change this to a default image
            }

            VendorModel vendorModel = UserServices.saveUser(vendor);

            if (vendorModel != null) {
                PopupMessage.showMessage("Vendor registered successfully!", "success", () -> {
                    AdminViewModel.initRegisterViewModel();
                    AdminViewModel.getRegisterViewModel().navigate(AdminViewModel.getRegisterViewModel().getNode());
                });
            } else {
                PopupMessage.showMessage("An error occured, please try again", "error", () -> {
                });
            }
        });
    }
}
