package com.Group3.foodorderingsystem.Module.Platform.Vendor.Vouchers.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.VoucherModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Services.VoucherServices;
import com.Group3.foodorderingsystem.Core.Storage.Storage;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.BottomButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.PopupMessage;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Vouchers.widgets.KButton;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VoucherAddUI extends BaseContentPanel {

    private TextField codeTextField; // For the 8-character string
    private TextField discountTextField; // For the double between 0 and 100
    private Label errorLabel;

    public VoucherAddUI() {
        super();

        setHeader(header());
        setContent(content());
        setFooter(footer());
    }

    private Node header() {
        return new TitleBackButton("Add Voucher", () -> {
            VendorViewModel.getVoucherViewModel().navigate(VendorViewModel.getVoucherViewModel().getVoucherListUI());
        });
    }

    private Node content() {
        VBox contentBox = new VBox(15); // Create VBox with spacing
        contentBox.setPadding(new Insets(20));

        // Code field
        Label codeLabel = new Label("Voucher Code (8 chars):");
        codeTextField = new TextField();
        codeTextField.setPromptText("Enter exactly 8 characters");
        codeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 8) {
                codeTextField.setText(newValue.substring(0, 8)); // Restrict to 8 characters
            }
        });

        Button autoGenerateButton = new KButton("Auto Generate", () -> {
            String code = Storage.generateNewId().substring(0, 8);
            codeTextField.setText(code);
        });

        HBox codeBox = new HBox(10);
        codeBox.getChildren().addAll(codeTextField, autoGenerateButton);

        // Discount field
        Label discountLabel = new Label("Discount Percentage (0-100):");
        discountTextField = new TextField();
        discountTextField.setPromptText("Enter a value between 0 and 100");
        discountTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d?)?")) {
                discountTextField.setText(oldValue); // Revert to the previous valid value
            } else if (!newValue.isEmpty()) {
                try {
                    double value = Double.parseDouble(newValue);
                    if (value < 0 || value > 100) {
                        discountTextField.setText(oldValue); // Revert if out of range
                    }
                } catch (NumberFormatException e) {
                    discountTextField.setText(oldValue); // Revert on parse error
                }
            }
        });

        errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        // Add labels and fields to the VBox
        contentBox.getChildren().addAll(
                codeLabel,
                codeBox,
                discountLabel,
                discountTextField,
                errorLabel);

        return contentBox;
    }

    private Node footer() {
        return new BottomButton("Create", () -> {
            String code = codeTextField.getText();
            String discountText = discountTextField.getText();

            if (code.length() != 8) {
                errorLabel.setText("Error: Voucher code must be exactly 8 characters.");
                return;
            }

            try {
                double discount = Double.parseDouble(discountText);
                if (discount < 0 || discount > 100) {
                    errorLabel.setText("Error: Discount percentage must be between 0 and 100.");
                    return;
                }

                errorLabel.setText("");

                VoucherModel voucher = new VoucherModel();
                voucher.setVoucherCode(code);
                voucher.setDiscountRate(discount);

                Object vendor = SessionUtil.getVendorFromSession();
                if (vendor instanceof VendorModel) {
                    voucher.setVendor((VendorModel) vendor);
                } else {
                    errorLabel.setText("Please login as a vendor to create a voucher.");
                    return;
                }

                if (VoucherServices.saveVouchers(voucher) == null) {
                    errorLabel.setText("Error: Voucher code already exists.");
                } else {
                    PopupMessage.showMessage("Voucher has been created.", "success", () -> {
                        VendorViewModel.initVoucherViewModel();
                        VendorViewModel.getVoucherViewModel()
                                .navigate(VendorViewModel.getVoucherViewModel().getVoucherListUI());
                    });
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid discount percentage.");
            }
        });
    }
}
