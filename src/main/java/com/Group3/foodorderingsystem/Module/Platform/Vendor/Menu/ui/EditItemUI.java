package com.Group3.foodorderingsystem.Module.Platform.Vendor.Menu.ui;

import java.util.Arrays;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.CategoryEnum;
import com.Group3.foodorderingsystem.Core.Services.ItemServices;
import com.Group3.foodorderingsystem.Core.Storage.Storage;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.BottomButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.FileUploadType;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.FileUploadWidget;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.PopupMessage;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.TitleTextField;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.TitleTextFieldEnum;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Menu.widgets.ComboBoxWidget;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Vouchers.ui.VoucherAddUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class EditItemUI extends BaseContentPanel {

    private TitleTextField itemName;
    private TitleTextField itemDescription;
    private TitleTextField itemPrice;
    private ComboBoxWidget category;
    private FileUploadWidget itemImage;

    private ItemModel itemModel;

    public EditItemUI(ItemModel itemModel) {
        super();
        this.itemModel = itemModel;

        setHeader(header());
        setContent(content());
        setFooter(footer());
    }

    private Node header() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);

        TitleBackButton backButton = new TitleBackButton("Edit Item", () -> {
            VendorViewModel.getMenuViewModel().navigate(VendorViewModel.getMenuViewModel().getMenuList());
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button deleteButton = new Button("Delete Item");
        deleteButton.setOnAction(event -> {
            if (ItemServices.deleteItem(itemModel)) {
                PopupMessage.showMessage("Item deleted. ", "success", () -> {
                    VendorViewModel.initMenuViewModel();
                    VendorViewModel.getMenuViewModel().navigate(VendorViewModel.getMenuViewModel().getNode());
                });
                VendorViewModel.initMenuViewModel();
            } else {
                PopupMessage.showMessage("Failed to delete item. ", "error", () -> {
                });
            }
        });

        HBox.setMargin(deleteButton, new Insets(0, 10, 0, 0));

        header.getChildren().addAll(backButton, spacer);
        if (ItemServices.isEligibleForDeletion(itemModel)) {
            header.getChildren().add(deleteButton);
        }

        return header;
    }

    private Node content() {
        VBox content = new VBox(10);
        content.setStyle("-fx-padding: 10px;");

        itemName = new TitleTextField("Name of Food/Drinks", itemModel.getItemName(),
                TitleTextFieldEnum.TextField);
        itemDescription = new TitleTextField("Description", itemModel.getItemDescription(),
                TitleTextFieldEnum.TextArea);
        itemPrice = new TitleTextField("Price", String.valueOf(itemModel.getItemPrice()),
                TitleTextFieldEnum.DecimalField);
        category = new ComboBoxWidget("Category",
                Arrays.stream(CategoryEnum.values())
                        .map(CategoryEnum::toString)
                        .toArray(String[]::new));
        category.setSelectedValue(itemModel.getItemCategory().toString());

        itemImage = new FileUploadWidget(FileUploadType.CIRCLE_AVATAR, itemModel.getItemImage());

        content.getChildren().addAll(itemName, itemDescription, itemPrice, category, itemImage);

        return content;
    }

    private Node footer() {
        return new BottomButton("Save", () -> {
            if (itemName.getInputValue() != "") {
                itemModel.setItemName(itemName.getInputValue());
            }

            if (itemDescription.getInputValue() != "") {
                itemModel.setItemDescription(itemDescription.getInputValue());
            }

            if (itemPrice.getInputValue() != "") {
                itemModel.setItemPrice(Double.parseDouble(itemPrice.getInputValue()));
            }

            itemModel.setItemCategory(CategoryEnum.valueOf(category.getSelectedValue()));

            if (itemImage.getSelectedFile() != null) {
                itemModel.setItemImage(Storage.saveFile(itemImage.getSelectedFile()));
            }

            if (ItemServices.saveItem(itemModel) != null) {
                PopupMessage.showMessage("Item updated. ", "success", () -> {
                    VendorViewModel.initMenuViewModel();
                    VendorViewModel.getMenuViewModel().navigate(VendorViewModel.getMenuViewModel().getNode());
                });
            } else {
                PopupMessage.showMessage("Failed to update item. ", "error", () -> {
                });
            }
        });
    }
}