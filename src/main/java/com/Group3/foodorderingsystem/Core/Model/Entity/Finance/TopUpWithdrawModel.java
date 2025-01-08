package com.Group3.foodorderingsystem.Core.Model.Entity.Finance;

import java.io.ObjectInputFilter.Status;

import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TopUpWithdrawModel {

    private String TopUpWithdrawModelId;
    private String userId;
    private double amount;
    private TransactionType transactionType;
    private Status status; // UNDICIDED, APPROVED, REJECTED

    public TopUpWithdrawModel() {
    }

    public void setTopUpWithdrawModelId(String TopUpWithdrawModelId) {
        this.TopUpWithdrawModelId = TopUpWithdrawModelId;
    }

    public String getTopUpWithdrawModelId() {
        return TopUpWithdrawModelId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
