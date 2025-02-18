package com.Group3.foodorderingsystem.Module.Platform.Customer.Order.ui;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;


import com.Group3.foodorderingsystem.Core.Model.Entity.Order.OrderModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;
import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Services.CustomerOrderServices;
import com.Group3.foodorderingsystem.Core.Services.VendorOrderServices;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.Node;


public class OrderHistoryUI extends VBox {

    private String selectedTab = "Active";
    CustomerModel customer = SessionUtil.getCustomerFromSession();

    private List<OrderModel> pendingOrders;
    private List<OrderModel> activeOrders;
    private List<OrderModel> pastOrders;

    
    public OrderHistoryUI(String selectedTab) {

        pendingOrders = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class, order -> order.getCustomer().equals(customer.getId()) && order.getStatus().equals(StatusEnum.PENDING));
        activeOrders = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class, order -> order.getCustomer().equals(customer.getId()) && (order.getStatus().equals(StatusEnum.PREPARING) || order.getStatus().equals(StatusEnum.READY_FOR_PICKUP) || order.getStatus().equals(StatusEnum.DELIVERING) || order.getStatus().equals(StatusEnum.WAITING_FOR_RIDER)));
        pastOrders = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class, order -> order.getCustomer().equals(customer.getId()) && (order.getStatus().equals(StatusEnum.DELIVERED) || order.getStatus().equals(StatusEnum.PICKED_UP) || order.getStatus().equals(StatusEnum.SERVED) || order.getStatus().equals(StatusEnum.CANCELLED) || order.getStatus().equals(StatusEnum.REJECTED)));
        this.selectedTab = selectedTab;
        initUI();
    }


    private void initUI() {
        Collections.sort(pendingOrders, Comparator.comparing(OrderModel::getTime).reversed());
        Collections.sort(activeOrders, Comparator.comparing(OrderModel::getTime).reversed());
        Collections.sort(pastOrders, Comparator.comparing(OrderModel::getTime).reversed());


        this.setStyle("-fx-background-color: #f8fafc;");
        this.setPadding(new Insets(10)); // Adjust padding as needed
        addTabs(this);

        //Load and apply css
        this.getStylesheets().add(getClass().getResource("/com/Group3/foodorderingsystem/Module/Platform/Customer/Order/ui/OrderHistoryUI.css").toExternalForm());
    }

    private void addTabs(VBox root) {
        TabPane tabPane = new TabPane();
        tabPane.setId("tabPane");
        Tab allTab = new Tab("Pending");
        Tab activeTab = new Tab("  Active");
        Tab pastTab = new Tab("   Past");
        allTab.setClosable(false);
        activeTab.setClosable(false);
        pastTab.setClosable(false);

        allTab.setContent(createOrdersContent(pendingOrders, "No pending orders"));
        activeTab.setContent(createOrdersContent(activeOrders, "No active orders"));
        pastTab.setContent(createOrdersContent(pastOrders, "No past orders"));

        tabPane.getTabs().addAll(allTab, activeTab, pastTab);

        if ("Active".equals(selectedTab)) {
            tabPane.getSelectionModel().select(activeTab);
        } else if ("Past".equals(selectedTab)) {
            tabPane.getSelectionModel().select(pastTab);
        } else if ("Pending".equals(selectedTab)) {
            tabPane.getSelectionModel().select(allTab);
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

        // HBox to hold the image and details side by side
        HBox contentBox = new HBox(10);
        contentBox.setAlignment(Pos.CENTER_LEFT);

        // Load and style the shop image
        VendorModel vendors = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.VENDOR), VendorModel.class, v -> v.getId().equals(order.getVendor())).get(0);

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
        String shopName = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.VENDOR), VendorModel.class, vendor -> vendor.getId().equals(order.getVendor())).get(0).getShopName();
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
            CustomerViewModel.getOrderViewModel().setOrderDetailsUI(new OrderDetailsUI());
            CustomerViewModel.getOrderViewModel().navigate(CustomerViewModel.getOrderViewModel().getOrderDetailsUI());
        });


        buttonContainer.getChildren().addAll(viewDetailsButton);

        // Assemble details in the VBox
        detailsVBox.getChildren().addAll(shopNameLabel, orderTimeLabel, orderStatusLabel, orderPriceLabel, buttonContainer);

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

    public OrderHistoryUI() {
        this("  Active");
        pendingOrders = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class, order -> order.getCustomer().equals(customer.getId()) && order.getStatus().equals(StatusEnum.PENDING));
        activeOrders = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class, order -> order.getCustomer().equals(customer.getId()) && (order.getStatus().equals(StatusEnum.PREPARING) || order.getStatus().equals(StatusEnum.READY_FOR_PICKUP) || order.getStatus().equals(StatusEnum.DELIVERING) || order.getStatus().equals(StatusEnum.WAITING_FOR_RIDER)));
        pastOrders = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class, order -> order.getCustomer().equals(customer.getId()) && (order.getStatus().equals(StatusEnum.DELIVERED) || order.getStatus().equals(StatusEnum.PICKED_UP) || order.getStatus().equals(StatusEnum.SERVED) || order.getStatus().equals(StatusEnum.CANCELLED) || order.getStatus().equals(StatusEnum.REJECTED)));

        initUI();
    }
}
