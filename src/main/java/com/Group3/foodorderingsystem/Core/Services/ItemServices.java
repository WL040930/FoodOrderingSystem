package com.Group3.foodorderingsystem.Core.Services;

import java.io.File;
import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Storage.Storage;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;

public class ItemServices {

    public static List<ItemModel> getItems() {
        return FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ITEM), ItemModel.class);
    }

    // save new or modify existing, use this
    public static ItemModel saveItem(ItemModel itemModel) {
        List<ItemModel> items = getItems();

        if (itemModel.getItemId() == null)
            itemModel.setItemId(Storage.generateNewId());
        else
            items.removeIf(item -> item.getItemId().equals(itemModel.getItemId()));

        items.add(itemModel);

        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.ITEM), items);
        return itemModel;
    }

    public static ItemModel saveItem(ItemModel itemModel, File file) {
        List<ItemModel> items = getItems();

        itemModel.setItemImage(Storage.saveFile(file));

        if (itemModel.getItemId() == null)
            itemModel.setItemId(Storage.generateNewId());
        else
            items.removeIf(item -> item.getItemId().equals(itemModel.getItemId()));

        items.add(itemModel);

        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.ITEM), items);
        return itemModel;
    }
}
