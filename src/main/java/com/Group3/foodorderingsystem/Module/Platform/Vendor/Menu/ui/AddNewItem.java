package com.Group3.foodorderingsystem.Module.Platform.Vendor.Menu.ui;

import java.io.File;
import java.util.Arrays;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.CategoryEnum;
import com.Group3.foodorderingsystem.Core.Services.ItemServices;
import com.Group3.foodorderingsystem.Core.Storage.Storage;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
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

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AddNewItem extends BaseContentPanel {

    private TitleTextField itemName;
    private TitleTextField itemDescription;
    private TitleTextField itemPrice;
    private ComboBoxWidget category;
    private FileUploadWidget itemImage;

    private Label errorLabel;

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
        VBox content = new VBox(10);
        content.setStyle("-fx-padding: 10px;");

        itemName = new TitleTextField("Name of Food/Drinks", "Enter the name of food/drink",
                TitleTextFieldEnum.TextField);
        itemDescription = new TitleTextField("Description", "Enter the description of food/drink",
                TitleTextFieldEnum.TextArea);
        itemPrice = new TitleTextField("Price", "Enter the price of food/drink (RM)", TitleTextFieldEnum.DecimalField);
        category = new ComboBoxWidget("Category",
                Arrays.stream(CategoryEnum.values())
                        .map(CategoryEnum::toString)
                        .toArray(String[]::new));

        itemImage = new FileUploadWidget(FileUploadType.CIRCLE_AVATAR, "logo.png");

        errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        content.getChildren().addAll(itemName, itemDescription, itemPrice, category, itemImage,
                errorLabel);

        return content;
    }

    public Node footer() {
        return new BottomButton("Create", () -> {
            String name = itemName.getInputValue();
            String description = itemDescription.getInputValue();
            String price = itemPrice.getInputValue();
            String category = this.category.getSelectedValue();
            File image = itemImage.getSelectedFile();

            if (name == "") {
                errorLabel.setText("Name is required");
            } else if (description == "") {
                errorLabel.setText("Description is required");
            } else if (price == "") {
                errorLabel.setText("Price is required");
            }  else {
                errorLabel.setText("");

                ItemModel item = new ItemModel();
                item.setItemName(name);
                item.setItemDescription(description);
                item.setItemPrice(Double.parseDouble(price));
                item.setItemCategory(CategoryEnum.valueOf(category));

                if (image != null) {
                    item.setItemImage(Storage.saveFile(image));
                } else {
                    item.setItemImage("logo.png");
                }

                item.setItemQuantity(0);

                Object vendor = SessionUtil.getVendorFromSession();
                if (vendor instanceof VendorModel) {
                    item.setVendorModel((VendorModel) vendor);
                } else {
                    PopupMessage.showMessage("An Error Occured. Please Try again", "error", () -> {
                    });
                }

                ItemModel savedItem = ItemServices.saveItem(item);

                if (savedItem != null) {
                    PopupMessage.showMessage("Item has been created successfully", "success", () -> {
                        VendorViewModel.initMenuViewModel();
                        VendorViewModel.getMenuViewModel().navigate(VendorViewModel.getMenuViewModel().getNode());
                    });
                } else {
                    PopupMessage.showMessage("An Error Occured. Please Try again", "error", () -> {
                    });
                }
            }
        });
    }
}
