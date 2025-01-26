package com.Group3.foodorderingsystem.Module.Platform.Customer.Home.ui;

import java.util.List;
import java.util.Map;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.OrderModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Core.Services.VendorOrderServices;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.Card;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.DynamicSearchBarUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class VendorReview extends BaseContentPanel {

    private VendorModel vendor;
    private Map<Integer, List<OrderModel>> orderMap;
    private String selectedRating;

    public VendorReview(String vendorId) {
        super();
        this.vendor = UserServices.findVendorById(vendorId);
        this.orderMap = VendorOrderServices.getOrdersGroupedByRating(vendor);

        setHeader(header());
        setContent(content());

        setFooterHeight(0);
        setContentHeight(580);
    }

    private Node header() {
        return new TitleBackButton("Review - " + vendor.getShopName(), () -> {
            CustomerViewModel.getHomeViewModel().navigate(CustomerViewModel.getHomeViewModel().getMenuSelectionUI());
        });
    }

    private Node content() {
        Label overallRating = new Label("Overall Rating: " + String.format("%.2f",
                VendorOrderServices.getOverallRating(vendor.getId())));
        overallRating.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        int[] starCounts = {
                orderMap.get(1) != null ? orderMap.get(1).size() : 0,
                orderMap.get(2) != null ? orderMap.get(2).size() : 0,
                orderMap.get(3) != null ? orderMap.get(3).size() : 0,
                orderMap.get(4) != null ? orderMap.get(4).size() : 0,
                orderMap.get(5) != null ? orderMap.get(5).size() : 0
        };
        int totalReviews = 0;

        for (int count : starCounts) {
            totalReviews += count;
        }

        // GridPane for star ratings
        GridPane ratingGrid = new GridPane();
        ratingGrid.setHgap(20);
        ratingGrid.setVgap(15);
        ratingGrid.setPadding(new Insets(10));
        ratingGrid.setStyle(
                "-fx-background-color: #f9f9f9; -fx-border-color: #dcdcdc; -fx-border-radius: 8; -fx-padding: 10;");

        for (int i = 0; i < starCounts.length; i++) {
            Label starLabel = new Label((i + 1) + " Star");
            starLabel.setFont(Font.font("Arial", FontWeight.MEDIUM, 14));
            starLabel.setTextFill(Color.BLACK);

            ProgressBar progressBar = new ProgressBar();
            progressBar.setPrefWidth(280);
            progressBar.setProgress((double) starCounts[i] / totalReviews);
            progressBar.setStyle("-fx-accent: #4caf50;");

            Label countLabel = new Label(starCounts[i] + " reviews");
            countLabel.setFont(Font.font("Arial", 13));
            countLabel.setTextFill(Color.GRAY);

            ratingGrid.addRow(i, starLabel, progressBar, countLabel);
        }

        // VBox to hold dynamic content
        VBox dynamicContentBox = new VBox(20);
        dynamicContentBox.setAlignment(Pos.TOP_CENTER);

        // ComboBox for sorting reviews by rating
        ComboBox<String> sortComboBox = new ComboBox<>();
        sortComboBox.getItems().addAll("1 Star", "2 Star", "3 Star", "4 Star", "5 Star");
        selectedRating = "5 Star";
        sortComboBox.getSelectionModel().select(selectedRating);

        sortComboBox.setOnAction(event -> {
            selectedRating = sortComboBox.getSelectionModel().getSelectedItem();
            updateDynamicContent(dynamicContentBox);
        });

        // Initialize the dynamic content
        updateDynamicContent(dynamicContentBox);

        // Combine static and dynamic content
        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.TOP_LEFT);
        contentBox.setPadding(new Insets(20));
        contentBox.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 8; -fx-padding: 15;");

        contentBox.getChildren().addAll(overallRating, ratingGrid, sortComboBox, dynamicContentBox);
        return contentBox;
    }

    private void updateDynamicContent(VBox dynamicContentBox) {
        // Get the selected star rating and retrieve orders
        int selectedStar = Integer.parseInt(selectedRating.split(" ")[0]);
        List<OrderModel> orders = orderMap.get(selectedStar);

        // Clear existing content
        dynamicContentBox.getChildren().clear();

        if (orders != null && !orders.isEmpty()) {
            // DynamicSearchBarUI to display the reviews
            DynamicSearchBarUI<OrderModel> reviewUI = new DynamicSearchBarUI<>(
                    orders, "orderId", null, this::buildReviewCard);
            reviewUI.setSearchBarVisible(false);

            // Add the updated review UI to the dynamic content box
            dynamicContentBox.getChildren().add(reviewUI);
        } else {
            Label noReviewsLabel = new Label("No reviews found.");
            noReviewsLabel.setFont(Font.font("Arial", FontWeight.MEDIUM, 16));
            noReviewsLabel.setTextFill(Color.GRAY);

            dynamicContentBox.getChildren().add(noReviewsLabel);
        }
    }

    private Node buildReviewCard(OrderModel order) {
        VBox reviewCard = new VBox(10);
        reviewCard.setPadding(new Insets(10));
        reviewCard.setStyle("-fx-padding: 10;");

        Label orderIdLabel = new Label("Order ID: " + order.getOrderId());
        orderIdLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        orderIdLabel.setTextFill(Color.BLACK);

        Label customerLabel = new Label("Customer: " + UserServices.findCustomerById(order.getCustomer()).getName());
        customerLabel.setFont(Font.font("Arial", FontWeight.MEDIUM, 14));
        customerLabel.setTextFill(Color.BLACK);

        // Render stars for the rating
        HBox starsBox = new HBox(5);
        starsBox.setAlignment(Pos.CENTER_LEFT);
        int rating = order.getRating(); // Assuming `getRating()` returns an integer value

        for (int i = 0; i < 5; i++) {
            Label starLabel = new Label(i < rating ? "★" : "☆");
            starLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            starLabel.setTextFill(i < rating ? Color.GOLD : Color.GRAY);
            starsBox.getChildren().add(starLabel);
        }

        Label reviewLabel = new Label(
                "Review: " + (order.getReview() != null ? order.getReview() : "No review provided"));
        reviewLabel.setFont(Font.font("Arial", FontWeight.MEDIUM, 14));
        reviewLabel.setTextFill(Color.BLACK);

        reviewCard.getChildren().addAll(orderIdLabel, starsBox, customerLabel, reviewLabel);
        return new Card(reviewCard, 300, 150, null);
    }
}
