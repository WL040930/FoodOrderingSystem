package com.Group3.foodorderingsystem.Core.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.Group3.foodorderingsystem.Core.Model.Entity.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.ItemModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.OrderModel;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Core.Model.Enum.StatusEnum;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;

public class CustomerOrderServices {

    //generate unique order id
    private static String generateUniqueOrderId() {
        List<OrderModel> orders = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class);

        if (orders == null) {
            orders = new ArrayList<>();
        }

        String orderId = UUID.randomUUID().toString();
        boolean isDuplicate;
        do {
            orderId = UUID.randomUUID().toString();
            isDuplicate = false;
            for (OrderModel order : orders) {
                if (order.getOrderId().equals(orderId)) {
                    isDuplicate = true;
                    break;
                }
            }
        } while (isDuplicate);
        
        return orderId;
    }



    //save order to file
    private static void saveOrderToFile(OrderModel order) {
        List<OrderModel> orders = FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ORDER), OrderModel.class);

        orders.add(order);

        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.ORDER), orders);
    }


    //calculate total price
    public double calculateTotalPrice(List<ItemModel> items) {
        return items.stream()
            .mapToDouble(item -> item.getItemPrice() * item.getItemQuantity())
            .sum();
    }


    // save order to file
    public void saveOrder() {
        List<ItemModel> items = SessionUtil.getItemsFromSession();
        CustomerModel customer = SessionUtil.getCustomerFromSession();

        // Calculate total price
        double totalPrice = calculateTotalPrice(items);

        // Create order object
        OrderModel order = new OrderModel();
        order.setOrderId(generateUniqueOrderId());
        order.setItems(items);
        order.setCustomer(customer);
        order.setTotalPrice(totalPrice);
        order.setStatus(StatusEnum.PENDING);

        // Save order to file
        saveOrderToFile(order);


        // Clear only item from session
        SessionUtil.setItemsInSession(null);

    }


    //TODO: cancel order

    //TODO: list pending order history

    //TODO: list  active order history 

    //TODO: list past order history

    //TODO: list a specific order details

    //TODO: Reorder previous order

    //TODO: Generate receipt 
    
}
