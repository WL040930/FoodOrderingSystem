package com.Group3.foodorderingsystem.Core.Model.Entity.Order;

import com.Group3.foodorderingsystem.Core.Model.Enum.ComplainStatusEnum;

public class ComplainModel {
    
    private String complainId;
    private String orderId;
    private String complainDescription;
    private ComplainStatusEnum complainStatus;
    private String complainReply;

    public ComplainModel() {
    }

    public String getComplainId() {
        return complainId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getComplainDescription() {
        return complainDescription;
    }

    public ComplainStatusEnum getComplainStatus() {
        return complainStatus;
    }

    public String getComplainReply() {
        return complainReply;
    }

    public void setComplainId(String complainId) {
        this.complainId = complainId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setComplainDescription(String complainDescription) {
        this.complainDescription = complainDescription;
    }

    public void setComplainStatus(ComplainStatusEnum complainStatus) {
        this.complainStatus = complainStatus;
    }

    public void setComplainReply(String complainReply) {
        this.complainReply = complainReply;
    }
}

