package com.Group3.foodorderingsystem.Core.Services;

import java.io.ObjectInputFilter.Status;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TopUpWithdrawModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel.TransactionType;
import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;
import com.Group3.foodorderingsystem.Core.Storage.Storage;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;

public class TopUpWithdrawServices {

    private static List<TopUpWithdrawModel> getTopUpWithdrawList() {
        return FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.TOPUPWITHDRAW), TopUpWithdrawModel.class);
    }

    public static void createNewRequest(String userId, Double amount, TransactionType transactionType) {
        List<TopUpWithdrawModel> topUpWithdrawList = getTopUpWithdrawList();
        TopUpWithdrawModel topUpWithdrawModel = new TopUpWithdrawModel();
        topUpWithdrawModel.setTopUpWithdrawModelId(Storage.generateNewId());
        topUpWithdrawModel.setUserId(userId);
        topUpWithdrawModel.setAmount(amount);
        topUpWithdrawModel.setTransactionType(transactionType);
        topUpWithdrawModel.setStatus(Status.UNDECIDED);
        topUpWithdrawList.add(topUpWithdrawModel);
        NotificationServices.createNewNotification(userId,
                transactionType == TransactionType.TOPUP ? NotificationServices.Template.topUpRequest(amount)
                        : NotificationServices.Template.withdrawRequest(amount));

        // send notification to admin also
        List<User> adminList = UserServices.getAdmins().stream()
                .filter(user -> user.getRole() == RoleEnum.ADMIN)
                .collect(Collectors.toList());
        for (User user : adminList) {
            User senderName = UserServices.findUserById(userId);
            NotificationServices.createNewNotification(user.getId(),
                    transactionType == TransactionType.TOPUP
                            ? NotificationServices.Template.adminTopupRequest(senderName.getName(), amount,
                                    topUpWithdrawModel.getTopUpWithdrawModelId())
                            : NotificationServices.Template.adminWithdrawRequest(senderName.getName(), amount));
        }
        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.TOPUPWITHDRAW), topUpWithdrawList);
    }

    public static void removeRequest(String topUpWithdrawModelId) {
        List<TopUpWithdrawModel> topUpWithdrawList = getTopUpWithdrawList();
        topUpWithdrawList.removeIf(
                topUpWithdrawModel -> topUpWithdrawModel.getTopUpWithdrawModelId().equals(topUpWithdrawModelId));
        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.TOPUPWITHDRAW), topUpWithdrawList);
    }

    public static List<TopUpWithdrawModel> getTopUpWithdrawListByStatus(Status status) {
        return getTopUpWithdrawList().stream()
                .filter(topUpWithdrawModel -> topUpWithdrawModel.getStatus() == status)
                .collect(Collectors.toList())
                .stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> {
                    Collections.reverse(list);
                    return list;
                }));

    }

    public static TopUpWithdrawModel updateStatus(TopUpWithdrawModel topUpWithdrawModel, Status status) {
        List<TopUpWithdrawModel> topUpWithdrawList = getTopUpWithdrawList();
        for (TopUpWithdrawModel topUpWithdrawModelTemp : topUpWithdrawList) {
            if (topUpWithdrawModelTemp.getTopUpWithdrawModelId().equals(topUpWithdrawModel.getTopUpWithdrawModelId())) {
                topUpWithdrawModelTemp.setStatus(status);

                if (status == Status.ALLOWED) {
                    NotificationServices.createNewNotification(topUpWithdrawModelTemp.getUserId(),
                            NotificationServices.Template.approveRequest(topUpWithdrawModelTemp.getAmount(),
                                    topUpWithdrawModelTemp.getTransactionType()));

                    TransactionModel transactionModel = TransactionServices.createTransaction(
                            topUpWithdrawModelTemp.getAmount(),
                            topUpWithdrawModelTemp.getTransactionType(),
                            topUpWithdrawModelTemp.getUserId());
                    if (transactionModel == null) {
                        return null;
                    }

                } else {
                    NotificationServices.createNewNotification(topUpWithdrawModelTemp.getUserId(),
                            NotificationServices.Template.rejectRequest(topUpWithdrawModelTemp.getAmount(),
                                    topUpWithdrawModelTemp.getTransactionType()));
                }
                break;
            }
        }
        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.TOPUPWITHDRAW), topUpWithdrawList);
        return topUpWithdrawModel;
    }

}
