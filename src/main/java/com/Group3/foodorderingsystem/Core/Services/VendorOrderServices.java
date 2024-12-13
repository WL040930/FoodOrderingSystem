package com.Group3.foodorderingsystem.Core.Services;

import java.util.List;
import com.Group3.foodorderingsystem.Core.Model.Entity.OrderModel;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;

public class VendorOrderServices {

    //List pending orders

    
    // Accept an order
    public void acceptOrder(String orderId) {
        List<OrderModel> orders = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class);

        for (OrderModel order : orders) {
            if (order.getOrderId().equals(orderId)) {
                order.setStatus(StatusEnum.PREPARING);
                break;
            }
        }

        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.ORDER), orders);
    }

    // Reject an order
    public void rejectOrder(String orderId) {
        List<OrderModel> orders = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class);

        for (OrderModel order : orders) {
            if (order.getOrderId().equals(orderId)) {
                order.setStatus(StatusEnum.REJECTED);
                break;
            }
        }

        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.ORDER), orders);
    }
}