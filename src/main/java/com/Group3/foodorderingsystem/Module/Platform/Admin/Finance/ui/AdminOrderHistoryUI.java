package com.Group3.foodorderingsystem.Module.Platform.Admin.Finance.ui;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.OrderModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;
import com.Group3.foodorderingsystem.Core.Services.CustomerOrderServices;
import com.Group3.foodorderingsystem.Core.Services.VendorOrderServices;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;
import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.Card;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Order.ui.OrderDetailsUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AdminOrderHistoryUI extends BaseContentPanel {

    public AdminOrderHistoryUI(CustomerModel customer) {
        super();

        // TODO: FIX THIS 
        String cssPath = "/com/Group3/foodorderingsystem/Module/Platform/Customer/Order/ui/OrderDetailsUI.css";
        URL cssURL = getClass().getResource(cssPath);

        if (cssURL != null) {
            this.getStylesheets().add(cssURL.toExternalForm());
        } else {
            System.out.println("CSS file not found: " + cssPath);
        }

        setHeader(header());
        setContent(
                createOrdersContent(CustomerOrderServices.getOrderByCustomerId(customer.getId()), "No order found."));

        setContentHeight(570);
    }

    private Node header() {
        return new TitleBackButton("Order History", () -> {
            AdminViewModel.getFinanceViewModel()
                    .navigate(AdminViewModel.getFinanceViewModel().getFinanceCustomerListUI());
        });
    }

    private Node createOrdersContent(List<OrderModel> orders, String emptyMessage) {
        VBox allOrders = new VBox(15);
        allOrders.setPadding(new Insets(30, 0, 0, 15));
        allOrders.setStyle("-fx-background-color: #f8fafc;");

        HBox filterBox = new HBox(10);
        ComboBox<String> filterComboBox = new ComboBox<>();
        filterBox.setAlignment(Pos.CENTER_RIGHT);
        filterComboBox.getStyleClass().add("filter-combobox");
        filterComboBox.getItems().addAll("All", "Day", "Week", "Month");
        filterComboBox.setValue("All");

        filterBox.getChildren().add(filterComboBox);
        allOrders.getChildren().add(filterBox);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(allOrders);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #f8fafc; -fx-background-color: transparent;");

        if (orders.isEmpty()) {
            Label noOrdersLabel = new Label(emptyMessage);
            noOrdersLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");
            allOrders.getChildren().add(noOrdersLabel);
        } else {
            for (OrderModel order : orders) {
                Node orderBox = createOrderBox(order);
                allOrders.getChildren().add(orderBox);
            }
        }

        filterComboBox.setOnAction(e -> {
            String filter = filterComboBox.getValue();
            List<OrderModel> filteredOrders = VendorOrderServices.filterOrders(orders, filter);
            allOrders.getChildren().clear();
            allOrders.getChildren().add(filterBox);

            if (filteredOrders.isEmpty()) {
                Label noOrdersLabel = new Label(emptyMessage);
                noOrdersLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");
                allOrders.getChildren().add(noOrdersLabel);
            } else {
                for (OrderModel order : filteredOrders) {
                    Node orderBox = createOrderBox(order);
                    allOrders.getChildren().add(orderBox);
                }
            }
        });

        return scrollPane;
    }

    private Node createOrderBox(OrderModel order) {
        VBox orderContainer = new VBox(5); // Main container for each order
        orderContainer.setPadding(new Insets(10));
        orderContainer.getStyleClass().add("order-container");

        // Order ID at the top of each order box
        Label orderIdLabel = new Label("Order ID: " + order.getOrderId());
        orderIdLabel.getStyleClass().add("order-id-label");
        orderContainer.getChildren().add(orderIdLabel);

        // HBox to hold the image and details side by side
        HBox contentBox = new HBox(10);
        contentBox.setAlignment(Pos.CENTER_LEFT);

        // Load and style the shop image
        VendorModel vendors = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.VENDOR), VendorModel.class,
                v -> v.getId().equals(order.getVendor())).get(0);

        ImageView shopImageView = loadShopImage(vendors.getShopImage());
        shopImageView.setFitHeight(50); // Adjust size as necessary
        shopImageView.setFitWidth(50);
        shopImageView.setPreserveRatio(true);

        // Convert Date to LocalDateTime
        LocalDateTime orderDateTime = Instant.ofEpochMilli(order.getTime().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        // Format LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm");
        String formattedDate = orderDateTime.format(formatter);

        // VBox for the text details next to the image
        VBox detailsVBox = new VBox(1);
        String shopName = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.VENDOR), VendorModel.class,
                vendor -> vendor.getId().equals(order.getVendor())).get(0).getShopName();
        Label shopNameLabel = new Label(shopName);
        Label orderTimeLabel = new Label(formattedDate);
        Label orderPriceLabel = new Label(String.format("RM%.2f", order.getTotalPrice()));
        Label orderStatusLabel = new Label("Status: " + order.getStatus().toString());
        applyStatusClass(orderStatusLabel, order.getStatus());

        // Adding styles from CSS
        shopNameLabel.getStyleClass().add("shop-name-label");
        orderTimeLabel.getStyleClass().add("order-time-label");
        orderPriceLabel.getStyleClass().add("order-price-label");
        orderStatusLabel.getStyleClass().add("order-status-label");

        // 'View Details' button placed directly below the status label
        HBox buttonContainer = new HBox(5);
        buttonContainer.getStyleClass().add("button-container");
        Button viewDetailsButton = new Button("View Details");
        viewDetailsButton.getStyleClass().add("view-details-button");

        viewDetailsButton.setOnAction(e -> {
            SessionUtil.setSelectedOrderInSession(order);
            // CustomerViewModel.getOrderViewModel().setOrderDetailsUI(new
            // OrderDetailsUI());
            // CustomerViewModel.getOrderViewModel().navigate(CustomerViewModel.getOrderViewModel().getOrderDetailsUI());
        });

        buttonContainer.getChildren().addAll(viewDetailsButton);

        // Assemble details in the VBox
        detailsVBox.getChildren().addAll(shopNameLabel, orderTimeLabel, orderStatusLabel, orderPriceLabel,
                buttonContainer);

        // Add the image and details to the content box
        contentBox.getChildren().addAll(shopImageView, detailsVBox);

        // Add the content box to the main container
        orderContainer.getChildren().add(contentBox);

        return new HBox(orderContainer); // Wrap in an HBox for alignment purposes in a larger list
    }

    private ImageView loadShopImage(String path) {
        ImageView imageView = Images.getImageView(path, 0, 0);
        return imageView;
    }

    private void applyStatusClass(Label label, StatusEnum status) {
        label.getStyleClass().add("order-status-label");

        switch (status) {
            case PENDING:
                label.getStyleClass().add("status-pending");
                break;
            case REJECTED:
                label.getStyleClass().add("status-rejected");
                break;
            case PREPARING:
            case WAITING_FOR_RIDER:
            case DELIVERING:
                label.getStyleClass().add("status-group2");
                break;
            case READY_FOR_PICKUP:
                label.getStyleClass().add("status-readyforpickup");
            case DELIVERED:
            case PICKED_UP:
            case SERVED:
                label.getStyleClass().add("status-group1");
                break;
            case CANCELLED:
                label.getStyleClass().add("status-cancelled");
                break;
        }
    }

}
