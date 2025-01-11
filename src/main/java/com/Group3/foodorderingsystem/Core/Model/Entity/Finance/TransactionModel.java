package com.Group3.foodorderingsystem.Core.Model.Entity.Finance;

import java.util.Date;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.OrderModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionModel {

    private String transactionId;
    private Double amount;
    private TransactionType transactionType;
    private Date transactionDate;

    // For payment transaction
    // For refund transaction
    private OrderModel orderModel;

    // For topup transaction
    // For Withdrawal transaction
    private User user;

    public enum TransactionType {
        PAYMENT,
        TOPUP,
        REFUND,
        WITHDRAWAL,
    }

    public TransactionModel() {
        this.transactionDate = new Date(); 
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
    }

    public OrderModel getOrderModel() {
        return orderModel;
    }

    public void setUserModel(User userModel) {
        this.user = userModel;
    }

    public User getUserModel() {
        return user;
    }
}
