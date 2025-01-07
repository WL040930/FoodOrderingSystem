package com.Group3.foodorderingsystem.Module.Platform.Vendor.Vouchers.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.VoucherModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Services.VoucherServices;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.Card;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.DynamicSearchBarUI;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Menu.ui.AddNewItem;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Vouchers.model.VoucherViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Vouchers.widgets.KButton;
import com.itextpdf.kernel.colors.Lab;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class VoucherListUI extends BaseContentPanel {

    public VoucherListUI() {
        super();

        setHeader(header());
        setContent(content());

        setFooterHeight(0);
        setContentHeight(570);
    }

    private Node header() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);

        TitleBackButton backButton = new TitleBackButton("Vouchers");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addNewItemButton = new Button("Add New Vouchers");
        addNewItemButton.setOnAction(event -> {
            VendorViewModel.getVoucherViewModel().setVoucherAddUI(new VoucherAddUI());
            VendorViewModel.getVoucherViewModel().navigate(VendorViewModel.getVoucherViewModel().getVoucherAddUI());
        });

        HBox.setMargin(addNewItemButton, new Insets(0, 10, 0, 0));

        header.getChildren().addAll(backButton, spacer, addNewItemButton);

        return header;
    }

    private Node content() {
        Object vendor = SessionUtil.getVendorFromSession();
        if (vendor instanceof VendorModel) {
            VendorModel vendorModel = (VendorModel) vendor;

            DynamicSearchBarUI<VoucherModel> searchBar = new DynamicSearchBarUI<>(
                    VoucherServices.getVouchersByVendorId(vendorModel.getId()),
                    "voucherCode",
                    null,
                    this::createVoucherCard);
            return searchBar;
        }

        return new VBox();
    }

    private Node createVoucherCard(VoucherModel voucher) {
        // Main container
        HBox contentBox = new HBox();
        contentBox.setSpacing(10);
        contentBox.setPadding(new Insets(10));
        contentBox.setAlignment(Pos.CENTER_LEFT);

        // Info box (70% width)
        VBox infoBox = new VBox();
        infoBox.setSpacing(5);
        Label voucherCode = new Label("Voucher Code: " + voucher.getVoucherCode());
        voucherCode.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label discountRate = new Label("Discount Rate: " + voucher.getDiscountRate() + "%");
        discountRate.setStyle("-fx-font-size: 12px;"); // Smaller font for secondary information

        infoBox.getChildren().addAll(voucherCode, discountRate);
        infoBox.setPrefWidth(300); // Fixed width for the info box

        // Button box (30% width)
        VBox buttonBox = new VBox();
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER);

        KButton editButton = new KButton("Edit", () -> {
            VendorViewModel.getVoucherViewModel().setVoucherEditUI(new VoucherEditUI(voucher));
            VendorViewModel.getVoucherViewModel().navigate(VendorViewModel.getVoucherViewModel().getVoucherEditUI());
        });

        KButton deleteButton = new KButton("Delete", () -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Voucher");
            alert.setHeaderText("Are you sure you want to delete this voucher? " + voucher.getVoucherCode());

            alert.showAndWait().ifPresent(response -> {
                // Checking if the user clicked the "OK" or "Yes" button
                if (response == ButtonType.OK || response == ButtonType.YES) {
                    VoucherServices.deleteVoucher(voucher.getVoucherCode());
                    VendorViewModel.getVoucherViewModel().setVoucherListUI(new VoucherListUI());
                    VendorViewModel.getVoucherViewModel()
                            .navigate(VendorViewModel.getVoucherViewModel().getVoucherListUI());
                }
            });
        });

        buttonBox.getChildren().addAll(editButton, deleteButton);
        buttonBox.setPrefWidth(120);

        contentBox.getChildren().addAll(infoBox, buttonBox);

        Card card = new Card(contentBox, 480, 80, null);
        HBox wrapper = new HBox(card);
        wrapper.setPadding(new Insets(5));
        wrapper.setAlignment(Pos.CENTER);
        return wrapper;
    }

}
