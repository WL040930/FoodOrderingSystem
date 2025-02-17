package com.Group3.foodorderingsystem.Module.Common.Transaction.ui;

import java.util.ArrayList;
import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.RunnerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Services.TopUpWithdrawServices;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Finance.widgets.DynamicButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.BottomButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.PopupMessage;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Runner.RunnerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TopupWithdrawUI extends BaseContentPanel {

    enum TransactionType {
        TOPUP,
        WITHDRAW
    }

    private User user;
    private TransactionType transactionType;
    private TextField amountField;
    private DynamicButton rm50, rm100, rm200, rm500, rm1000, others;
    private List<DynamicButton> buttonList;

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
        vBox.setPadding(new Insets(10)); // Padding for the entire content
        Label label = new Label(transactionType == TransactionType.TOPUP ? "Top Up" : "Withdraw");
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label instruction = new Label("Enter the amount you wish to "
                + (transactionType == TransactionType.TOPUP ? "top up" : "withdraw") + " from your account");
        instruction.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");

        amountField = new TextField();
        amountField.setStyle("-fx-font-size: 14px;");
        configureDecimalInput(amountField);

        // Create buttons and add them to a list for state management
        buttonList = new ArrayList<>();
        rm50 = createDynamicButton("RM 50", "50.00");
        rm100 = createDynamicButton("RM 100", "100.00");
        rm200 = createDynamicButton("RM 200", "200.00");
        rm500 = createDynamicButton("RM 500", "500.00");
        rm1000 = createDynamicButton("RM 1000", "1000.00");
        others = createDynamicButton("Others", "");

        buttonList.add(rm50);
        buttonList.add(rm100);
        buttonList.add(rm200);
        buttonList.add(rm500);
        buttonList.add(rm1000);
        buttonList.add(others);

        // Split buttons into two rows
        HBox firstRow = new HBox(10, rm50, rm100, rm200);
        HBox secondRow = new HBox(10, rm500, rm1000, others);

        // Make buttons expand equally within the rows
        HBox.setHgrow(rm50, Priority.ALWAYS);
        HBox.setHgrow(rm100, Priority.ALWAYS);
        HBox.setHgrow(rm200, Priority.ALWAYS);
        HBox.setHgrow(rm500, Priority.ALWAYS);
        HBox.setHgrow(rm1000, Priority.ALWAYS);
        HBox.setHgrow(others, Priority.ALWAYS);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: red;");

        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            // First part: Check if the new value matches any button value
            boolean isMatchFound = false;
            for (DynamicButton button : buttonList) {
                if (newValue.equals(button.getValue())) {
                    button.setSelected(true);
                    isMatchFound = true;
                } else {
                    button.setSelected(false);
                }
            }

            // If no match is found, select the "Others" button
            if (!isMatchFound) {
                others.setSelected(true);
            }

            // Second part: Validate the amount entered
            errorLabel.setText(""); // Reset error label
            if (!newValue.matches("\\d*(\\.\\d{0,2})?")) {
                amountField.setText(oldValue); // Revert to the old value if format is invalid
            }

            // Handle transaction type and max amount validation
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

        vBox.getChildren().addAll(label, instruction, amountField, firstRow, secondRow, errorLabel);

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

    private void configureDecimalInput(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty() || newValue.matches("\\d*\\.?\\d{0,2}")) {
                textField.setText(newValue);
            } else {
                textField.setText(oldValue);
            }
        });

        textField.focusedProperty().addListener((observable, oldFocused, newFocused) -> {
            if (!newFocused) {
                if (!textField.getText().isEmpty()) {
                    try {
                        double value = Double.parseDouble(textField.getText());
                        textField.setText(String.format("%.2f", value));
                    } catch (NumberFormatException e) {
                        textField.setText("");
                    }
                }
            }
        });
    }

    private DynamicButton createDynamicButton(String label, String value) {
        return new DynamicButton(label, () -> {
            // Set the selected button state to true and reset others
            buttonList.forEach(button -> button.setSelected(false));
            DynamicButton selectedButton = buttonList.stream()
                    .filter(button -> button.getText().equals(label))
                    .findFirst()
                    .orElse(null);

            if (selectedButton != null) {
                selectedButton.setSelected(true);
            }

            // Update the amount field
            if (!value.isEmpty()) {
                amountField.setText(value);
            } else {
                amountField.clear(); // For "Others"
            }

        }, false, value);
    }
}
