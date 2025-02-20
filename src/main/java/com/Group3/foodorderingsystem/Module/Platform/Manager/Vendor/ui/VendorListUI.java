package com.Group3.foodorderingsystem.Module.Platform.Manager.Vendor.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.Card;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Database.ui.EditUserInformation;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.PopupMessage;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.DynamicSearchBarUI;
import com.Group3.foodorderingsystem.Module.Platform.Manager.ManagerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Vouchers.widgets.KButton;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class VendorListUI extends BaseContentPanel {

    public VendorListUI() {
        super();

        setHeader(header());
        setContent(content());

        setFooterHeight(0);
        setContentHeight(580);
    }

    private Node header() {
        return new TitleBackButton("Vendor List");
    }

    private Node content() {
        DynamicSearchBarUI searchBarUI = new DynamicSearchBarUI<VendorModel>(UserServices.getVendors(), "shopName",
                null, this::vendorCard);

        return searchBarUI;
    }

    private Node vendorCard(VendorModel user) {
        // Horizontal box for the main user panel
        HBox mainPanel = new HBox();
        mainPanel.setSpacing(10);

        // User profile picture
        ImageView imageView = Images.getImageView(user.getProfilePicture(), 100, 100);

        // Vertical box for user details
        VBox userDetailsBox = new VBox(10);
        Label nameLabel = new Label(user.getName());
        Label emailLabel = new Label(user.getEmail());
        Label roleLabel = new Label(user.getRole().toString());

        userDetailsBox.getChildren().addAll(nameLabel, emailLabel, roleLabel);

        // Add profile picture and details to the main panel
        mainPanel.getChildren().addAll(imageView, userDetailsBox);

        VBox mainPaneVBox = new VBox();
        mainPaneVBox.getChildren().add(mainPanel);

        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setSpacing(10);

        Button performanceButton = new KButton("Performance", () -> {
            ManagerViewModel.getVendorPerformanceViewModel().setPerformanceVendor(new PerformanceVendor(user));
            ManagerViewModel.getVendorPerformanceViewModel()
                    .navigate(ManagerViewModel.getVendorPerformanceViewModel().getPerformanceVendor());
        });

        KButton menuButton = new KButton("Menu", () -> {
            ManagerViewModel.getVendorPerformanceViewModel().setMenuVendor(new MenuVendor(user));
            ManagerViewModel.getVendorPerformanceViewModel()
                    .navigate(ManagerViewModel.getVendorPerformanceViewModel().getMenuVendor());
        });

        buttonBox.getChildren().add(menuButton);

        buttonBox.getChildren().addAll(performanceButton);
        mainPaneVBox.getChildren().add(buttonBox);

        // Create the card to hold the user panel
        Card userCard = new Card(mainPaneVBox, 470, 100, null);
        userCard.setSpacing(0);

        // Wrap the card in a centered container
        HBox cardContainer = new HBox(userCard);
        cardContainer.setAlignment(Pos.CENTER);

        return cardContainer;
    }
}
