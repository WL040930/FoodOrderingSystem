package com.Group3.foodorderingsystem.Module.Platform.Admin.Finance.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Services.TransactionServices;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Finance.widgets.DynamicButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.BottomButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.PopupMessage;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.List;

public class AdminTopUpUI extends BaseContentPanel {

    private CustomerModel customer;
    private TextField amount;
    private DynamicButton rm50, rm100, rm200, rm500, rm1000, others;
    private List<DynamicButton> buttonList;

    public AdminTopUpUI(CustomerModel customer) {
        super();
        this.customer = customer;

        setHeader(header());
        setContent(content());
        setFooter(footer());
    }

    private Node header() {
        return new TitleBackButton("Top Up", () -> {
            AdminViewModel.getFinanceViewModel()
                    .navigate(AdminViewModel.getFinanceViewModel().getFinanceCustomerListUI());
        });
    }

    private Node content() {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20)); // Padding for the entire content

        Label label = new Label("You are topping up the account " + customer.getEmail());
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        amount = new TextField();
        amount.setStyle("-fx-min-height: 30px; -fx-font-size: 14px; -fx-padding: 4px;");
        amount.setPromptText("Enter amount (e.g., 100.00)");
        configureDecimalInput(amount);

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

        // Add both rows to the VBox
        vbox.getChildren().addAll(label, amount, firstRow, secondRow);

        // Add listener to the amount field to check for matching values
        amount.textProperty().addListener((observable, oldValue, newValue) -> {
            // Check if the new value matches any button value
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
        });

        return vbox;
    }

    private Node footer() {
        return new BottomButton("Top Up", () -> {
            double topUpAmount = 0.0;

            if (!amount.getText().isEmpty()) {
                topUpAmount = Double.parseDouble(amount.getText());
            }

            TransactionModel model = TransactionServices.createTransaction(
                    topUpAmount,
                    TransactionModel.TransactionType.TOPUP,
                    customer.getId());

            if (model != null) {
                PopupMessage.showMessage("Top up request successful.", "success", () -> {
                    AdminViewModel.initFinanceViewModel();
                    AdminViewModel.getFinanceViewModel()
                            .navigate(AdminViewModel.getFinanceViewModel().getFinanceCustomerListUI());
                });
            } else {
                PopupMessage.showMessage("Top up request failed. Please try again later.", "error", () -> {
                });
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
                amount.setText(value);
            } else {
                amount.clear(); // For "Others"
            }

        }, false, value);
    }
}
