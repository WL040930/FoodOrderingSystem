package com.Group3.foodorderingsystem.Module.Common.Transaction.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.RunnerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Services.TopUpWithdrawServices;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.BottomButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.PopupMessage;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Runner.RunnerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class TopupWithdrawUI extends BaseContentPanel {

    enum TransactionType {
        TOPUP,
        WITHDRAW
    }

    private User user;
    private TransactionType transactionType;
    private TextField amountField;

    public TopupWithdrawUI(User user, TransactionType transactionType) {
        super();
        this.user = user;
        this.transactionType = transactionType;

        setHeader(header());
        setContent(content());
        setFooter(footer());
    }

    private Node header() {
        return new TitleBackButton("", () -> {
            switch (user.getRole()) {
                case CUSTOMER:
                    CustomerViewModel.getTransactionViewModel()
                            .navigate(CustomerViewModel.getTransactionViewModel().getUserFinance());
                    CustomerViewModel.navigate(CustomerViewModel.getTransactionViewModel().getNode());
                    break;

                case VENDOR:
                    VendorViewModel.getTransactionViewModel()
                            .navigate(VendorViewModel.getTransactionViewModel().getUserFinance());
                    VendorViewModel.navigate(VendorViewModel.getTransactionViewModel().getNode());
                    break;

                case RUNNER:
                    RunnerViewModel.getTransactionViewModel()
                            .navigate(RunnerViewModel.getTransactionViewModel().getUserFinance());
                    RunnerViewModel.navigate(RunnerViewModel.getTransactionViewModel().getNode());
                    break;

                default:
                    break;
            }
        });
    }

    private Node content() {
        VBox vBox = new VBox(10);
        Label label = new Label(transactionType == TransactionType.TOPUP ? "Top Up" : "Withdraw");
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label instruction = new Label("Enter the amount you wish to "
                + (transactionType == TransactionType.TOPUP ? "top up" : "withdraw") + " from your account");
        instruction.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");

        amountField = new TextField();
        amountField.setStyle("-fx-font-size: 14px;");

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: red;");

        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            errorLabel.setText("");

            if (!newValue.matches("\\d*(\\.\\d{0,2})?")) {
                amountField.setText(oldValue);
            }

            if (transactionType == TransactionType.WITHDRAW) {
                try {
                    double enteredAmount = Double.parseDouble(amountField.getText());
                    double maxAmount = 0;

                    switch (user.getRole()) {
                        case RUNNER:
                            RunnerModel runner = UserServices.findRunnerById(user.getId());
                            maxAmount = runner.getRevenue();
                            break;
                        case VENDOR:
                            VendorModel vendor = UserServices.findVendorById(user.getId());
                            maxAmount = vendor.getRevenue();
                            break;

                        default:
                            break;
                    }

                    amountField.setPromptText("Max: " + String.format("%.2f", maxAmount));

                    if (enteredAmount > maxAmount) {
                        errorLabel
                                .setText("Amount cannot exceed your balance of RM " + String.format("%.2f", maxAmount));
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        });

        vBox.getChildren().addAll(label, instruction, amountField, errorLabel);

        return vBox;
    }

    private Node footer() {
        return new BottomButton(transactionType == TransactionType.TOPUP ? "Top Up" : "Withdraw", () -> {
            try {
                double enteredAmount = Double.parseDouble(amountField.getText());
                double maxAmount = 0;

                // Determine the maximum allowable amount based on the user's role
                switch (user.getRole()) {
                    case RUNNER:
                        RunnerModel runner = UserServices.findRunnerById(user.getId());
                        maxAmount = runner.getRevenue();
                        break;
                    case VENDOR:
                        VendorModel vendor = UserServices.findVendorById(user.getId());
                        maxAmount = vendor.getRevenue();
                        break;
                    case CUSTOMER:
                        CustomerModel customer = UserServices.findCustomerById(user.getId());
                        maxAmount = customer.getBalance();
                        break;
                    default:
                        break;
                }

                // Validation to block further actions if the amount exceeds the maximum
                if (transactionType == TransactionType.WITHDRAW && enteredAmount > maxAmount) {
                    System.out.println("Transaction blocked: Amount exceeds the balance of RM "
                            + String.format("%.2f", maxAmount));
                    return;
                }

                TopUpWithdrawServices.createNewRequest(user.getId(), enteredAmount,
                        transactionType == TransactionType.TOPUP ? TransactionModel.TransactionType.TOPUP
                                : TransactionModel.TransactionType.WITHDRAWAL);

                PopupMessage.showMessage("Request has been sent to Admin.", "success", () -> {
                    switch (user.getRole()) {
                        case CUSTOMER:
                            CustomerViewModel.initTransactionViewModel();
                            CustomerViewModel.initNotificationViewModel();
                            CustomerViewModel.navigate(CustomerViewModel.getTransactionViewModel().getNode());
                            break;

                        case VENDOR:
                            VendorViewModel.initTransactionViewModel();
                            VendorViewModel.navigate(VendorViewModel.getTransactionViewModel().getNode());
                            break;

                        case RUNNER:
                            RunnerViewModel.initTransactionViewModel();
                            RunnerViewModel.navigate(RunnerViewModel.getTransactionViewModel().getNode());
                            break;
                    
                        default:
                            break;
                    }
                });

            } catch (NumberFormatException e) {
                System.out.println("Invalid amount entered!");
            }
        });
    }

}
