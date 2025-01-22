package com.Group3.foodorderingsystem.Core.Services;

import java.util.ArrayList;
import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel.TransactionType;
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

        public static String approveRequest(Double amount, TransactionType transactionType) {
            return "Your <b>" + transactionType + "</b> request of <b>RM " + String.format("%.2f", amount)
                    + "</b> has been approved.";
        }

        public static String rejectRequest(Double amount, TransactionType transactionType) {
            return "Your <b>" + transactionType + "</b> request of <b>RM " + String.format("%.2f", amount)
                    + "</b> has been rejected.";
        }

        public static String withdrawRequest(Double amount) {
            return "Your withdraw request of <b>RM " + String.format("%.2f", amount) + "</b> is being processed.";
        }

        public static String adminTopupRequest(String userId, Double amount, String requestId) {
            return "User <b>" + userId + "</b> has requested a top up of <b>RM " + String.format("%.2f", amount)
                    + "</b>.<br>Request ID: <b>" + requestId + "</b>";
        }

        public static String adminWithdrawRequest(String userId, Double amount) {
            return "User <b>" + userId + "</b> has requested a withdraw of <b>RM " + String.format("%.2f", amount)
                    + "</b>.";
        }

        public static String receiveCredit(Double amount, TransactionType transactionType) {
            return "You have received <b>RM " + String.format("%.2f", amount) + "</b> from a " + transactionType
                    + " transaction.";
        }

        public static String withdrawCredit(Double amount, TransactionType transactionType) {
            return "You have withdrawn <b>RM " + String.format("%.2f", amount) + "</b> from a " + transactionType
                    + " transaction.";
        }

        public static String transactionReject(Double amount, TransactionType transactionType) {
            return "Your <b>" + transactionType.name() + "</b> transaction of <b>RM " + String.format("%.2f", amount)
                    + "</b> has been rejected.";
        }

        public static String receiveOrderPayment(Double amount, String orderId) {
            return "You have received <b>RM " + String.format("%.2f", amount) + "</b> from order <b>" + orderId
                    + "</b>.";
        }

        public static String receiveDeliveryPayment(Double amount, String orderId) {
            return "You have received <b>RM " + String.format("%.2f", amount) + "</b> from delivery of order <b>"
                    + orderId + "</b>.";
        }

        public static String payOrder(Double amount, String orderId) {
            return "You have paid <b>RM " + String.format("%.2f", amount) + "</b> for order <b>" + orderId + "</b>.";
        }

    }
}
