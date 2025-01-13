package com.Group3.foodorderingsystem.Core.Services;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.OrderModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.RunnerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Core.Model.Enum.RunnerStatusEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;



public class VendorOrderServices {
    
    //get pending order list
    public static List<OrderModel> getOrderList(String type) {

        VendorModel vendor = (VendorModel) SessionUtil.getVendorFromSession();
        List<OrderModel> orderList;

        if (type.equals("Pending")) {
            orderList = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class, order -> order.getVendor().equals(vendor.getId()) && order.getStatus().toString().equals("PENDING"));
            Collections.sort(orderList, Comparator.comparing(OrderModel::getTime).reversed());
            
        } else if (type.equals("Active")) {
            orderList = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class, order -> order.getVendor().equals(vendor.getId()) && order.getStatus().toString().equals("PREPARING") );
            Collections.sort(orderList, Comparator.comparing(OrderModel::getTime).reversed());
            
        } else if (type.equals("Prepared")) {
            orderList = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class, order -> order.getVendor().equals(vendor.getId()) && (order.getStatus().toString().equals("READY_FOR_PICKUP") || order.getStatus().toString().equals("DELIVERING") || order.getStatus().toString().equals("WAITING_FOR_RIDER")));
            // sort the order by priotise ready for pickup, then sort by time
            orderList = orderList.stream()
                        .sorted(Comparator.comparing((OrderModel o) -> !o.getStatus().toString().equals("READY_FOR_PICKUP"))
                                        .thenComparing(OrderModel::getTime, Comparator.reverseOrder()))
                        .collect(Collectors.toList());
        } else  {
            orderList = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class, order -> order.getVendor().equals(vendor.getId()) && (order.getStatus().toString().equals("DELIVERED") || order.getStatus().toString().equals("PICKED_UP") || order.getStatus().toString().equals("SERVED")));
            Collections.sort(orderList, Comparator.comparing(OrderModel::getTime).reversed());
        } 

        return orderList;
    }


    public static void updateOrderStatus(OrderModel order, StatusEnum status) {
        List<OrderModel> orderList = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class);

        for (OrderModel o : orderList) {
            if (o.getOrderId().equals(order.getOrderId())) {
                o.setStatus(status);
                break;
            }
        }

        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.ORDER), orderList);
    }

    //assign order to runner (change the status to assigning, then add the order id into the runner list)
    public static void assignOrderToRunner(String orderId) {
        
        //first find the first runner that is available and the assigned orderID is not equal to the current orderID
        List<RunnerModel> runners = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.RUNNER), RunnerModel.class, runner -> runner.getStatus().equals(RunnerStatusEnum.AVAILABLE) && !runner.getOrderIDs().contains(orderId));

        //update the order status to assigning
        if (!runners.isEmpty())  {
            RunnerModel availableRunner = runners.get(0);
            List<RunnerModel> runnerList = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.RUNNER), RunnerModel.class);

            for (RunnerModel runner : runnerList) {
                if (runner.getId().equals(availableRunner.getId())) {
                    runner.setStatus(RunnerStatusEnum.ASSIGNING);
                    List<String> orderList = runner.getOrderIDs();
                    orderList.add(orderId); 
                    runner.setorderIDs(orderList);
                    break;
                }
            }

            FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.RUNNER), runnerList);
        } //TODO: if no runner is available, cancel the order and refund the customer

    }

}
