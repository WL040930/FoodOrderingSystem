package com.Group3.foodorderingsystem.Module.Common.Transaction.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel.TransactionType;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.RunnerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;
import com.Group3.foodorderingsystem.Core.Services.TransactionServices;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.Card;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Common.Transaction.Widgets.KSeparator;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.DynamicSearchBarUI;
import com.Group3.foodorderingsystem.Module.Platform.Runner.RunnerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Vouchers.widgets.KButton;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class UserFinance extends BaseContentPanel {

    private User user = new User();
    private Double balance;

    public UserFinance() {
        super();
        initUser();

        setHeader(header());
        setContent(content());

        setContentHeight(570);
        setFooterHeight(0);
    }

    private void initUser() {
        CustomerModel customer = SessionUtil.getCustomerFromSession();
        if (customer != null) {
            this.user.setId(customer.getId());
            this.user.setRole(customer.getRole());
            customer = UserServices.findCustomerById(customer.getId());
            this.balance = customer.getBalance();
        }

        Object vendor = SessionUtil.getVendorFromSession();
        if (vendor != null && vendor instanceof VendorModel) {
            this.user.setId(((VendorModel) vendor).getId());
            this.user.setRole(((VendorModel) vendor).getRole());
            VendorModel vendorModel = UserServices.findVendorById(((VendorModel) vendor).getId());
            this.balance = vendorModel.getRevenue();
        }
        Object runner = SessionUtil.getRiderFromSession();
        if (runner != null && runner instanceof RunnerModel) {
            this.user.setId(((RunnerModel) runner).getId());
            this.user.setRole(((RunnerModel) runner).getRole());
            RunnerModel runnerModel = UserServices.findRunnerById(((RunnerModel) runner).getId());
            this.balance = runnerModel.getRevenue();
        }
    }

    private Node header() {
        return new TitleBackButton("Finance");
    }

    private Node content() {
        VBox content = new VBox(10);
        content.setPadding(new Insets(10, 10, 10, 10));
        content.setAlignment(Pos.TOP_CENTER);

        // Decorated Balance Label
        Label balanceLabel = new Label("Your" + (user.getRole() == RoleEnum.CUSTOMER ? " Balance" : " Revenue"));
        balanceLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");

        // Decorated Balance Value Label
        Label balanceValue = new Label(String.format("RM %.2f", balance));
        balanceValue.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #4CAF50;");

        content.getChildren().addAll(balanceLabel, balanceValue);

        // Add role-specific button
        if (user.getRole() == RoleEnum.CUSTOMER) {
            KButton topUpButton = new KButton("Top Up", () -> {
                if (user.getRole() == RoleEnum.CUSTOMER) {
                    CustomerViewModel.getTransactionViewModel().setTopupWithdrawUI(new TopupWithdrawUI(user,
                            TopupWithdrawUI.TransactionType.TOPUP));
                    CustomerViewModel.getTransactionViewModel()
                            .navigate(CustomerViewModel.getTransactionViewModel().getTopupWithdrawUI());
                    CustomerViewModel.navigate(CustomerViewModel.getTransactionViewModel().getNode());
                }
            });
            content.getChildren().add(topUpButton);
        } else {
            KButton withdrawButton = new KButton("Withdraw", () -> {
                if (user.getRole() == RoleEnum.VENDOR) {
                    VendorViewModel.getTransactionViewModel().setTopupWithdrawUI(new TopupWithdrawUI(user,
                            TopupWithdrawUI.TransactionType.WITHDRAW));
                    VendorViewModel.getTransactionViewModel()
                            .navigate(VendorViewModel.getTransactionViewModel().getTopupWithdrawUI());
                    VendorViewModel.navigate(VendorViewModel.getTransactionViewModel().getNode());

                } else if (user.getRole() == RoleEnum.RUNNER) {
                    RunnerViewModel.getTransactionViewModel().setTopupWithdrawUI(new TopupWithdrawUI(user,
                            TopupWithdrawUI.TransactionType.WITHDRAW));
                    RunnerViewModel.getTransactionViewModel()
                            .navigate(RunnerViewModel.getTransactionViewModel().getTopupWithdrawUI());
                    RunnerViewModel.navigate(RunnerViewModel.getTransactionViewModel().getNode());
                }
            });
            content.getChildren().add(withdrawButton);
        }

        if (user.getRole() == RoleEnum.VENDOR || user.getRole() == RoleEnum.RUNNER) {
            Label revenueSummary = new Label("Revenue Summary >");
            revenueSummary.setStyle(
                    "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0000EE; -fx-cursor: hand; -fx-underline: true;");

            revenueSummary.setOnMouseClicked(e -> {
                if (user.getRole() == RoleEnum.VENDOR) {
                    VendorViewModel.getTransactionViewModel().setRevenueSummary(new RevenueSummary(user));
                    VendorViewModel.getTransactionViewModel()
                            .navigate(VendorViewModel.getTransactionViewModel().getRevenueSummary());
                    VendorViewModel.navigate(VendorViewModel.getTransactionViewModel().getNode());
                } else if (user.getRole() == RoleEnum.RUNNER) {
                    RunnerViewModel.getTransactionViewModel().setRevenueSummary(new RevenueSummary(user));
                    RunnerViewModel.getTransactionViewModel()
                            .navigate(RunnerViewModel.getTransactionViewModel().getRevenueSummary());
                    RunnerViewModel.navigate(RunnerViewModel.getTransactionViewModel().getNode());
                }
            });

            content.getChildren().add(revenueSummary);
        }

        // Add a Separator
        KSeparator separator = new KSeparator();
        content.getChildren().add(separator);

        // Decorated Transaction History Label
        Label transactionHistoryLabel = new Label("Transaction History");
        transactionHistoryLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #555;");
        content.getChildren().add(transactionHistoryLabel);

        // Add DynamicSearchBarUI
        DynamicSearchBarUI<TransactionModel> searchBar = new DynamicSearchBarUI<>(
                TransactionServices.getTransactionByUserId(user.getId()), "transactionId", null, this::transactionBox);
        searchBar.setSearchBarVisible(false);

        content.getChildren().add(searchBar);

        return content;
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

        HBox contentBox = new HBox(new Card(box, 450, 100, null));
        contentBox.setPadding(new Insets(5, 0, 5, 0));
        contentBox.setAlignment(Pos.CENTER);
        return contentBox;
    }

}
