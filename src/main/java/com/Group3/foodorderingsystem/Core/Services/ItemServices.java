package com.Group3.foodorderingsystem.Core.Services;

import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Storage.Storage;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;

public class ItemServices {

    public static List<ItemModel> getItems() {
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

    
}
