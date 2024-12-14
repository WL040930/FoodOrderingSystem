package com.Group3.foodorderingsystem.Core.Model.Entity.Order;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.CategoryEnum;

public class ItemModel {
    
    private String itemId; 
    private String itemName;
    private String itemDescription;
    private double itemPrice;
    private CategoryEnum itemCategory;
    private String itemImage;
    private int itemQuantity; 
    private VendorModel vendorModel; 

    public ItemModel() {
    }

    public ItemModel(String itemId, String itemName, String itemDescription, double itemPrice, CategoryEnum itemCategory, String itemImage, int itemQuantity, VendorModel vendorModel) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
        this.itemImage = itemImage;
        this.itemQuantity = itemQuantity;
        this.vendorModel = vendorModel;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public CategoryEnum getItemCategory() {
        return itemCategory;
    }

    public String getItemImage() {
        return itemImage;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public VendorModel getVendorModel() {
        return vendorModel;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemCategory(CategoryEnum itemCategory) {
        this.itemCategory = itemCategory;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public void setVendorModel(VendorModel vendorModel) {
        this.vendorModel = vendorModel;
    }
}
