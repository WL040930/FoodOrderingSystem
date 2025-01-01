package com.Group3.foodorderingsystem.Module.Platform.Customer.Order.model;

import com.Group3.foodorderingsystem.Module.Platform.Customer.Order.ui.OrderHistoryUI;
import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Order.ui.OrderDetailsUI;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Order.ui.OrderSummaryUI;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;

import javafx.scene.Node;

public class OrderViewModel extends ViewModelConfig{
    
    private OrderHistoryUI orderHistoryUI;
    private OrderDetailsUI orderDetailsUI;
    private OrderSummaryUI orderSummaryUI;

    public void navigate(Node node) {
        setNode(node);

        CustomerViewModel.navigate(node);
    }

    public void init() {
        orderHistoryUI = new OrderHistoryUI();

        setNode(orderHistoryUI);

    }

    public OrderViewModel() {
        orderHistoryUI = new OrderHistoryUI();
        
    }

    public OrderHistoryUI getOrderHistoryUI() {
        return orderHistoryUI;
    }

    public OrderHistoryUI getOrderHistoryUI(String tab) {
        orderHistoryUI = new OrderHistoryUI(tab);
        return orderHistoryUI;
    }

    public void setOrderHistoryUI(OrderHistoryUI orderHistoryUI) {
        this.orderHistoryUI = orderHistoryUI;
    }

    public OrderDetailsUI getOrderDetailsUI() {
        return this.orderDetailsUI;
    }

    public void setOrderDetailsUI(OrderDetailsUI orderDetailsUI) {
        this.orderDetailsUI = orderDetailsUI;
    }

    public OrderSummaryUI getOrderSummaryUI() {
        return this.orderSummaryUI;
    }

    public void setOrderSummaryUI(OrderSummaryUI orderSummaryUI) {
        this.orderSummaryUI = orderSummaryUI;
    }

}