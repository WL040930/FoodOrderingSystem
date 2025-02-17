package com.Group3.foodorderingsystem.Core.Services;

import java.util.ArrayList;
import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel.TransactionType;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
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

    public static void generateReceipt(TransactionModel transactionModel, CustomerModel customerModel) {
        List<NotificationModel> notificationList = getNotificationList();
        NotificationModel notificationModel = new NotificationModel();
        notificationModel.setUserId(customerModel.getId());
        notificationModel.setContent(Template.sendReceipt(transactionModel.getTransactionId()));
        notificationModel.setTransactionId(transactionModel.getTransactionId());
        notificationList.add(notificationModel);
        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.NOTIFICATION), notificationList);
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

        public static String orderAcceptedCustomer(String orderId) {
            return "Your order <b>" + orderId + "</b> has been accepted.";
        }

        public static String orderRejectedCustomer(String orderId) {
            return "Your order <b>" + orderId + "</b> has been rejected. We have refunded the amount to your account.";
        }

        public static String orderReadyPickUpCustomer(String orderId) {
            return "Your order <b>" + orderId + "</b> is ready for pick up.";
        }

        public static String orderDeliveringCustomer(String orderId) {
            return "Your order <b>" + orderId + "</b> is on the way.";
        }

        public static String orderCompletedCustomer(String orderId) {
            return "Your order <b>" + orderId + "</b> has been completed.";
        }

        public static String orderCancelledCustomer(String orderId) {
            return "Your order <b>" + orderId + "</b> has been cancelled.";
        }

        public static String orderPlacedVendor() {
            return "You have a new order.";
        }

        public static String orderRunnerPickUpVendor(String orderId) {
            return orderId + ": Your order has been picked up by runner.";
        }

        public static String orderDeliveringVendor(String orderId) {
            return orderId + ": Your order is on the way.";
        }

        public static String orderCompletedVendor(String orderId) {
            return orderId + ": Your order has been completed.";
        }

        public static String orderCancelledVendor(String orderId) {
            return orderId + ": Your order has been cancelled.";
        }

        public static String orderReceivedRunner(String orderId) {
            return orderId + ": You have received a new order.";
        }

        public static String orderReadyPickUpRunner(String orderId) {
            return orderId + ": Vendor has prepared the order.";
        }

        public static String orderCompletedRunner(String orderId) {
            return orderId + ": Order has been completed.";
        }

        public static String sendReceipt(String transactionId) {
            return "Receipt for transaction <b>" + transactionId + "</b>.";
        }
    }
}
