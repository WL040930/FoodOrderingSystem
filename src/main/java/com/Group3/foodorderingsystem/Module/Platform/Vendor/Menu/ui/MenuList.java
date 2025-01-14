package com.Group3.foodorderingsystem.Module.Platform.Vendor.Menu.ui;

import java.util.ArrayList;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Services.ItemServices;
import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.Card;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.DynamicSearchBarUI;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Vouchers.widgets.KButton;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class MenuList extends BaseContentPanel {

    private VBox resultContainer;

    public MenuList() {
        super();

        setHeader(header());
        setContent(content());

        setFooterHeight(0);
        setContentHeight(580);
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
        resultContainer = new VBox();

        refreshContent();

        return resultContainer;
    }

    private void refreshContent() {
        resultContainer.getChildren().clear();

        Object vendor = SessionUtil.getVendorFromSession();
        DynamicSearchBarUI<ItemModel> searchBarWithData;

        if (vendor instanceof VendorModel) {
            VendorModel vendorModel = (VendorModel) vendor;
            searchBarWithData = new DynamicSearchBarUI<>(
                    ItemServices.getItemByVendorWithDisabled(vendorModel),
                    "itemName",
                    null,
                    this::buildCard);
        } else {
            searchBarWithData = new DynamicSearchBarUI<>(new ArrayList<>(), "itemName", null, this::buildCard);
        }

        resultContainer.getChildren().add(searchBarWithData);
    }

    private Node buildCard(ItemModel itemModel) {
        VBox card = new VBox();

        HBox contentContainer = new HBox();

        ImageView imageView = Images.getImageView(itemModel.getItemImage(), 100, 100);
        Label itemName = new Label(itemModel.getItemName());
        Label itemPrice = new Label("Price: " + itemModel.getItemPrice());
        Label itemCategory = new Label("Category: " + itemModel.getItemCategory().toString());

        VBox itemDetails = new VBox(itemName, itemPrice, itemCategory);
        itemDetails.setPadding(new Insets(0, 0, 0, 10));

        contentContainer.getChildren().addAll(imageView, itemDetails);

        HBox buttonContainer = new HBox(10);
        KButton editButton = new KButton("Edit", () -> {
            VendorViewModel.getMenuViewModel().setEditItemUI(new EditItemUI(itemModel));
            VendorViewModel.getMenuViewModel().navigate(VendorViewModel.getMenuViewModel().getEditItemUI());
        });

        KButton discardButton = new KButton("Discard", () -> {
            ItemServices.updateItemStatus(itemModel, false);
            refreshContent();
        });
        discardButton.setBackgroundColor(KButton.red);

        KButton enableButton = new KButton("Enable", () -> {
            ItemServices.updateItemStatus(itemModel, true);
            refreshContent();
        });
        enableButton.setBackgroundColor(KButton.green);

        buttonContainer.getChildren().addAll(itemModel.isEnabled() ? discardButton : enableButton, editButton);
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);

        card.getChildren().addAll(contentContainer, buttonContainer);

        HBox cardContainer = new HBox(new Card(card, 470, 150, null));
        cardContainer.setPadding(new Insets(0, 10, 0, 10));
        return cardContainer;
    }
}
