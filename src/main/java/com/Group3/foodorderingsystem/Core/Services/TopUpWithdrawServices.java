package com.Group3.foodorderingsystem.Core.Services;

import java.io.ObjectInputFilter.Status;
import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TopUpWithdrawModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel.TransactionType;
import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
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
        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.TOPUPWITHDRAW), topUpWithdrawList);
    }
}
