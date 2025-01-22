package com.Group3.foodorderingsystem.Module.Platform.Runner.Home.ui;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.management.Notification;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Order.OrderModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.RunnerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.RunnerStatusEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;
import com.Group3.foodorderingsystem.Core.Services.NotificationServices;
import com.Group3.foodorderingsystem.Core.Services.RunnerOrderServices;
import com.Group3.foodorderingsystem.Core.Services.TransactionServices;
import com.Group3.foodorderingsystem.Core.Services.VendorOrderServices;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;
import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Module.Platform.Runner.RunnerViewModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;
import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class RunnerHomeUI extends VBox {

    RunnerModel runner;
    List<OrderModel> orders = RunnerOrderServices.listPastOrders();
    
    public RunnerHomeUI() {
        runner = (RunnerModel) SessionUtil.getRiderFromSession();

        initUI();
    }

    private void initUI() {

        this.setStyle("-fx-background-color: #f8fafc;");
        this.setPadding(new Insets(10)); // Adjust padding as needed
        addTabs(this);

        this.getStylesheets().add(getClass().getResource("/com/Group3/foodorderingsystem/Module/Platform/Runner/Home/ui/RunnerHomeUI.css").toExternalForm());
    }


    private void addTabs(VBox root) {
        TabPane tabPane = new TabPane();
        tabPane.setId("tabPane");
        Tab currentTab = new Tab("Current");
        Tab pastTab = new Tab("   Past");
        currentTab.setClosable(false);
        pastTab.setClosable(false);

        currentTab.setContent(createCurrentOrdersContent());
        pastTab.setContent(createOrdersContent(orders, "No past orders"));


        tabPane.getTabs().addAll(currentTab, pastTab);

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab == pastTab) {
                refreshPastOrdersContent(pastTab);
            }
        });


        root.getChildren().add(tabPane);
    }

    private void refreshPastOrdersContent(Tab pastTab) {
        // Refresh the orders list
        orders = RunnerOrderServices.listPastOrders();
        // Update the content of the past tab
        pastTab.setContent(createOrdersContent(orders, "No past orders"));
    }

    private Node createCurrentOrdersContent() {
        VBox contentBox = new VBox(10);
        contentBox.getStyleClass().add("content-box");
        updateCurrentOrdersContent(contentBox);
        return contentBox;
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
        Label orderPriceLabel = new Label(String.format("$%.2f", order.getDeliveryFee()));
        Label orderStatusLabel = new Label("Status: " + order.getStatus().toString());

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
            RunnerViewModel.getHomeViewModel().setRunnerViewDetailsUI(new RunnerViewDetailsUI());
            RunnerViewModel.getHomeViewModel().navigate(RunnerViewModel.getHomeViewModel().getRunnerViewDetailsUI());
            
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


    VBox availabilityBox = new VBox(10); 

    private Node updateCurrentOrdersContent(VBox contentBox) {

        contentBox.getChildren().clear();
        runner = (RunnerModel) SessionUtil.getRiderFromSession();
        

        if (runner.getStatus() == RunnerStatusEnum.UNAVAILABLE || runner.getStatus() == RunnerStatusEnum.AVAILABLE) {
            contentBox.getChildren().add(createAvailabilityToggleButtons());
            contentBox.getChildren().add(availabilityBox);
            updateAvailabilityContent(runner.getStatus(), availabilityBox); 
        } else if (runner.getStatus() == RunnerStatusEnum.ASSIGNING) {
            contentBox.getChildren().add(createAssignedContentBox());
            
        } else if (runner.getStatus() == RunnerStatusEnum.DELIVERING) {
            contentBox.getChildren().add(createDeliveringContentBox());
        }
        
        return contentBox;
    }

    private void buttonAction(OrderModel selectedOrder, String Reply) {

        String message = null;

        if (Reply.equals("Reject")) {
            message = "Are you sure you want to reject this order?";
        } else if (Reply.equals("Accept")) {
            message = "Are you sure you want to accept this order?";
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                if (Reply.equals("Reject")) {
                    RunnerOrderServices.updateRunnerStatus(RunnerStatusEnum.AVAILABLE);
                    VendorOrderServices.assignOrderToRunner(selectedOrder.getOrderId());
                    refreshContent();
                } else if (Reply.equals("Accept")) {
                    RunnerOrderServices.updateRunnerStatus(RunnerStatusEnum.DELIVERING);
                    RunnerOrderServices.saveRiderToOrder(selectedOrder);

                    //create transaction to the runner
                    TransactionServices.createTransaction(selectedOrder.getOrderId(), TransactionModel.TransactionType.PAYMENT, RoleEnum.RUNNER);
                    refreshContent();
                    RunnerViewModel.initNotificationViewModel();
                    RunnerViewModel.initTransactionViewModel();
                }
            }
            
        });
            
    }


    private HBox createAvailabilityToggleButtons() {
        HBox availableButtonsBox = new HBox(10);
        Label availabilityLabel = new Label("Availability: ");
        ToggleButton availableButton = new ToggleButton("Available");
        ToggleButton unavailableButton = new ToggleButton("Unavailable");

        VBox.setMargin(availableButtonsBox, new Insets(20, 0, 0, 0));
        availableButtonsBox.getStyleClass().add("availability-buttons-box");
        availabilityLabel.getStyleClass().add("availability-label");
        availableButton.getStyleClass().add("availability-box");
        availableButton.getStyleClass().add("available-button");
        unavailableButton.getStyleClass().add("unavailable-button");

        ToggleGroup toggleGroup = new ToggleGroup();
        availableButton.setToggleGroup(toggleGroup);
        unavailableButton.setToggleGroup(toggleGroup);

        if (runner.getStatus() == RunnerStatusEnum.AVAILABLE) {
            availableButton.setSelected(true);
        } else if (runner.getStatus() == RunnerStatusEnum.UNAVAILABLE) {
            unavailableButton.setSelected(true);
        }

        availableButton.setOnAction(e -> {
            RunnerOrderServices.updateRunnerStatus(RunnerStatusEnum.AVAILABLE);
            updateAvailabilityContent(RunnerStatusEnum.AVAILABLE, availabilityBox);
        });
        unavailableButton.setOnAction(e -> {
            RunnerOrderServices.updateRunnerStatus(RunnerStatusEnum.UNAVAILABLE);
            updateAvailabilityContent(RunnerStatusEnum.UNAVAILABLE, availabilityBox);
        });

        availableButtonsBox.getChildren().addAll(availabilityLabel, availableButton, unavailableButton);
        return availableButtonsBox;
    }

    private void updateAvailabilityContent(RunnerStatusEnum status, VBox availabilityBox) {
        availabilityBox.getChildren().clear(); // Clear previous content
        availabilityBox.setAlignment(Pos.CENTER);
        VBox.setMargin(availabilityBox, new Insets(50, 0, 0, 0));

        ImageView availableIcon = new ImageView();
        Label availableInfo = new Label();
        
        if (status == RunnerStatusEnum.AVAILABLE) {
            availableIcon = Images.getImageView("available.png", 200, 200);
            availableInfo = new Label("You are now available to accept orders.");

        } else if (status == RunnerStatusEnum.UNAVAILABLE) {
            availableIcon = Images.getImageView("unavailable.png", 200, 200);           
            availableInfo = new Label("You are not available to take any orders.");
        }
        availableInfo.getStyleClass().add("status-info");

        availabilityBox.getChildren().addAll(availableIcon, availableInfo);
    }

    private VBox createAssignedContentBox() {
        OrderModel assignedOrder = RunnerOrderServices.getAssignedOrder();

        VBox assignedContentBox = new VBox(10);

        // Button container
        VBox buttonContainer = new VBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        VBox.setMargin(buttonContainer, new Insets(50, 0, 0, 0));

        ImageView imageView = Images.getImageView("pending_order.png", 200, 200);
        

        Button rejectOrderButton = new Button("Reject");
        rejectOrderButton.getStyleClass().add("reject-order-button");
        rejectOrderButton.setOnAction(e -> buttonAction(assignedOrder, "Reject"));

        Button acceptOrdButton = new Button("Accept");
        acceptOrdButton.getStyleClass().add("accept-order-button");
        acceptOrdButton.setOnAction(e -> buttonAction(assignedOrder, "Accept"));

        buttonContainer.getChildren().addAll(imageView, rejectOrderButton, acceptOrdButton);


        assignedContentBox.getChildren().addAll(buttonContainer, createOrderDetail(assignedOrder));

        return assignedContentBox;
    }

    private VBox createDeliveringContentBox() {
        OrderModel assignedOrder = RunnerOrderServices.getAssignedOrder();

        VBox assignedContentBox = new VBox(10);

        // Button container
        VBox buttonContainer = new VBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        VBox.setMargin(buttonContainer, new Insets(50, 0, 0, 0));

        ImageView imageView = Images.getImageView("food_delivering.png", 200, 200);
        buttonContainer.getChildren().addAll(imageView);

        Label message = new Label("");
        message.getStyleClass().add("detail-title-label");
        Button doneButton = new Button();
        doneButton.getStyleClass().add("press-button");


        if (assignedOrder.getStatus() == StatusEnum.WAITING_FOR_RIDER) {
            doneButton.setText("Pick Up Order"); 
            message.setText("Order accepted, awaiting food readiness.");
            doneButton.setOnAction(e -> {
                //confirmation message
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you ready to pick up the order?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        VendorOrderServices.updateOrderStatus(assignedOrder, StatusEnum.DELIVERING);
                        refreshContent();
                    }
                });
            });
            buttonContainer.getChildren().addAll(message, doneButton);
        } else if (assignedOrder.getStatus() == StatusEnum.DELIVERING) {
            doneButton.setText("Confirm Delivery"); 
            message.setText("Delivering order to customer.");
            doneButton.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Have you delivered the order?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        VendorOrderServices.updateOrderStatus(assignedOrder, StatusEnum.DELIVERED);
                        RunnerOrderServices.updateRunnerStatus(RunnerStatusEnum.AVAILABLE);
                        refreshContent();
                    }

                    // Send notification to customer, vendor and runner
                    NotificationServices.createNewNotification(assignedOrder.getCustomer(), NotificationServices.Template.orderCompletedCustomer(assignedOrder.getOrderId()));
                    NotificationServices.createNewNotification(assignedOrder.getVendor(), NotificationServices.Template.orderCompletedVendor(assignedOrder.getOrderId()));
                    NotificationServices.createNewNotification(runner.getId(), NotificationServices.Template.orderCompletedRunner(assignedOrder.getOrderId()));

                    // Refresh notification view model
                    RunnerViewModel.initNotificationViewModel();
                    
                });
            });
            buttonContainer.getChildren().addAll(message, doneButton);

        } else if (assignedOrder.getStatus() == StatusEnum.PREPARING) {
            message.setText("Order accepted, vendor is preparing the order.");
            buttonContainer.getChildren().addAll(message);
        }


        

        assignedContentBox.getChildren().addAll(buttonContainer, createOrderDetail(assignedOrder));

        return assignedContentBox;
    }


    private VBox createOrderDetail(OrderModel assignedOrder) {

        VBox orderContentBox = new VBox(10);

        //order detail
        VBox orderDetailBox = new VBox(10);
        Separator separator1 = new Separator();
        separator1.getStyleClass().add("separator");
        Separator separator2 = new Separator();
        separator1.getStyleClass().add("separator");

        //delivery fee
        VBox totalBox = new VBox(10);
        totalBox.setAlignment(Pos.CENTER);
        totalBox.setPadding(new Insets(0, 20, 0, 0));
        Label totelLabel = new Label("Delivery Fee:");
        totelLabel.getStyleClass().add("total-price-label");
        Label total = new Label("RM" + String.format("%.2f", assignedOrder.getDeliveryFee()));
        total.getStyleClass().add("total-price-amount-label");
        totalBox.getChildren().addAll(separator1, totelLabel, total, separator2);

        //delivery address
        HBox deliveryAddressBox = new HBox(10);
        Label deliveryAddressLabel = new Label("Delivery Address:");
        deliveryAddressLabel.getStyleClass().add("status-label");
        Label deliveryAddressValueLabel = new Label(assignedOrder.getDeliveryAddress());
        deliveryAddressValueLabel.getStyleClass().add("status-value-label");
        deliveryAddressBox.getChildren().addAll(deliveryAddressLabel, deliveryAddressValueLabel);

        //Order detail title
        VBox orderBox = new VBox(10);
        orderBox.setPadding(new Insets(10, 0, 0, 0));
        Label detailsLabel = new Label("Order Details");
        detailsLabel.getStyleClass().add("detail-title-label");

        //order id
        HBox orderIdBox = new HBox(10);
        Label orderIdLabel = new Label("Order ID:");
        orderIdLabel.getStyleClass().add("status-label");
        Label orderIdValueLabel = new Label(assignedOrder.getOrderId());
        orderIdValueLabel.getStyleClass().add("status-value-label");
        orderIdBox.getChildren().addAll(orderIdLabel, orderIdValueLabel);

        //shop name
        HBox shopNameBox = new HBox(10);
        Label shopNameLabel = new Label("Shop name:");
        shopNameLabel.getStyleClass().add("status-label");
        String shopName = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.VENDOR), VendorModel.class, vendor -> vendor.getId().equals(assignedOrder.getVendor())).get(0).getShopName();
        Label shopNameValueLabel = new Label(shopName);
        shopNameValueLabel.getStyleClass().add("status-value-label");
        shopNameBox.getChildren().addAll(shopNameLabel, shopNameValueLabel);

        //areas
        HBox areasBox = new HBox(10);
        Label areasLabel = new Label("Areas:");
        areasLabel.getStyleClass().add("status-label");
        Label areasValueLabel = new Label(assignedOrder.getArea());
        areasValueLabel.getStyleClass().add("status-value-label");
        areasBox.getChildren().addAll(areasLabel, areasValueLabel);

        orderDetailBox.getChildren().addAll(totalBox, detailsLabel, deliveryAddressBox, areasBox, orderIdBox, shopNameBox);
        

        //item list
        VBox itemsBox = new VBox(10);
        itemsBox.setPadding(new Insets(10, 0, 0, 0));
        Separator separator3 = new Separator();
        separator3.getStyleClass().add("separator");

        Label itemsLabel = new Label("Items Details");
        itemsLabel.getStyleClass().add("detail-title-label");

        itemsBox.getChildren().addAll(separator3, itemsLabel);

        for (ItemModel item : assignedOrder.getItems()) {
            HBox itemBox = new HBox(10);
            itemBox.setPadding(new Insets(5, 0, 5, 0));
            itemBox.setPrefWidth(450);
            itemBox.setMaxWidth(450);

            Label itemQuantityLabel = new Label(item.getItemQuantity() + "x");
            Label itemLabel = new Label(item.getItemName());
            itemQuantityLabel.getStyleClass().add("item-quantity-label");
            itemLabel.getStyleClass().add("item-label");
            HBox.setHgrow(itemLabel, Priority.ALWAYS);

            Label priceLabel = new Label("RM" + String.format("%.2f", item.getItemPrice() * item.getItemQuantity()));
            priceLabel.getStyleClass().add("price-label");

            itemBox.getChildren().addAll(itemQuantityLabel, itemLabel, priceLabel);
            itemsBox.getChildren().addAll(itemBox);
        }


        // Customer details
        VBox customerBox = new VBox(10);
        customerBox.setPadding(new Insets(10, 0, 0, 0));

        Separator separator4 = new Separator();
        separator4.getStyleClass().add("separator");

        //customer title
        CustomerModel customer = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.CUSTOMER), CustomerModel.class, c -> c.getId().equals(assignedOrder.getCustomer())).get(0);

        Label customerLabel = new Label("Customer Details");
        customerLabel.getStyleClass().add("detail-title-label");

        //customer name
        HBox customerNamesBox = new HBox(10);
        Label customerNameLabel = new Label("Customer Name:");
        customerNameLabel.getStyleClass().add("status-label");
        Label customerNameValueLabel = new Label(customer.getName());
        customerNameValueLabel.getStyleClass().add("status-value-label");
        customerNamesBox.getChildren().addAll(customerNameLabel, customerNameValueLabel);

        //customer phone
        HBox customerPhoneBox = new HBox(10);
        Label customerPhoneLabel = new Label("Customer Phone:");
        customerPhoneLabel.getStyleClass().add("status-label");
        Label customerPhoneValueLabel = new Label(customer.getPhoneNumber());
        customerPhoneValueLabel.getStyleClass().add("status-value-label");
        customerPhoneBox.getChildren().addAll(customerPhoneLabel, customerPhoneValueLabel);

        customerBox.getChildren().addAll(separator4, customerLabel, customerNamesBox, customerPhoneBox);

        

        orderContentBox.getChildren().addAll(orderDetailBox, itemsBox, customerBox);

        return orderContentBox;
    }

    private void refreshContent() {
        VBox contentBox = (VBox) this.lookup(".content-box");
        if (contentBox != null) {
            updateCurrentOrdersContent(contentBox);
        }
    }

    
}
