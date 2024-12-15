package com.Group3.foodorderingsystem.Core.Util;

import com.Group3.foodorderingsystem.Core.Model.Entity.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.ItemModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.SessionEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionUtil {
    private static Map<String, Object> session = new HashMap<>();

    public static void setItemsInSession(List<ItemModel> items) {
        session.put(SessionEnum.ITEMS.getKey(), items);
    }

    @SuppressWarnings("unchecked")
    public static List<ItemModel> getItemsFromSession() {
        return (List<ItemModel>) session.getOrDefault(SessionEnum.ITEMS.getKey(), new ArrayList<>());
    }

    public static void setCustomerInSession(CustomerModel customer) {
        session.put(SessionEnum.CUSTOMER.getKey(), customer);
    }

    public static CustomerModel getCustomerFromSession() {
        return (CustomerModel) session.get(SessionEnum.CUSTOMER.getKey());
    }

    public static void setAdminInSession(Object admin) {
        session.put(SessionEnum.ADMIN.getKey(), admin);
    }

    public static Object getAdminFromSession() {
        return session.get(SessionEnum.ADMIN.getKey());
    }

    public static void setVendorInSession(Object vendor) {
        session.put(SessionEnum.VENDOR.getKey(), vendor);
    }



    public static Object getVendorFromSession() {
        return session.get(SessionEnum.VENDOR.getKey());
    }

    public static void setRiderInSession(Object rider) {
        session.put(SessionEnum.RIDER.getKey(), rider);
    }

    public static Object getRiderFromSession() {
        return session.get(SessionEnum.RIDER.getKey());
    }

    public static void setSelectedOrderInSession(Object order) {
        session.put(SessionEnum.SELECTED_ORDER.getKey(), order);
    }

    public static Object getSelectedOrderFromSession() {
        return session.get(SessionEnum.SELECTED_ORDER.getKey());
    }

    public static void clearSession() {
        session.clear();
    }
}