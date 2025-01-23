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

    private User user;
    private DISPLAY_BY displayBy = DISPLAY_BY.DAILY;
    private VBox content;

    public enum DISPLAY_BY {
        DAILY,
        MONTHLY,
        YEARLY
    }

    public RevenueSummary(User user) {
        super();
        this.user = user;

        setHeader(header());
        setContent(content());

        setContentHeight(580);
        setFooterHeight(0);
    }

    private Node header() {
        return new TitleBackButton("Revenue Summary", () -> {
            if (user.getRole() == RoleEnum.VENDOR) {
                VendorViewModel.getTransactionViewModel()
                        .navigate(VendorViewModel.getTransactionViewModel().getUserFinance());
                VendorViewModel.navigate(VendorViewModel.getTransactionViewModel().getNode());
            } else if (user.getRole() == RoleEnum.RUNNER) {
                RunnerViewModel.getTransactionViewModel()
                        .navigate(RunnerViewModel.getTransactionViewModel().getUserFinance());
                RunnerViewModel.navigate(RunnerViewModel.getTransactionViewModel().getNode());
            }
        });
    }

    private Node content() {
        VBox mainContent = new VBox(10);

        content = new VBox(10);

        ComboBox<String> displayByComboBox = new ComboBox<>();
        displayByComboBox.getItems().addAll("Daily", "Monthly", "Yearly");
        displayByComboBox.getSelectionModel().select(displayBy.toString());
        displayByComboBox.setOnAction(e -> {
            String selected = displayByComboBox.getSelectionModel().getSelectedItem();
            switch (selected) {
                case "Daily":
                    displayBy = DISPLAY_BY.DAILY;
                    break;
                case "Monthly":
                    displayBy = DISPLAY_BY.MONTHLY;
                    break;
                case "Yearly":
                    displayBy = DISPLAY_BY.YEARLY;
                    break;
                default:
                    break;
            }
            updateContent();
        });

        mainContent.getChildren().addAll(displayByComboBox, content);
        mainContent.setPadding(new javafx.geometry.Insets(10));

        updateContent();

        return mainContent;
    }

    private void updateContent() {
        content.getChildren().removeAll(content.getChildren());

        switch (displayBy) {
            case DAILY:
                DatePicker datePicker = new DatePicker();
                Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        if (date.isAfter(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #d3d3d3;");
                        }
                    }
                };
                datePicker.setDayCellFactory(dayCellFactory);
                datePicker.getEditor().setDisable(true);
                datePicker.setOnAction(e -> {
                    LocalDate selectedDate = datePicker.getValue();
                    if (content.getChildren().size() > 2) {
                        content.getChildren().remove(content.getChildren().size() - 1);
                    }

                    List<TransactionModel> transactions = TransactionServices.getTransactions(user.getId(),
                            selectedDate, null, null);
                    Double totalRevenue = calculateTotalRevenue(transactions);
                    content.getChildren().add(buildSummaryContent(selectedDate.toString(), totalRevenue));
                });
                content.getChildren().addAll(new Label("Select Date"), datePicker);
                break;

            case MONTHLY:
                ComboBox<String> monthComboBox = new ComboBox<>();
                monthComboBox.getItems().addAll(
                        "January", "February", "March", "April", "May", "June", "July", "August", "September",
                        "October", "November", "December");
                monthComboBox.setOnAction(e -> {
                    String selectedMonth = monthComboBox.getValue();
                    if (content.getChildren().size() > 2) {
                        content.getChildren().remove(content.getChildren().size() - 1);
                    }
                    int year = LocalDate.now().getYear();
                    List<TransactionModel> transactions = TransactionServices.getTransactions(user.getId(), null,
                            selectedMonth, year);
                    Double totalRevenue = calculateTotalRevenue(transactions);
                    content.getChildren().add(buildSummaryContent(selectedMonth, totalRevenue));
                });
                content.getChildren().addAll(new Label("Select Month"), monthComboBox);
                break;

            case YEARLY:
                ComboBox<Integer> yearComboBox = new ComboBox<>();
                int currentYear = LocalDate.now().getYear();
                IntStream.rangeClosed(currentYear - 10, currentYear)
                        .forEach(yearComboBox.getItems()::add);
                yearComboBox.setOnAction(e -> {
                    Integer selectedYear = yearComboBox.getValue();
                    if (content.getChildren().size() > 2) {
                        content.getChildren().remove(content.getChildren().size() - 1);
                    }
                    List<TransactionModel> transactions = TransactionServices.getTransactions(user.getId(), null, null,
                            selectedYear);
                    Double totalRevenue = calculateTotalRevenue(transactions);
                    content.getChildren().add(buildSummaryContent(selectedYear.toString(), totalRevenue));
                });
                content.getChildren().addAll(new Label("Select Year"), yearComboBox);
                break;

            default:
                break;
        }
    }

    private Node buildSummaryContent(String period, Double amount) {
        VBox vbox = new VBox(3);

        Label selectedPeriodLabel = new Label("Selected Period: ");
        Label periodLabel = new Label(period);
        Label space = new Label();
        Label totalRevenueLabel = new Label("Total Revenue: ");
        Label totalRevenue = new Label("RM " + String.format("%.2f", amount));

        vbox.getChildren().addAll(selectedPeriodLabel, periodLabel, space, totalRevenueLabel, totalRevenue);

        Card card = new Card(vbox, 300, 150, null);

        return card;
    }

    private Double calculateTotalRevenue(List<TransactionModel> transactions) {
        return transactions.stream()
                .mapToDouble(TransactionModel::getAmount)
                .sum();
    }

}
