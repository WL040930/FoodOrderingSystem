package com.Group3.foodorderingsystem.Module.Common.Notification.model;

import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.NotificationModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Services.NotificationServices;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Module.Common.Notification.ui.NotificationUI;

import javafx.scene.Node;

public class NotificationViewModel extends ViewModelConfig {

    private User user;
    List<NotificationModel> notifications;

    public List<NotificationModel> getNotifications() {
        return notifications;
    }
    
    @Override
    public void navigate (Node node) {
        setNode(node);
    }

    public NotificationViewModel(String userId) {
        super();
        this.user = UserServices.findUserById(userId);

        this.notifications = NotificationServices.getPersonalNotification(userId);
    }

    public void init() {
        setNotificationUI(new NotificationUI(user));
        setNode(getNotificationUI());
    }

    private NotificationUI notificationUI;

    public NotificationUI getNotificationUI() {
        return notificationUI;
    }

    public void setNotificationUI(NotificationUI notificationUI) {
        this.notificationUI = notificationUI;
    }
}
