package com.Group3.foodorderingsystem.Module.Platform.Customer.Order.model;

import com.Group3.foodorderingsystem.Module.Platform.Customer.Order.ui.OrderHistoryUI;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Order.ui.OrderDetailsUI;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Order.ui.OrderSummaryUI;

public class OrderViewModel {
    
    private OrderHistoryUI orderHistoryUI;

    public OrderViewModel() {
        orderHistoryUI = new OrderHistoryUI();
        
    }

    public OrderHistoryUI getOrderHistoryUI() {
        return orderHistoryUI;
    }

    public void setOrderHistoryUI(OrderHistoryUI orderHistoryUI) {
        this.orderHistoryUI = orderHistoryUI;
    }

    public OrderDetailsUI getOrderDetailsUI() {
        return new OrderDetailsUI();
    }

    public OrderSummaryUI getOrderSummaryUI() {
        return new OrderSummaryUI();
    }
}