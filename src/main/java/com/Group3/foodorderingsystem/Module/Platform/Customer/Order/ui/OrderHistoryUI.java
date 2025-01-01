package com.Group3.foodorderingsystem.Module.Platform.Customer.Order.ui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Order.OrderModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.CategoryEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.OrderMethodEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;
import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;


public class OrderHistoryUI extends VBox {

    private String selectedTab = "  Active";
    private List<OrderModel> pendingOrders;
    private List<OrderModel> activeOrders;
    private List<OrderModel> pastOrders;

    public OrderHistoryUI(String selectedTab) {
        this.selectedTab = selectedTab;
        initUI();
    }

    private void initUI() {
        this.setStyle("-fx-background-color: #f8fafc;");
        this.setPadding(new Insets(10)); // Adjust padding as needed
        initializeData();
        addTabs(this);

        //Load and apply css
        this.getStylesheets().add(getClass().getResource("/com/Group3/foodorderingsystem/Module/Platform/Customer/Order/ui/OrderHistoryUI.css").toExternalForm());
    }

    private void initializeData() {
        // Initialization of data here (for demonstration, using hardcoded values)
            pendingOrders = new ArrayList<>();
    
            VendorModel vendor1 = new VendorModel();
            vendor1.setShopImage("shop1.png");
            vendor1.setShopName("Shop 1");
    
            VendorModel vendor2 = new VendorModel();
            vendor2.setShopImage("shop2.png");
            vendor2.setShopName("Shop 2 Shop 2 Shop 2 Shop 2 Shop 2 Shop 2 Shop 2");
    
            VendorModel vendor3 = new VendorModel();
            vendor3.setShopImage("shop3.png");
            vendor3.setShopName("Shop 3");
    
            OrderModel order1 = new OrderModel();
            order1.setOrderId("f47ac10b");
            order1.setTime(LocalDateTime.parse("2024-12-14T10:00:00"));
            order1.setTotalPrice(2000.0);
            order1.setVendor(vendor1);
            order1.setStatus(StatusEnum.PENDING); // Set status
            order1.setOrderMethod(OrderMethodEnum.DELIVERY); // Set order method
            order1.setDeliveryAddress("123, Jalan ABC, 12345, Kuala Lumpur");

            List<ItemModel> items1 = new ArrayList<>();
            ItemModel item1 = new ItemModel();
            item1.setItemId("item1");
            item1.setItemName("Item 1 Item 1 Item 1 Item 1 Item 1 Item 1 Item 1 Item 1 Item 1 Item 1 Item 1 Item 1 Item 1 Item 1 Item 1 Item 1 Item 1 Item 1 Item 1 Item 1");
            item1.setItemDescription("Description 1");
            item1.setItemPrice(10.0);
            item1.setItemCategory(CategoryEnum.FOOD);
            item1.setItemImage("item1.png");
            item1.setItemQuantity(200);
            items1.add(item1);

            ItemModel item2 = new ItemModel();
            item2.setItemId("item2");
            item2.setItemName("Item 2");
            item2.setItemDescription("Description 2");
            item2.setItemPrice(5.0);
            item2.setItemCategory(CategoryEnum.FOOD);
            item2.setItemImage("item2.png");
            item2.setItemQuantity(1);
            items1.add(item2);
            items1.add(item2);
            items1.add(item2);

            order1.setItems(items1);

    
            OrderModel order2 = new OrderModel();
            order2.setOrderId("9b2e3c7d");
            order2.setTime(LocalDateTime.parse("2024-12-14T11:00:00"));
            order2.setTotalPrice(30.0);
            order2.setVendor(vendor2);
            order2.setStatus(StatusEnum.PENDING); // Set status
            order2.setOrderMethod(OrderMethodEnum.TAKEAWAY); // Set order method

            List<ItemModel> items2 = new ArrayList<>();
            ItemModel item3 = new ItemModel();
            item3.setItemId("item3");
            item3.setItemName("Item");
            item3.setItemDescription("Description 3");
            item3.setItemPrice(15.0);
            item3.setItemCategory(CategoryEnum.FOOD);
            item3.setItemImage("item3.png");
            item3.setItemQuantity(2);
            items2.add(item3);

            ItemModel item4 = new ItemModel();
            item4.setItemId("item4");
            item4.setItemName("Item 4");
            item4.setItemDescription("Description 4");
            item4.setItemPrice(10.0);
            item4.setItemCategory(CategoryEnum.FOOD);
            item4.setItemImage("item4.png");
            item4.setItemQuantity(1);
            items2.add(item4);

            order2.setItems(items2);
            
            //items in order2 to session
            SessionUtil.setItemsInSession(items2);

            OrderModel order3 = new OrderModel();
            order3.setOrderId("123e4567");
            order3.setTime(LocalDateTime.parse("2024-12-14T12:00:00"));
            order3.setTotalPrice(40.0);
            order3.setVendor(vendor3);
            order3.setStatus(StatusEnum.DELIVERED); // Set status
            order3.setOrderMethod(OrderMethodEnum.DINE_IN); // Set order method
            CustomerModel customer = new CustomerModel();
            customer.setName("John Doe");
            order3.setCustomer(customer);

            order3.setItems(items2);
            

            OrderModel order4 = order1;
    
            pendingOrders.add(order1);
            pendingOrders.add(order2);
            pendingOrders.add(order3);
            pendingOrders.add(order4);

            activeOrders = new ArrayList<>();
            activeOrders.add(order1);
            activeOrders.add(order3);

            pastOrders = new ArrayList<>();

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

        if ("  Active".equals(selectedTab)) {
            tabPane.getSelectionModel().select(activeTab);
        } else if ("   Past".equals(selectedTab)) {
            tabPane.getSelectionModel().select(pastTab);
        } else {
            tabPane.getSelectionModel().select(allTab);
        }

        root.getChildren().add(tabPane);
    }

    private ScrollPane createOrdersContent(List<OrderModel> orders, String emptyMessage) {
        VBox allOrders = new VBox(15);
        allOrders.setPadding(new Insets(30, 0, 0, 15));
        allOrders.setStyle("-fx-background-color: #f8fafc;");

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
        //TODO: add correct path for image
        ImageView shopImageView = loadShopImage("logo.png");
        shopImageView.setFitHeight(50); // Adjust size as necessary
        shopImageView.setFitWidth(50);
        shopImageView.setPreserveRatio(true);

        // VBox for the text details next to the image
        VBox detailsVBox = new VBox(1);
        Label shopNameLabel = new Label(order.getVendor().getShopName());
        Label orderTimeLabel = new Label(order.getTime().toString());
        Label orderPriceLabel = new Label(String.format("$%.2f", order.getTotalPrice()));
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

        // Add action to the button
        viewDetailsButton.setOnAction(e -> {
            SessionUtil.setSelectedOrderInSession(order);
            CustomerViewModel.navigate(CustomerViewModel.getOrderViewModel().getOrderDetailsUI());
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
            case READY_FOR_PICKUP:
            case DELIVERING:
                label.getStyleClass().add("status-group2");
                break;
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
    }
}
