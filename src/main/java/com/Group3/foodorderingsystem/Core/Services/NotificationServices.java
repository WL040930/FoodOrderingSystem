package com.Group3.foodorderingsystem.Core.Services;

import java.util.ArrayList;
import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.NotificationModel;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;

public class NotificationServices {

    private static List<NotificationModel> getNotificationList() {
        return FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.NOTIFICATION), NotificationModel.class);
    }

    public static void createNewNotification(String userId, String message) {
        List<NotificationModel> notificationList = getNotificationList();
        NotificationModel notificationModel = new NotificationModel();
        notificationModel.setUserId(userId);
        notificationModel.setContent(message);
        notificationList.add(notificationModel);
        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.NOTIFICATION), notificationList);
    }

    public static List<NotificationModel> getPersonalNotification(String userId) {
        List<NotificationModel> notificationList = getNotificationList();
        List<NotificationModel> personalNotificationList = new ArrayList<>();

        for (NotificationModel notificationModel : notificationList) {
            if (notificationModel.getUserId().equals(userId)) {
                personalNotificationList.add(notificationModel);
            }
        }

        personalNotificationList.sort((n1, n2) -> n2.getDate().compareTo(n1.getDate()));
        return personalNotificationList;
    }

    public static class Template {

        public static String topUpRequest(Double amount) {
            return "Your top up request of <b>RM " + String.format("%.2f", amount) + "</b> is being processed.";
        }

        public static String withdrawRequest(Double amount) {
            return "Your withdraw request of <b>RM " + String.format("%.2f", amount) + "</b> is being processed.";
        }

    }
}
