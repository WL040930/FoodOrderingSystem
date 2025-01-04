package com.Group3.foodorderingsystem.Module.Platform.Vendor.Menu.ui;

import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class MenuList extends BaseContentPanel {

    public MenuList() {
        super();

        setHeader(header());
    }

    private Node header() {
        // Create the HBox for the header
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER); // Aligns items vertically and horizontally in the center

        // Create the back button
        TitleBackButton backButton = new TitleBackButton("Menu");

        // Create a spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS); // Makes the spacer take up all available space

        // Create the "Add New Item" button
        Button addNewItemButton = new Button("Add New Item");
        addNewItemButton.setOnAction(event -> {
            VendorViewModel.getMenuViewModel().setAddNewItem(new AddNewItem());
            VendorViewModel.getMenuViewModel().navigate(VendorViewModel.getMenuViewModel().getAddNewItem());
        });

        // Add right padding to the button
        HBox.setMargin(addNewItemButton, new Insets(0, 10, 0, 0)); 

        // Add the components to the header
        header.getChildren().addAll(backButton, spacer, addNewItemButton);

        return header;
    }
}
