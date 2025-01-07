package com.Group3.foodorderingsystem.Module.Platform.Vendor.Vouchers.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.VoucherModel;
import com.Group3.foodorderingsystem.Core.Services.VoucherServices;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.BottomButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.PopupMessage;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class VoucherEditUI extends BaseContentPanel {

    private VoucherModel voucher;

    private TextField discountTextField;

    public VoucherEditUI(VoucherModel voucher) {
        super();
        this.voucher = voucher;

        setHeader(header());
        setContent(content());
        setFooter(footer());
    }

    private Node header() {
        return new TitleBackButton("Voucher Code: " + voucher.getVoucherCode(), () -> {
            VendorViewModel.initVoucherViewModel();
            VendorViewModel.getVoucherViewModel().navigate(VendorViewModel.getVoucherViewModel().getVoucherListUI());
        });
    }

    private Node content() {
        VBox contentBox = new VBox(15);

        Label discountLabel = new Label("Discount Percentage (0-100):");
        discountTextField = new TextField();
        discountTextField.setPromptText(voucher.getDiscountRate() + "%");
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
                    discountTextField.setText(oldValue); 
                }
            }
        });

        contentBox.getChildren().addAll(discountLabel, discountTextField);
        return contentBox;
    }

    private Node footer() {
        return new BottomButton("Save", () -> {
            if (!discountTextField.getText().isEmpty()) {
                voucher.setDiscountRate(Double.parseDouble(discountTextField.getText()));

                if (VoucherServices.updateVoucher(voucher) != null) {
                    PopupMessage.showMessage("Voucher Updated.", "success", () -> {
                        VendorViewModel.initVoucherViewModel();
                        VendorViewModel.getVoucherViewModel().navigate(VendorViewModel.getVoucherViewModel().getVoucherListUI());
                    });
                } else {
                    System.err.println("Error updating voucher");
                }
            } 
        });
    }
}
