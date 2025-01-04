package com.Group3.foodorderingsystem.Module.Platform.Vendor.Menu.ui;

import java.util.ArrayList;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.CategoryEnum;
import com.Group3.foodorderingsystem.Core.Services.ItemServices;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Menu.widgets.ItemDisplay;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class MenuList extends BaseContentPanel {

    public MenuList() {
        super();

        setHeader(header());
        setContent(content());
    }

    private Node header() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);

        TitleBackButton backButton = new TitleBackButton("Menu");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addNewItemButton = new Button("Add New Item");
        addNewItemButton.setOnAction(event -> {
            VendorViewModel.getMenuViewModel().setAddNewItem(new AddNewItem());
            VendorViewModel.getMenuViewModel().navigate(VendorViewModel.getMenuViewModel().getAddNewItem());
        });

        HBox.setMargin(addNewItemButton, new Insets(0, 10, 0, 0));

        header.getChildren().addAll(backButton, spacer, addNewItemButton);

        return header;
    }

    private Node content() {
        VBox resultContainer = new VBox();

        ItemDisplay displayTemplate = new ItemDisplay(resultContainer);
        SearchBarWithData<ItemModel> searchBarWithData = null;

        Object vendor = SessionUtil.getVendorFromSession();
        if (vendor instanceof VendorModel) {
            VendorModel vendorModel = (VendorModel) vendor;
            searchBarWithData = new SearchBarWithData<>(ItemServices.getItemByVendor(vendorModel),
                    ItemModel.class,
                    displayTemplate,
                    "itemName");
        } else {
            searchBarWithData = new SearchBarWithData<>(new ArrayList<>(),
                    ItemModel.class,
                    displayTemplate,
                    "itemName");
        }

        return new VBox(searchBarWithData, resultContainer);
    }
}
