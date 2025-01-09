package com.Group3.foodorderingsystem.Core.Services;

import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.NotificationModel;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;

public class NotificationServices {
    
    private static List<NotificationModel> getNotificationList() {
        return  FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.NOTIFICATION), NotificationModel.class);
    }

    public static void createNewNotification(String userId, String message) {
        List<NotificationModel> notificationList = getNotificationList();
        NotificationModel notificationModel = new NotificationModel();
        notificationModel.setUserId(userId);
        notificationModel.setContent(message);
        notificationList.add(notificationModel);
        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.NOTIFICATION), notificationList);
    }

    public static class Template {
            
            public static String topUpRequest(Double amount) {
                return "<html>Your top up request of <b>RM " + amount + "</b> is being processed.</html>";
            }

            public static String withdrawRequest(Double amount) {
                return "<html>Your withdraw request of <b>RM " + amount + "</b> is being processed.</html>";
            }
            
        }
}
