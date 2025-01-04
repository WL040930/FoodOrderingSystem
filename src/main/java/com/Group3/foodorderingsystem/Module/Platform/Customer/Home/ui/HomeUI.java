package com.Group3.foodorderingsystem.Module.Platform.Customer.Home.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Services.ItemServices;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.DynamicSearchBarUI;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class HomeUI extends BaseContentPanel {

    public HomeUI() {
        super();
        setHeader(new TitleBackButton("Home"));
        setContent(content());
    }

    private Node content() {
        List<ItemModel> items = ItemServices.getItems();
        VBox presetUI = new VBox(new Label("No search input. Start typing to search..."));

        // Define the custom render template
        DynamicSearchBarUI.RenderTemplate<ItemModel> renderTemplate = item -> {
            HBox itemBox = new HBox(10);
            Label itemName = new Label(item.getItemName());
            Button itemButton = new Button("Select");
            itemBox.getChildren().addAll(itemName, itemButton);
            return itemBox;
        };

        // Create the search widget with the custom render template
        DynamicSearchBarUI<ItemModel> searchWidget = new DynamicSearchBarUI<>(items, "itemName", presetUI, renderTemplate);

        return searchWidget; // Return the complete search widget
    }
}
