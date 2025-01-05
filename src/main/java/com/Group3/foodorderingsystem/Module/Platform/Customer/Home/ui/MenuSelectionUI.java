package com.Group3.foodorderingsystem.Module.Platform.Customer.Home.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Services.ItemServices;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.DynamicSearchBarUI;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.MenuItemCard;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.VendorCard;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.VendorReviewCard;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MenuSelectionUI extends BaseContentPanel {

    private VendorModel vendorModel;

    public MenuSelectionUI(String vendorId) {
        super();
        this.vendorModel = UserServices.findVendorById(vendorId);

        setHeader(header());
        setContent(content());
        setFooter(footer());
    }

    private Node header() {
        return new TitleBackButton(vendorModel.getShopName(), () -> {
            // Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
            // confirmationAlert.setTitle("Confirmation");
            // confirmationAlert.setHeaderText(null);
            // confirmationAlert.setContentText("Are you sure you want to navigate back?");

            // // Check if user confirms the action
            // confirmationAlert.showAndWait().ifPresent(response -> {
            // if (response == ButtonType.OK) {
            CustomerViewModel.getHomeViewModel().navigate(CustomerViewModel.getHomeViewModel().getHomeUI());
            SessionUtil.setItemsInSession(null);
            // }
            // });
        });
    }

    private Node content() {
        VBox content = new VBox();
        content.setSpacing(20);

        VendorReviewCard vendorReviewCard = new VendorReviewCard(vendorModel);
        content.getChildren().add(vendorReviewCard.getCard());

        DynamicSearchBarUI.RenderTemplate<ItemModel> renderTemplate = new MenuItemCard();

        DynamicSearchBarUI<ItemModel> searchBarUI = new DynamicSearchBarUI<ItemModel>(
                ItemServices.getItemByVendor(vendorModel), "itemName", null, renderTemplate);
        content.getChildren().add(searchBarUI);
        return content;
    }

    private Node footer() {
        return new Label("Footer");
    }
}
