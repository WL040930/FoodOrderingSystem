package com.Group3.foodorderingsystem.Core.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.CategoryEnum;
import com.Group3.foodorderingsystem.Core.Storage.Storage;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;

public class ItemServices {

    private static List<ItemModel> getItems() {
        return FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ITEM), ItemModel.class);
    }

    public static ItemModel saveItem(ItemModel itemModel) {
        List<ItemModel> items = getItems();

        if (itemModel.getItemId() == null) {
            itemModel.setItemId(Storage.generateNewId());
            items.add(itemModel); // Add new item
        } else {
            // Find the index of the existing item
            int index = -1;
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getItemId().equals(itemModel.getItemId())) {
                    index = i;
                    break;
                }
            }

            if (index != -1) {
                items.set(index, itemModel); // Replace existing item
            } else {
                items.add(itemModel); // If item not found, add as new
            }
        }

        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.ITEM), items);
        return itemModel;
    }

    public static List<ItemModel> getItemByVendor(VendorModel vendorModel) {
        return getItems().stream()
                .filter(item -> item.getVendorModel().getId().equals(vendorModel.getId()) && item.isEnabled())
                .collect(java.util.stream.Collectors.toList());
    }

    public static List<ItemModel> getItemByVendorWithDisabled(VendorModel vendorModel) {
        return getItems().stream()
                .filter(item -> item.getVendorModel().getId().equals(vendorModel.getId()))
                .collect(java.util.stream.Collectors.toList());
    }

    public static Map<CategoryEnum, List<VendorModel>> filterVendorItem() {
        Map<CategoryEnum, List<VendorModel>> vendorsByCategory = new HashMap<>();

        for (CategoryEnum i : CategoryEnum.values()) {
            vendorsByCategory.put(i, new ArrayList<>());
        }

        for (VendorModel vendor : UserServices.getVendors()) {
            List<ItemModel> items = getItemByVendor(vendor);

            long foodCount = items.stream().filter(item -> item.getItemCategory() == CategoryEnum.FOOD).count();
            long drinkCount = items.stream().filter(item -> item.getItemCategory() == CategoryEnum.DRINK).count();

            if (foodCount > drinkCount) {
                vendorsByCategory.get(CategoryEnum.FOOD).add(vendor);
            } else {
                vendorsByCategory.get(CategoryEnum.DRINK).add(vendor);
            }
        }

        return vendorsByCategory;
    }

    public static void updateItemStatus(ItemModel itemModel, boolean status) {
        List<ItemModel> items = getItems();

        for (ItemModel item : items) {
            if (item.getItemId().equals(itemModel.getItemId())) {
                item.setEnabled(status);
                break;
            }
        }

        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.ITEM), items);
    }

    public static boolean isEligibleForDeletion(ItemModel itemModel) {
        return !VendorOrderServices.didItemOrdered(itemModel.getItemId());
    }

    public static boolean deleteItem(ItemModel itemModel) {
        if (!isEligibleForDeletion(itemModel)) {
            return false;
        }

        List<ItemModel> items = getItems();
        items.removeIf(item -> item.getItemId().equals(itemModel.getItemId()));

        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.ITEM), items);
        return true;
    }

}
