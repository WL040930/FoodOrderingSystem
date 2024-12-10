package com.Group3.foodorderingsystem.Core.Services;

import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Entity.ItemModel;
import com.Group3.foodorderingsystem.Core.Storage.StorageEnum;
import com.Group3.foodorderingsystem.Core.Util.FileUtil;

public class ItemServices {
    
    public static List<ItemModel> getItems() {
        return FileUtil.loadFile(StorageEnum.getFileName(StorageEnum.ITEM), ItemModel.class); 
    }

    public static ItemModel saveItem(ItemModel itemModel) {
        List<ItemModel> items = getItems();
        items.add(itemModel);

        FileUtil.saveFile(StorageEnum.getFileName(StorageEnum.ITEM), items);
        return itemModel;
    }
}
