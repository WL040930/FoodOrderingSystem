package com.Group3.foodorderingsystem.Core.Model.Entity.Order;


import java.util.Date;
import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Enum.OrderMethodEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;

public class OrderModel {
    private String orderId;
    private String customer;
    private String vendor;
    private String rider;
    private List<ItemModel> items;
    private double totalPrice;
    private String deliveryAddress;
    private StatusEnum status;
    private OrderMethodEnum orderMethod;
    private Date time;
    private int rating;
    private String review;

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

    public String getRider() {
        return rider;
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

    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
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

    public void setRider(String rider) {
        this.rider = rider;
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

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReview(String review) {
        this.review = review;
    }
}