package com.Group3.foodorderingsystem.Core.Model.Entity.User;

import java.util.Date;

import com.Group3.foodorderingsystem.Core.Storage.Storage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationModel {
    
    private String notificationId; 
    private String userId; 
    private String content; 
    private Date date;

    public NotificationModel() {
        this.date = new Date();
        this.notificationId = Storage.generateNewId(); 
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
    
}
