package com.Group3.foodorderingsystem.Core.Services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.Group3.foodorderingsystem.Core.Model.Entity.Finance.TransactionModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.Order.OrderModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.RunnerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Core.Model.Enum.RunnerStatusEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;
import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;

public class VendorOrderServices {

    private static List<OrderModel> orderList() {
        return FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class);
    }

    // get order list
    public static List<OrderModel> getOrderList(String type) {

        VendorModel vendor = (VendorModel) SessionUtil.getVendorFromSession();
        List<OrderModel> orderList;

        if (type.equals("Pending")) {
            orderList = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class,
                    order -> order.getVendor().equals(vendor.getId())
                            && order.getStatus().toString().equals("PENDING"));
            Collections.sort(orderList, Comparator.comparing(OrderModel::getTime).reversed());

        } else if (type.equals("Active")) {
            orderList = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class,
                    order -> order.getVendor().equals(vendor.getId())
                            && order.getStatus().toString().equals("PREPARING"));
            Collections.sort(orderList, Comparator.comparing(OrderModel::getTime).reversed());

        } else if (type.equals("Prepared")) {
            orderList = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class,
                    order -> order.getVendor().equals(vendor.getId())
                            && (order.getStatus().toString().equals("READY_FOR_PICKUP")
                                    || order.getStatus().toString().equals("DELIVERING")
                                    || order.getStatus().toString().equals("WAITING_FOR_RIDER")));
            // sort the order by priotise ready for pickup, then sort by time
            orderList = orderList.stream()
                    .sorted(Comparator.comparing((OrderModel o) -> !o.getStatus().toString().equals("READY_FOR_PICKUP"))
                            .thenComparing(OrderModel::getTime, Comparator.reverseOrder()))
                    .collect(Collectors.toList());
        } else {
            orderList = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class,
                    order -> order.getVendor().equals(vendor.getId())
                            && (order.getStatus().toString().equals("DELIVERED")
                                    || order.getStatus().toString().equals("PICKED_UP")
                                    || order.getStatus().toString().equals("SERVED")));
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

        if (status == StatusEnum.REJECTED) {
            CustomerOrderServices.setBalance(order.getCustomer(), order.getTotalPrice(), "customer");
        } else if (status == StatusEnum.PREPARING) {
            TransactionServices.createTransaction(order.getOrderId(), TransactionModel.TransactionType.PAYMENT,
                    RoleEnum.VENDOR);
        }
    }

    // retrieve overall rating of the vendor
    public static double getOverallRating(String vendorId) {
        List<OrderModel> orderList = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class);
        List<OrderModel> vendorOrderList = orderList.stream().filter(order -> order.getVendor().equals(vendorId))
                .collect(Collectors.toList());
        double totalRating = 0;
        int count = 0;

        for (OrderModel order : vendorOrderList) {
            if (order.getRating() != 0) {
                totalRating += order.getRating();
                count++;
            }
        }

        if (count == 0) {
            return 0;
        }

        return totalRating / count;
    }

    public static OrderModel getOrderById(String Id) {
        for (OrderModel order : orderList()) {
            if (order.getOrderId().equals(Id)) {
                return order;
            }
        }
        return null;
    }

    // assign order to runner (change the status to assigning, then add the order id
    // into the runner list)
    public static void assignOrderToRunner(String orderId) {

        // first find the first runner that is available and the assigned orderID is not
        // equal to the current orderID
        List<RunnerModel> runners = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.RUNNER),
                RunnerModel.class, runner -> runner.getStatus().equals(RunnerStatusEnum.AVAILABLE)
                        && !runner.getOrderIDs().contains(orderId));

        // update the order status to assigning
        if (!runners.isEmpty()) {
            RunnerModel availableRunner = runners.get(0);
            List<RunnerModel> runnerList = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.RUNNER),
                    RunnerModel.class);

            for (RunnerModel runner : runnerList) {
                if (runner.getId().equals(availableRunner.getId())) {
                    runner.setStatus(RunnerStatusEnum.ASSIGNING);
                    List<String> orderList = runner.getOrderIDs();
                    orderList.add(orderId);
                    runner.setorderIDs(orderList);
                    break;
                }
            }

            NotificationServices.createNewNotification(availableRunner.getId(),
                    NotificationServices.Template.orderReceivedRunner(orderId));

            FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.RUNNER), runnerList);
        } else {
            // set the order status to cancelled
            OrderModel order = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class,
                    o -> o.getOrderId().equals(orderId)).get(0);
            updateOrderStatus(order, StatusEnum.CANCELLED);

            // refund the customer
            CustomerOrderServices.setBalance(order.getCustomer(), order.getTotalPrice(), "customer");

            // deduct the vendor balance
            CustomerOrderServices.setBalance(order.getVendor(), -1 * order.getSubTotalPrice(), "vendor");

            // create a refund transaction
            TransactionServices.createTransaction(order.getOrderId(), TransactionModel.TransactionType.REFUND,
                    RoleEnum.CUSTOMER);
            NotificationServices.createNewNotification(order.getCustomer(),
                    NotificationServices.Template.orderCancelledCustomer(order.getOrderId()));
            NotificationServices.createNewNotification(order.getVendor(),
                    NotificationServices.Template.orderCancelledVendor(order.getOrderId()));
        }

    }

    // get the orders grouped by rating
    public static Map<Integer, List<OrderModel>> getOrdersGroupedByRating(VendorModel vendor) {
        List<OrderModel> orderList = FileUtil.getModelByField(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class, order -> order.getVendor().equals(vendor.getId()) && order.getRating() != 0);
        Map<Integer, List<OrderModel>> ratingMap = new HashMap<>();

        for (OrderModel order : orderList) {
            Integer rating = order.getRating();
            ratingMap.computeIfAbsent(rating, k -> new ArrayList<>()).add(order);
        }

        return ratingMap;
    }

    public static List<OrderModel> filterOrders(List<OrderModel> orders, String filter) {
        LocalDate now = LocalDate.now();
        return orders.stream().filter(order -> {
            Date orderDate = order.getTime();
            LocalDate localOrderDate = orderDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            switch (filter) {
                case "Day":
                    return ChronoUnit.DAYS.between(localOrderDate, now) <= 1;
                case "Week":
                    return ChronoUnit.WEEKS.between(localOrderDate, now) <= 1;
                case "Month":
                    return ChronoUnit.MONTHS.between(localOrderDate, now) <= 1;
                default:
                    return true;
            }
        }).collect(Collectors.toList());
    }

    public static boolean didItemOrdered(String itemId) {
        List<OrderModel> orderList = orderList();
        for (OrderModel order : orderList) {
            if (order.getItems().stream().anyMatch(item -> item.getItemId().equals(itemId))) {
                return true;
            }
        }
        return false;
    }
}
