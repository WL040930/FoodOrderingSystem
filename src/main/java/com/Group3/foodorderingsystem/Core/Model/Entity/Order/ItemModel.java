package com.Group3.foodorderingsystem.Core.Model.Entity.Order;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.CategoryEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemModel {

    private String itemId;
    private String itemName;
    private String itemDescription;
    private double itemPrice;
    private CategoryEnum itemCategory;
    private String itemImage;
    private int itemQuantity;
    private VendorModel vendorModel;
    private boolean isEnabled;

    public ItemModel() {
        this.isEnabled = true;
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

    public VendorModel getVendorModel() {
        return vendorModel;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
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

    public void setVendorModel(VendorModel vendorModel) {
        this.vendorModel = vendorModel;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
