package com.Group3.foodorderingsystem.Module.Platform.Vendor.Home.ui;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.OrderModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;
import com.Group3.foodorderingsystem.Core.Services.CustomerOrderServices;
import com.Group3.foodorderingsystem.Core.Services.VendorOrderServices;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VendorOrderListUI extends VBox{
    
    private String selectedTab;

    private List<OrderModel> pendingOrders = VendorOrderServices.getOrderList("Pending");
    private List<OrderModel> activeOrders = VendorOrderServices.getOrderList("Active");
    private List<OrderModel> preparedOrders = VendorOrderServices.getOrderList("Prepared");
    private List<OrderModel> pastOrders = VendorOrderServices.getOrderList("Past");

    public VendorOrderListUI(String selectedTab) {
        this.selectedTab = selectedTab;
        initUI();
    }

    public VendorOrderListUI() {
        this("Pending");
    }

    private void initUI() {

        this.setStyle("-fx-background-color: #f8fafc;");
        this.setPadding(new Insets(10)); // Adjust padding as needed
        addTabs(this);

        this.getStylesheets().add(getClass().getResource("/com/Group3/foodorderingsystem/Module/Platform/Vendor/Home/ui/VendorOrderListUI.css").toExternalForm());
    }

    private void addTabs(VBox root) {
        TabPane tabPane = new TabPane();
        tabPane.setId("tabPane");
        Tab pendingTab = new Tab("Pending");
        Tab activeTab = new Tab("  Active");
        Tab preparedTab = new Tab("Prepared");
        Tab pastTab = new Tab("   Past");
        pendingTab.setClosable(false);
        activeTab.setClosable(false);
        pastTab.setClosable(false);
        preparedTab.setClosable(false);

        pendingTab.setContent(createOrdersContent(pendingOrders, "No pending orders"));
        activeTab.setContent(createOrdersContent(activeOrders, "No active orders"));
        preparedTab.setContent(createOrdersContent(preparedOrders, "No prepared orders"));
        pastTab.setContent(createOrdersContent(pastOrders, "No past orders"));


        tabPane.getTabs().addAll(pendingTab, activeTab, preparedTab, pastTab);


        if ("Active".equals(selectedTab)) {
            tabPane.getSelectionModel().select(activeTab);
        } else if ("Past".equals(selectedTab)) {
            tabPane.getSelectionModel().select(pastTab);
        } else if ("Pending".equals(selectedTab)) {
            tabPane.getSelectionModel().select(pendingTab);
        } else if ("Prepared".equals(selectedTab)) {
            tabPane.getSelectionModel().select(preparedTab);
        }

        root.getChildren().add(tabPane);
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
                HBox orderBox = createOrderBox(order);
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
                    HBox orderBox = createOrderBox(order);
                    allOrders.getChildren().add(orderBox);
                }
            }
        });
    
        return scrollPane;
    }

    private HBox createOrderBox(OrderModel order) {
        VBox orderContainer = new VBox(5); // Main container for each order
        orderContainer.setPadding(new Insets(10));
        orderContainer.getStyleClass().add("order-container");

        // Order ID at the top of each order box
        Label orderIdLabel = new Label("Order ID: " + order.getOrderId());
        orderIdLabel.getStyleClass().add("order-id-label");
        orderContainer.getChildren().add(orderIdLabel);

        // HBox to hold the details side by side
        HBox contentBox = new HBox(10);
        contentBox.setAlignment(Pos.CENTER_LEFT);

        // Convert Date to LocalDateTime
        LocalDateTime orderDateTime = Instant.ofEpochMilli(order.getTime().getTime())
                                     .atZone(ZoneId.systemDefault())
                                     .toLocalDateTime();

        // Format LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm");
        String formattedDate = orderDateTime.format(formatter);

        // get total quantity of items in the order
        int totalQuantity = order.getItems().stream().mapToInt(item -> item.getItemQuantity()).sum();


        // VBox for the text details 
        VBox detailsVBox = new VBox(1);
        Label orderPriceLabel = new Label(String.format("RM%.2f", order.getTotalPrice()));
        Label orderItemQuantity = new Label("Number of items: " + totalQuantity);

        orderPriceLabel.getStyleClass().add("order-price-label");
        orderItemQuantity.getStyleClass().add("item-quantity-label");

        //VBox for the time and status
        VBox timeStatusVBox = new VBox(1);
        timeStatusVBox.getStyleClass().add("time-status-vbox");
        Label orderTimeLabel = new Label(formattedDate);
        Label orderMethodLabel = new Label("Method: " + order.getOrderMethod());
        Label orderStatusLabel = new Label("Status: " + order.getStatus().toString());
        applyStatusClass(orderStatusLabel, order.getStatus());


        // Apply styles to the labels
        orderTimeLabel.getStyleClass().add("order-time-label");
        orderMethodLabel.getStyleClass().add("order-status-label");
        orderStatusLabel.getStyleClass().add("order-status-label");

        timeStatusVBox.getChildren().addAll(orderMethodLabel, orderStatusLabel);

        // 'View Details' button placed directly below the status label
        HBox buttonContainer = new HBox(1);
        buttonContainer.getStyleClass().add("button-container");


        Button viewDetailsButton = new Button("View Details");
        viewDetailsButton.getStyleClass().add("view-details-button");
        viewDetailsButton.setOnAction(e -> {
            SessionUtil.setSelectedOrderInSession(order);
            //navigate to order details page
            VendorViewModel.getHomeViewModel().setVendorOrderDetailsUI(new VendorOrderDetailsUI());
            VendorViewModel.getHomeViewModel().navigate(VendorViewModel.getHomeViewModel().getVendorOrderDetailsUI());
        });


        buttonContainer.getChildren().addAll(timeStatusVBox, viewDetailsButton);

        // Assemble details in the VBox
        detailsVBox.getChildren().addAll(orderPriceLabel, orderItemQuantity, orderTimeLabel, buttonContainer);

        // Add the details to the content box
        contentBox.getChildren().addAll(detailsVBox);

        // Add the content box to the main container
        orderContainer.getChildren().add(contentBox);

        return new HBox(orderContainer); // Wrap in an HBox for alignment purposes in a larger list
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
            case DELIVERING:
            case WAITING_FOR_RIDER:
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
