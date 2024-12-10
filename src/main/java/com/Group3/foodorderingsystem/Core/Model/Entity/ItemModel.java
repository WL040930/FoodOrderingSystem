package com.Group3.foodorderingsystem.Core.Model.Entity;

public class ItemModel {
    
    private String itemId; 
    private String itemName;
    private String itemDescription;
    private double itemPrice;
    private String itemCategory;
    private String itemImage;
    private String itemQuantity; 
    private ShopModel shopModel; 

    public ItemModel() {
    }

    public ItemModel(String itemId, String itemName, String itemDescription, double itemPrice, String itemCategory, String itemImage, String itemQuantity, ShopModel shopModel) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
        this.itemImage = itemImage;
        this.itemQuantity = itemQuantity;
        this.shopModel = shopModel;
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

    public String getItemCategory() {
        return itemCategory;
    }

    public String getItemImage() {
        return itemImage;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public ShopModel getShopModel() {
        return shopModel;
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

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public void setShopModel(ShopModel shopModel) {
        this.shopModel = shopModel;
    }
}
