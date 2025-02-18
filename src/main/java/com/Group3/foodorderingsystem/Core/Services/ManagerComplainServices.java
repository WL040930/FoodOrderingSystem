package com.Group3.foodorderingsystem.Core.Services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ComplainModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Order.OrderModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.ComplainStatusEnum;
import com.Group3.foodorderingsystem.Core.Storage.Storage;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;

public class ManagerComplainServices {

    // Get orders based on complaint status (PENDING or RESOLVED)
    public static List<OrderModel> getOrders(ComplainStatusEnum status) {
        // Retrieve complaint list
        List<ComplainModel> complains = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.COMPLAIN), ComplainModel.class);

        // Retrieve order list
        List<OrderModel> orders = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class);

        // Create a map of orderId -> complaint to improve lookup performance
        Map<String, ComplainModel> complainMap = complains.stream()
            .collect(Collectors.toMap(ComplainModel::getOrderId, complain -> complain));

        // Filter orders based on complaint status
        return orders.stream()
            .filter(order -> {
                ComplainModel complain = complainMap.get(order.getOrderId());
                return complain != null && complain.getComplainStatus() == status;
            })
            .collect(Collectors.toList());
    }

    // mark the complain as resolved 
    public static void resolveComplain(String orderId) {
        // Retrieve complaint list
        List<ComplainModel> complains = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.COMPLAIN), ComplainModel.class);

        // Find the complaint
        ComplainModel complain = complains.stream()
            .filter(c -> c.getOrderId().equals(orderId))
            .findFirst()
            .orElse(null);

        // Update the complaint status
        if (complain != null) {
            complain.setComplainStatus(ComplainStatusEnum.RESOLVED);
            FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.COMPLAIN), complains);
        }
    }

    // add reply to the complain
    public static void addReply(String orderId, String reply) {
        // Retrieve complaint list
        List<ComplainModel> complains = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.COMPLAIN), ComplainModel.class);

        // Find the complaint
        ComplainModel complain = complains.stream()
            .filter(c -> c.getOrderId().equals(orderId))
            .findFirst()
            .orElse(null);

        // Update the complaint reply
        if (complain != null) {
            complain.setComplainReply(reply);
            FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.COMPLAIN), complains);
        }
    }

    //fine vendor
    public static void fineVendor(String orderId, double fine) {
        //TODO: wait for transaction fine
    }
}
