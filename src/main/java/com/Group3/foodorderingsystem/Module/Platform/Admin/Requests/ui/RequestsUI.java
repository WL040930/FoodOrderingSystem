package com.Group3.foodorderingsystem.Module.Platform.Admin.Requests.ui;

import java.io.ObjectInputFilter.Status;

import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TopUpWithdrawModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Services.TopUpWithdrawServices;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.Card;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Common.Transaction.ui.TopupWithdrawUI;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.DynamicSearchBarUI;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Vouchers.widgets.KButton;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;

public class RequestsUI extends BaseContentPanel {

    private final ObjectProperty<Status> selectedOption = new SimpleObjectProperty<>(Status.UNDECIDED);
    private VBox contentContainer;
    DynamicSearchBarUI<TopUpWithdrawModel> searchBar;

    public RequestsUI() {
        super();

        setHeader(header());
        setContent(content());
        setFooterHeight(0);
        setContentHeight(570);
    }

    private Node header() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);

        TitleBackButton backButton = new TitleBackButton("Requests");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        ComboBox<Status> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(Status.values());
        comboBox.setValue(selectedOption.get());

        comboBox.setOnAction(event -> {
            selectedOption.set(comboBox.getValue());
        });

        HBox.setMargin(comboBox, new Insets(0, 10, 0, 0));

        header.getChildren().addAll(backButton, spacer, comboBox);

        return header;
    }

    private Node content() {
        contentContainer = new VBox();
        contentContainer.setAlignment(Pos.TOP_CENTER);
        contentContainer.setSpacing(10);

        searchBar = new DynamicSearchBarUI<>(
                TopUpWithdrawServices.getTopUpWithdrawListByStatus(selectedOption.get()), "userId", null,
                this::buildCard);

        selectedOption.addListener((observable, oldValue, newValue) -> {
            updateSearchBar(newValue);
        });

        contentContainer.getChildren().add(searchBar);

        return contentContainer;
    }

    private void updateSearchBar(Status newStatus) {
        // Remove the old searchBar and create a new one
        contentContainer.getChildren().remove(searchBar);
        searchBar = new DynamicSearchBarUI<>(
                TopUpWithdrawServices.getTopUpWithdrawListByStatus(newStatus),
                "TopUpWithdrawModelId",
                null,
                this::buildCard);
        contentContainer.getChildren().add(searchBar);
    }

    public ObjectProperty<Status> selectedOptionProperty() {
        return selectedOption;
    }

    public Status getSelectedOption() {
        return selectedOption.get();
    }

    public void setSelectedOption(Status status) {
        this.selectedOption.set(status);
    }

    private Node buildCard(TopUpWithdrawModel topUpWithdrawModel) {
        VBox contentCard = new VBox();
        contentCard.setSpacing(3);

        // Label for transaction type
        Label transactionTypeLabel = new Label(topUpWithdrawModel.getTransactionType().toString());
        transactionTypeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        // User details
        User user = UserServices.findUserById(topUpWithdrawModel.getUserId());
        Label requestId = new Label("Request ID: " + topUpWithdrawModel.getTopUpWithdrawModelId());
        Label username = new Label("Username: " + user.getName());
        Label email = new Label("Email: " + user.getEmail());
        Label amount = new Label("Amount: " + String.format("RM %.2f", topUpWithdrawModel.getAmount()));

        // Add labels to the content card
        contentCard.getChildren().addAll(transactionTypeLabel, new Label(), requestId, username, email, amount);

        // Add action buttons if status is UNDECIDED
        if (getSelectedOption() == Status.UNDECIDED) {
            HBox buttonBox = new HBox();
            buttonBox.setAlignment(Pos.CENTER_RIGHT);
            buttonBox.setSpacing(10);

            // Reject button
            KButton rejectButton = new KButton("Reject", () -> {
                TopUpWithdrawModel model = TopUpWithdrawServices.updateStatus(topUpWithdrawModel, Status.REJECTED);
                if (model == null) {
                    System.out.println("Failed to update status");
                } else {
                    System.out.println("Status updated successfully");
                }
                updateSearchBar(getSelectedOption());
            });
            rejectButton.setBackgroundColor(KButton.red);

            // Approve button
            KButton approveButton = new KButton("Approve", () -> {
                TopUpWithdrawModel model = TopUpWithdrawServices.updateStatus(topUpWithdrawModel, Status.ALLOWED);
                if (model == null) {
                    System.out.println("Failed to update status");
                } else {
                    System.out.println("Status updated successfully");
                }

                updateSearchBar(getSelectedOption());
            });
            approveButton.setBackgroundColor(KButton.green);

            buttonBox.getChildren().addAll(rejectButton, approveButton);
            contentCard.getChildren().add(buttonBox);
        }

        VBox cardContainer = new VBox();
        cardContainer.getChildren().add(new Card(contentCard, 300, 100, null));
        cardContainer.setPadding(new Insets(0, 10, 0, 10));
        return cardContainer;
    }

}
