package com.Group3.foodorderingsystem.Module.Common.Transaction.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;
import com.Group3.foodorderingsystem.Core.Services.TransactionServices;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.Card;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Runner.RunnerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class RevenueSummary extends BaseContentPanel {

    private final User user;
    private DISPLAY_BY displayBy = DISPLAY_BY.DAILY;
    private VBox content;

    public enum DISPLAY_BY {
        DAILY, MONTHLY, YEARLY
    }

    public RevenueSummary(User user) {
        this.user = user;

        setHeader(createHeader());
        setContent(createContent());

        setContentHeight(580);
        setFooterHeight(0);
    }

    private Node createHeader() {
        return new TitleBackButton("Revenue Summary", () -> navigateBack());
    }

    private void navigateBack() {
        if (user.getRole() == RoleEnum.VENDOR) {
            VendorViewModel.getTransactionViewModel()
                    .navigate(VendorViewModel.getTransactionViewModel().getUserFinance());
            VendorViewModel.navigate(VendorViewModel.getTransactionViewModel().getNode());
        } else if (user.getRole() == RoleEnum.RUNNER) {
            RunnerViewModel.getTransactionViewModel()
                    .navigate(RunnerViewModel.getTransactionViewModel().getUserFinance());
            RunnerViewModel.navigate(RunnerViewModel.getTransactionViewModel().getNode());
        }
    }

    private Node createContent() {
        VBox mainContent = new VBox(10);
        mainContent.setPadding(new javafx.geometry.Insets(10));

        ComboBox<String> displayByComboBox = createDisplayByComboBox();
        content = new VBox(10);

        mainContent.getChildren().addAll(displayByComboBox, content);
        updateContent();

        return mainContent;
    }

    private ComboBox<String> createDisplayByComboBox() {
        ComboBox<String> displayByComboBox = new ComboBox<>();
        displayByComboBox.getItems().addAll("Daily", "Monthly", "Yearly");
        displayByComboBox.getSelectionModel().select(displayBy.toString());
        displayByComboBox.setOnAction(e -> {
            String selected = displayByComboBox.getValue();
            displayBy = DISPLAY_BY.valueOf(selected.toUpperCase());
            updateContent();
        });
        return displayByComboBox;
    }

    private void updateContent() {
        content.getChildren().clear();

        switch (displayBy) {
            case DAILY:
                renderDailyContent();
                break;
            case MONTHLY:
                renderMonthlyContent();
                break;
            case YEARLY:
                renderYearlyContent();
                break;
        }
    }

    private void renderDailyContent() {
        DatePicker datePicker = createDatePicker();
        content.getChildren().addAll(new Label("Select Date"), datePicker);
    }

    private void renderMonthlyContent() {
        ComboBox<String> monthComboBox = new ComboBox<>();
        monthComboBox.getItems().addAll(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December");

        monthComboBox.setOnAction(e -> {
            String selectedMonth = monthComboBox.getValue();
            String uppercaseMonth = selectedMonth.toUpperCase(); // Convert to uppercase for compatibility
            int year = LocalDate.now().getYear(); // Use the current year
            updateSummary(null, uppercaseMonth, year); // Pass uppercase month name
        });

        content.getChildren().addAll(new Label("Select Month"), monthComboBox);
    }

    private void renderYearlyContent() {
        ComboBox<Integer> yearComboBox = new ComboBox<>();
        int currentYear = LocalDate.now().getYear();
        IntStream.rangeClosed(currentYear - 10, currentYear)
                .forEach(yearComboBox.getItems()::add);

        yearComboBox.setOnAction(e -> {
            Integer selectedYear = yearComboBox.getValue();
            updateSummary(null, null, selectedYear);
        });

        content.getChildren().addAll(new Label("Select Year"), yearComboBox);
    }

    private DatePicker createDatePicker() {
        DatePicker datePicker = new DatePicker();
        datePicker.setDayCellFactory(createDayCellFactory());
        datePicker.getEditor().setDisable(true);

        datePicker.setOnAction(e -> {
            LocalDate selectedDate = datePicker.getValue();
            updateSummary(selectedDate.toString(), null, null);
        });

        return datePicker;
    }

    private Callback<DatePicker, DateCell> createDayCellFactory() {
        return dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isAfter(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #d3d3d3;");
                }
            }
        };
    }

    private void updateSummary(String period, String month, Integer year) {
        List<TransactionModel> transactions = TransactionServices.getTransactions(
                user.getId(),
                period != null ? LocalDate.parse(period) : null,
                month,
                year);

        Double totalRevenue = calculateTotalRevenue(transactions);

        // Remove old cards and add new content
        content.getChildren().removeIf(node -> node instanceof Card);
        content.getChildren().add(buildSummaryCard(
                period != null ? period : (month != null ? capitalizeFirstLetter(month) : year.toString()),
                totalRevenue));
    }

    private Node buildSummaryCard(String period, Double amount) {
        VBox vbox = new VBox(3);

        vbox.getChildren().addAll(
                new Label("Selected Period: "),
                new Label(period),
                new Label(),
                new Label("Total Revenue: "),
                new Label("RM " + String.format("%.2f", amount)));

        return new Card(vbox, 300, 150, null);
    }

    private Double calculateTotalRevenue(List<TransactionModel> transactions) {
        return transactions.stream()
                .mapToDouble(TransactionModel::getAmount)
                .sum();
    }

    private String getMonthName(String month) {
        int monthIndex = Integer.parseInt(month) - 1; // Convert "01" to 0-based index
        return java.time.Month.of(monthIndex + 1).name(); // Convert to Month Enum and get name
    }

    private String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

}
