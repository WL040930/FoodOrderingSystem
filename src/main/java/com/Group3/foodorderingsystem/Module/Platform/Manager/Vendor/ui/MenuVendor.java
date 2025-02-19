package com.Group3.foodorderingsystem.Module.Platform.Manager.Vendor.ui;

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
import com.Group3.foodorderingsystem.Module.Platform.Manager.ManagerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Menu.ui.EditItemUI;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Vouchers.widgets.KButton;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MenuVendor extends BaseContentPanel {

    private VBox resultContainer;
    private VendorModel vendorModel;
    public MenuVendor(VendorModel vendorModel) {
        super();
        this.vendorModel = vendorModel;

        setHeader(header());
        setContent(content());

        setFooterHeight(0);
        setContentHeight(580);
    }

    private Node header() {
        return new TitleBackButton("Vendor Menu", () -> {
            ManagerViewModel.getVendorPerformanceViewModel()
                    .navigate(ManagerViewModel.getVendorPerformanceViewModel().getVendorListUI());
        });
    }

    private Node content() {
        resultContainer = new VBox();

        refreshContent();

        return resultContainer;
    }

    private void refreshContent() {
        resultContainer.getChildren().clear();

        DynamicSearchBarUI<ItemModel> searchBarWithData = new DynamicSearchBarUI<>(
                    ItemServices.getItemByVendorWithDisabled(vendorModel),
                    "itemName",
                    null,
                    this::buildCard);

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

        buttonContainer.getChildren().addAll(itemModel.isEnabled() ? discardButton : enableButton);
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);

        card.getChildren().addAll(contentContainer, buttonContainer);

        HBox cardContainer = new HBox(new Card(card, 470, 150, null));
        cardContainer.setPadding(new Insets(0, 10, 0, 10));
        return cardContainer;
    }

}
