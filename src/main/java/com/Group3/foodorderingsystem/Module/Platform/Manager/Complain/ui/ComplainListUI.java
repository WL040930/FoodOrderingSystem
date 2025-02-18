package com.Group3.foodorderingsystem.Module.Platform.Manager.Complain.ui;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.OrderModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ComplainModel;
import com.Group3.foodorderingsystem.Core.Services.ManagerComplainServices;
import com.Group3.foodorderingsystem.Core.Services.VendorOrderServices;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;
import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Order.ui.OrderDetailsUI;
import com.Group3.foodorderingsystem.Module.Platform.Manager.ManagerViewModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.ComplainStatusEnum;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ComplainListUI extends VBox {

    List<OrderModel> pendingOrders;
    List<OrderModel> pastOrders;

    
    public ComplainListUI() {
        pendingOrders = ManagerComplainServices.getOrders(ComplainStatusEnum.PENDING);
        pastOrders = ManagerComplainServices.getOrders(ComplainStatusEnum.RESOLVED);
    
        initUI();
    }

    private void initUI() {
        this.setStyle("-fx-background-color: #f8fafc;");
        this.setPadding(new Insets(10)); // Adjust padding as needed
        addTabs(this);

        //Load and apply css
        this.getStylesheets().add(getClass().getResource("/com/Group3/foodorderingsystem/Module/Platform/Manager/Complain/ui/ComplainListUI.css").toExternalForm());
    }


    private void addTabs(VBox root) {
        TabPane tabPane = new TabPane();
        tabPane.setId("tabPane");
        Tab pendingTab = new Tab("Pending");
        Tab pastTab = new Tab("Resolved");
        pendingTab.setClosable(false);
        pastTab.setClosable(false);

        pendingTab.setContent(createOrdersContent(pendingOrders, "No pending orders"));
        pastTab.setContent(createOrdersContent(pastOrders, "No past orders"));

        tabPane.getTabs().addAll(pendingTab, pastTab);

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
        CustomerModel customer = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.CUSTOMER), CustomerModel.class, c -> c.getId().equals(order.getCustomer())).get(0);
        ComplainModel complain = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.COMPLAIN), ComplainModel.class, c -> c.getOrderId().equals(order.getOrderId())).get(0);

        VBox orderContainer = new VBox(5); // Main container for each order
        orderContainer.setPadding(new Insets(10));
        orderContainer.getStyleClass().add("order-container");

        // HBox to hold the image and details side by side
        HBox contentBox = new HBox(10);
        contentBox.setAlignment(Pos.CENTER_LEFT);

        //if the tab is pending, show the complain_pending.png icon
        ImageView iconImage;
        if (complain.getComplainStatus() == ComplainStatusEnum.PENDING) {
            iconImage = Images.getImageView("complain_pending.png", 0, 0);
        } else {
            iconImage = Images.getImageView("available.png", 0, 0);
        }
        
        
        iconImage.setFitHeight(50); 
        iconImage.setFitWidth(50);
        iconImage.setPreserveRatio(true);


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
        Label customerNameLabel = new Label(customer.getName());

        // Adding styles from CSS
        shopNameLabel.getStyleClass().add("shop-name-label");
        orderTimeLabel.getStyleClass().add("order-time-label");
        customerNameLabel.getStyleClass().add("order-price-label");

        // 'View Details' button placed directly below the status label
        HBox buttonContainer = new HBox(5);
        buttonContainer.getStyleClass().add("button-container");
        Button viewDetailsButton = new Button("View Details");
        viewDetailsButton.getStyleClass().add("view-details-button");


        viewDetailsButton.setOnAction(e -> {
            // TODO: logic to navigate to order details page
            SessionUtil.setSelectedOrderInSession(order);
            ManagerViewModel.getComplainViewModel().setComplainDetailsUI(new ComplainDetailsUI());
            ManagerViewModel.getComplainViewModel().navigate(ManagerViewModel.getComplainViewModel().getComplainDetailsUI());
        });


        buttonContainer.getChildren().addAll(viewDetailsButton);

        // Assemble details in the VBox
        detailsVBox.getChildren().addAll(shopNameLabel, orderTimeLabel, customerNameLabel, buttonContainer);

        // Add the image and details to the content box
        contentBox.getChildren().addAll(iconImage, detailsVBox);

        // Add the content box to the main container
        orderContainer.getChildren().add(contentBox);

        return new HBox(orderContainer); // Wrap in an HBox for alignment purposes in a larger list
    }
}
