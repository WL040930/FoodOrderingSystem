package com.Group3.foodorderingsystem.Core.Services;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.RunnerModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.RunnerStatusEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Core.Model.Entity.Order.OrderModel;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class RunnerOrderServices {
    
    
    
    // Retrieve order list by runner id
    public static List<OrderModel> listPastOrders() {
        RunnerModel runner = (RunnerModel) SessionUtil.getRiderFromSession();
        List<OrderModel> orders = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class, order -> {
            String riderId = order.getRider();
            StatusEnum status = order.getStatus();
            return riderId != null && riderId.equals(runner.getId() ) && status == StatusEnum.DELIVERED;
        });
        Collections.sort(orders, Comparator.comparing(OrderModel::getTime).reversed());

        return orders;
    }


    //retrieve assigned order
    public static OrderModel getAssignedOrder() {
        RunnerModel runner = (RunnerModel) SessionUtil.getRiderFromSession();
        int orderIdSize = runner.getOrderIDs().size();
    
        if (orderIdSize > 0) {
            return FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class, order -> order.getOrderId().equals(runner.getOrderIDs().get(orderIdSize - 1))).get(0);
        } else {
            return null;
        }
    }

    //Update status
    public static void updateRunnerStatus(RunnerStatusEnum status) {
        RunnerModel runner = (RunnerModel) SessionUtil.getRiderFromSession();

    
        List<RunnerModel> runners = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.RUNNER), RunnerModel.class);

        for (RunnerModel r : runners) {
            if (r.getId().equals(runner.getId())) {
                r.setStatus(status);
                SessionUtil.setRiderInSession(r);
                break;
            } 
        
        }

        // retrive the order if the status is available
        if (status == RunnerStatusEnum.AVAILABLE) {
            OrderModel selectedOrder = getAssignedOrder();
            if (selectedOrder != null) {
                if (selectedOrder.getStatus() == StatusEnum.DELIVERED) {
                    for (RunnerModel r : runners) {
                        if (r.getOrderIDs().contains(selectedOrder.getOrderId())) {
                            r.getOrderIDs().remove(selectedOrder.getOrderId());
                        }
                    
                    }
                }
            }

        }
        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.RUNNER), runners);
        
    
    
    }


    //Accept order
    public static void saveRiderToOrder(OrderModel order) {
        
        RunnerModel runner = (RunnerModel) SessionUtil.getRiderFromSession();

        // save rider id to the order
        OrderModel orders = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class, o -> o.getOrderId().equals(order.getOrderId())).get(0);

        List<OrderModel> orderList = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class);

        for (OrderModel o : orderList) {
            if (o.getOrderId().equals(orders.getOrderId())) {
                o.setRider(runner.getId());
                break;
            }
        }

        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.ORDER), orderList);


    }

    // Get runner orders group by rating
    public static Map<Integer, List<OrderModel>> getRunnerOrdersGroupByRating(String runnerId) {
        List<OrderModel> orderList = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class, order -> runnerId.equals(order.getRider()) && order.getRating() != 0);
        Map<Integer, List<OrderModel>> ratingMap = new HashMap<>();

        for (OrderModel order : orderList) {
            Integer rating = order.getRating();
            ratingMap.computeIfAbsent(rating, k -> new ArrayList<>()).add(order);
        }

        return ratingMap;
    }

    //get overall rating of the runner
    public static double getRunnerOverallRating(String runnerId) {
        List<OrderModel> orderList = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class, order -> runnerId.equals(order.getRider()) && order.getRating() != 0);
        double totalRating = 0;
        for (OrderModel order : orderList) {
            totalRating += order.getRating();
        }

        return totalRating / orderList.size();
    }

}
