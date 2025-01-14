package com.Group3.foodorderingsystem.Module.Platform.Vendor.Menu.model;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Menu.ui.AddNewItem;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Menu.ui.EditItemUI;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Menu.ui.MenuList;

import javafx.scene.Node;

public class MenuViewModel extends ViewModelConfig {

    public MenuViewModel() {
        super();
    }

    public void navigate(Node node) {
        setNode(node);
        VendorViewModel.navigate(VendorViewModel.getMenuViewModel().getNode());
    }

    public void init() {
        this.menuList = new MenuList();
        setNode(menuList);
    }

    private MenuList menuList;

    public MenuList getMenuList() {
        return menuList;
    }

    public void setMenuList(MenuList menuList) {
        this.menuList = menuList;
    }

    private AddNewItem addNewItem;

    public AddNewItem getAddNewItem() {
        return addNewItem;
    }

    public void setAddNewItem(AddNewItem addNewItem) {
        this.addNewItem = addNewItem;
    }

    private EditItemUI editItemUI;

    public EditItemUI getEditItemUI() {
        return editItemUI;
    }

    public void setEditItemUI(EditItemUI editItemUI) {
        this.editItemUI = editItemUI;
    }
}
