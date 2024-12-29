package com.Group3.foodorderingsystem.Core.Model.Entity.Order;

import java.time.LocalDateTime;
import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Enum.OrderMethodEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;

public class OrderModel {
    private String orderId;
    private CustomerModel customer;
    private VendorModel vendor;
    private List<ItemModel> items;
    private double totalPrice;
    private String deliveryAddress;
    private StatusEnum status;
    private OrderMethodEnum orderMethod;
    private LocalDateTime time;

    public OrderModel() {
        this.time = LocalDateTime.now();
    }

    // getters and setters
    public String getOrderId() {
        return orderId;
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    public VendorModel getVendor() {
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

    public LocalDateTime getTime() {
        return time;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public void setVendor(VendorModel vendor) {
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


    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
