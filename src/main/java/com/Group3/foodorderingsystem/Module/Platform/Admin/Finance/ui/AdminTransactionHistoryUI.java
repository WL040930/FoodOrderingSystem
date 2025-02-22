package com.Group3.foodorderingsystem.Module.Platform.Admin.Finance.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel.TransactionType;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Services.NotificationServices;
import com.Group3.foodorderingsystem.Core.Services.TransactionServices;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.Card;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.PopupMessage;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.DynamicSearchBarUI;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Vouchers.widgets.KButton;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class AdminTransactionHistoryUI extends BaseContentPanel {

    private CustomerModel customerModel;

    public AdminTransactionHistoryUI(CustomerModel customerModel) {
        super();
        this.customerModel = customerModel;

        setHeader(header());
        setContent(content());

        setFooterHeight(0);
        setContentHeight(580);
    }

    private Node header() {
        return new TitleBackButton("Transaction History", () -> {
            AdminViewModel.getFinanceViewModel()
                    .navigate(AdminViewModel.getFinanceViewModel().getFinanceCustomerListUI());
        });
    }

    private Node content() {
        // Add DynamicSearchBarUI
        DynamicSearchBarUI<TransactionModel> searchBar = new DynamicSearchBarUI<>(
                TransactionServices.getTransactionByUserId(customerModel.getId()), "transactionId", null,
                this::transactionBox);
        searchBar.setSearchBarVisible(false);

        return searchBar;
    }

    private Node transactionBox(TransactionModel transaction) {
        HBox box = new HBox();
        box.setPadding(new Insets(5, 10, 5, 10));
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);

        VBox left = new VBox();
        Label transactionType = new Label();
        if (transaction.getTransactionType() == TransactionType.PAYMENT
                || transaction.getTransactionType() == TransactionType.REFUND) {
            transactionType
                    .setText(transaction.getTransactionType() + " - Order ID: "
                            + transaction.getOrderModel().getOrderId());
        } else {
            transactionType.setText(transaction.getTransactionType().toString());
        }

        transactionType.setStyle("-fx-font-weight: bold;");
        Label date = new Label(transaction.getTransactionDate().toString());
        left.getChildren().addAll(transactionType, date);

        VBox right = new VBox();
        Label amount = new Label(
                transaction.getAmount() > 0 ? "+ RM" + String.format("%.2f", transaction.getAmount())
                        : "- RM" + String.format("%.2f", Math.abs(transaction.getAmount())));
        amount.setStyle(transaction.getAmount() > 0 ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
        right.setAlignment(Pos.CENTER_RIGHT);

        right.getChildren().add(amount);

        // Add a spacer to push the "right" VBox to the far right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        box.getChildren().addAll(left, spacer, right);

        VBox contentVBox = new VBox(box);
        contentVBox.setSpacing(10);
        KButton sendReceipt = new KButton("Send Receipt", () -> {
            NotificationServices.sendReceipt(transaction, customerModel);
            PopupMessage.showMessage("Receipt Sent.", "success", () -> {
            });
        });
        contentVBox.getChildren().add(sendReceipt);

        HBox contentBox = new HBox(new Card(contentVBox, 450, 100, null));
        contentBox.setPadding(new Insets(5, 0, 5, 0));
        contentBox.setAlignment(Pos.CENTER);
        return contentBox;
    }

}
