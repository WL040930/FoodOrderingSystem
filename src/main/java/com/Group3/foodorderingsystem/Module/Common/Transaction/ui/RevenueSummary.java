package com.Group3.foodorderingsystem.Module.Common.Transaction.ui;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel.TransactionType;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;
import com.Group3.foodorderingsystem.Core.Services.TransactionServices;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.Card;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.DynamicSearchBarUI;
import com.Group3.foodorderingsystem.Module.Platform.Runner.RunnerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
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
        displayByComboBox.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-border-color: #cccccc;" +
                        "-fx-border-radius: 5px;" +
                        "-fx-padding: 5px;" +
                        "-fx-font-size: 14px;");
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
        monthComboBox.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-border-color: #cccccc;" +
                        "-fx-border-radius: 5px;" +
                        "-fx-padding: 5px;" +
                        "-fx-font-size: 14px;");
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
        yearComboBox.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-border-color: #cccccc;" +
                        "-fx-border-radius: 5px;" +
                        "-fx-padding: 5px;" +
                        "-fx-font-size: 14px;");
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
        datePicker.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-border-color: #cccccc;" +
                        "-fx-border-radius: 5px;" +
                        "-fx-padding: 5px;" +
                        "-fx-font-size: 14px;");
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
        if (content.getChildren().size() > 1) {
            content.getChildren().subList(2, content.getChildren().size()).clear();
        }

        content.getChildren().add(buildSummaryCard(
                period != null ? period : (month != null ? capitalizeFirstLetter(month) : year.toString()),
                totalRevenue));

        if (month != null) {
            content.getChildren().add(buildMonthlyGraph(month, year, transactions));
        } else if (year != null) {
            content.getChildren().add(buildYearlyGraph(year, transactions));
        }

        content.getChildren().add(buildTransactionHistory(transactions));
    }

    private Node buildMonthlyGraph(String month, Integer year, List<TransactionModel> transactions) {
        int daysInMonth = LocalDate.of(year, getMonthNumber(month), 1).lengthOfMonth();
        double[] dailyTotals = new double[daysInMonth];

        transactions.forEach(transaction -> {
            LocalDate date = convertToLocalDate(transaction.getTransactionDate());
            if (date.getMonthValue() == getMonthNumber(month)) {
                dailyTotals[date.getDayOfMonth() - 1] += transaction.getAmount();
            }
        });

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Revenue");

        for (int i = 0; i < daysInMonth; i++) {
            series.getData().add(new XYChart.Data<>(String.valueOf(i + 1), dailyTotals[i]));
        }

        LineChart<String, Number> lineChart = createLineChart("Days", "Revenue (RM)",
                "Daily Revenue for " + capitalizeFirstLetter(month) + " " + year, series);
        return lineChart;
    }

    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Node buildYearlyGraph(Integer year, List<TransactionModel> transactions) {
        double[] monthlyTotals = new double[12];

        transactions.forEach(transaction -> {
            LocalDate date = convertToLocalDate(transaction.getTransactionDate());
            if (date.getYear() == year) {
                monthlyTotals[date.getMonthValue() - 1] += transaction.getAmount();
            }
        });

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Revenue");

        for (int i = 0; i < 12; i++) {
            series.getData()
                    .add(new XYChart.Data<>(
                            capitalizeFirstLetter(LocalDate.of(year, i + 1, 1).getMonth().toString().toLowerCase()),
                            monthlyTotals[i]));
        }

        LineChart<String, Number> lineChart = createLineChart("Months", "Revenue (RM)", "Monthly Revenue for " + year,
                series);
        return lineChart;
    }

    private LineChart<String, Number> createLineChart(String xAxisLabel, String yAxisLabel, String title,
            XYChart.Series<String, Number> series) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel(xAxisLabel);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(yAxisLabel);

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(title);
        lineChart.getData().add(series);
        lineChart.setLegendVisible(false);

        // Set fixed height for the graph
        lineChart.setPrefHeight(400); // Preferred height
        lineChart.setMinHeight(400); // Minimum height
        lineChart.setMaxHeight(400); // Maximum height

        return lineChart;
    }

    private int getMonthNumber(String month) {
        try {
            return Month.valueOf(month.toUpperCase()).getValue();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid month name: " + month);
        }
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

    private Node buildTransactionHistory(List<TransactionModel> transactions) {
        DynamicSearchBarUI<TransactionModel> transactionsSearchUI = new DynamicSearchBarUI<>(transactions,
                "transactionId", null, this::transactionBox);
        transactionsSearchUI.setSearchBarVisible(false);
        return transactionsSearchUI;
    }

    private Double calculateTotalRevenue(List<TransactionModel> transactions) {
        return transactions.stream()
                .mapToDouble(TransactionModel::getAmount)
                .sum();
    }

    private String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
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

        HBox contentBox = new HBox(new Card(box, 470, 100, null));
        contentBox.setPadding(new Insets(5, 0, 5, 0));
        contentBox.setAlignment(Pos.CENTER);
        return contentBox;
    }
}
