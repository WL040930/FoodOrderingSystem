package com.Group3.foodorderingsystem.Module.Platform.Vendor.Menu.ui;

import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.BottomButton;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class AddNewItem extends BaseContentPanel {

    public AddNewItem() {
        super();

        setHeader(header());
        setContent(content());
        setFooter(footer());
    }

    public Node header() {
        return new TitleBackButton("Create New Item", () -> {
            VendorViewModel.getMenuViewModel().navigate(VendorViewModel.getMenuViewModel().getMenuList());
        });
    }

    public Node content() {
        return new VBox();
    }

    public Node footer() {
        return new BottomButton("Create", () -> {
            // Handle create button click
        });
    }
}
