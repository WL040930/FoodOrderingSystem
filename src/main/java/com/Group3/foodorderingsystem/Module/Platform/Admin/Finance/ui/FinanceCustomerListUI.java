package com.Group3.foodorderingsystem.Module.Platform.Admin.Finance.ui;

import java.util.Arrays;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.Card;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.DynamicSearchBarUI;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Vouchers.widgets.KButton;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FinanceCustomerListUI extends BaseContentPanel {

    public FinanceCustomerListUI() {
        super();

        setHeader(header());
        setContent(content());

        setFooterHeight(0);
        setContentHeight(570);
    }

    private Node header() {
        return new TitleBackButton("Finance");
    }

    private Node content() {
        DynamicSearchBarUI<CustomerModel> searchBarUI = new DynamicSearchBarUI<>(UserServices.getCustomers(), "email",
                null, this::renderCustomerCard);
        return searchBarUI;
    }

    private Node renderCustomerCard(CustomerModel customer) {
        VBox contentBox = new VBox();

        Label emailLabel = new Label(customer.getEmail());
        emailLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label nameLabel = new Label(customer.getName());
        nameLabel.setStyle("-fx-font-size: 14px;");

        Label balanceLabel = new Label("Balance: RM " + customer.getBalance());
        balanceLabel.setStyle("-fx-font-size: 14px;");

        contentBox.getChildren().addAll(emailLabel, nameLabel, balanceLabel);

        HBox actionBox = new HBox(5);
        actionBox.setAlignment(Pos.CENTER_RIGHT);

        KButton viewButton = new KButton("Top Up", () -> {
            AdminViewModel.getFinanceViewModel().setAdminTopUpUI(new AdminTopUpUI(customer));
            AdminViewModel.getFinanceViewModel().navigate(AdminViewModel.getFinanceViewModel().getAdminTopUpUI());
        });

        KButton orderButton = new KButton("Transactions", () -> {
            AdminViewModel.getFinanceViewModel().setAdminTransactionHistoryUI(new AdminTransactionHistoryUI(customer));
            AdminViewModel.getFinanceViewModel().navigate(AdminViewModel.getFinanceViewModel().getAdminTransactionHistoryUI());
        });

        actionBox.getChildren().addAll(viewButton, orderButton);

        contentBox.getChildren().add(actionBox);

        HBox hbox = new HBox(new Card(contentBox, 470, 100, null));
        hbox.setPadding(new Insets(3));
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

}
