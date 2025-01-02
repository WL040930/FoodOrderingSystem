package com.Group3.foodorderingsystem.Core.Model.Entity.Order;


import java.util.Date;
import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Enum.OrderMethodEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;

public class OrderModel {
    private String orderId;
    private String customer;
    private String vendor;
    private List<ItemModel> items;
    private double totalPrice;
    private String deliveryAddress;
    private StatusEnum status;
    private OrderMethodEnum orderMethod;
    private Date time;

    public OrderModel() {
        //get current time
        this.time = new Date();
    }

    // getters and setters
    public String getOrderId() {
        return orderId;
    }

    public String getCustomer() {
        return customer;
    }

    public String getVendor() {
        return vendor;
    }

    public List<ItemModel> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public OrderMethodEnum getOrderMethod() {
        return orderMethod;
    }

    public Date getTime() {
        return time;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setItems(List<ItemModel> items) {
        this.items = items;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public void setOrderMethod(OrderMethodEnum orderMethod) {
        this.orderMethod = orderMethod;
    }


    public void setTime(Date time) {
        this.time = time;
    }
}