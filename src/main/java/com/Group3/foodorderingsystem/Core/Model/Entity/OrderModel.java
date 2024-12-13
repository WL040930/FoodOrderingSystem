package com.Group3.foodorderingsystem.Core.Model.Entity;

import java.util.List;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;

public class OrderModel {
    private String orderId;
    private CustomerModel customer;
    private ShopModel shopModel; 
    private List<ItemModel> items;
    private double totalPrice;
    private StatusEnum status;

    public OrderModel() {
    }

    // getters and setters
    public String getOrderId() {
        return orderId;
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    public List<ItemModel> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public void setItems(List<ItemModel> items) {
        this.items = items;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

}
