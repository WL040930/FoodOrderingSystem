package com.Group3.foodorderingsystem.Module.Platform.Customer.Home.ui;

import java.util.ArrayList;

import com.Group3.foodorderingsystem.Core.Model.Entity.Order.ItemModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Services.ItemServices;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.BottomButton;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.DynamicSearchBarUI;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.MenuItemCard;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.VendorCard;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.VendorReviewCard;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
            Alert confirmationAlert = new Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert
                    .setContentText("Are you sure you want to navigate back? All items in cart will be removed.");

            // Check if user confirms the action
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    CustomerViewModel.getHomeViewModel().navigate(CustomerViewModel.getHomeViewModel().getHomeUI());
                    CustomerViewModel.initOrderViewModel();
                    SessionUtil.setItemsInSession(new ArrayList<>());
                }
            });
        });
    }

    private Node content() {
        VBox content = new VBox();
        content.setSpacing(20);

        VendorReviewCard vendorReviewCard = new VendorReviewCard(vendorModel, () -> {
            CustomerViewModel.getHomeViewModel().setVendorReview(new VendorReview(vendorModel.getId()));
            CustomerViewModel.getHomeViewModel().navigate(CustomerViewModel.getHomeViewModel().getVendorReview());
        });
        content.getChildren().add(vendorReviewCard.getCard());

        DynamicSearchBarUI.RenderTemplate<ItemModel> renderTemplate = new MenuItemCard();

        DynamicSearchBarUI<ItemModel> searchBarUI = new DynamicSearchBarUI<ItemModel>(
                ItemServices.getItemByVendor(vendorModel), "itemName", null, renderTemplate);
        content.getChildren().add(searchBarUI);
        return content;
    }

    private Node footer() {
        return CustomerViewModel.getHomeViewModel().getBottomButton();
    }
}
