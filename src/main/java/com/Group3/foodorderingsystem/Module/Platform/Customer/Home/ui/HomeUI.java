package com.Group3.foodorderingsystem.Module.Platform.Customer.Home.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.DynamicSearchBarUI;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.VendorCard;

import javafx.scene.Node;

import java.util.List;

public class HomeUI extends BaseContentPanel {

    public HomeUI() {
        super();
        setHeader(new TitleBackButton("Home"));
        setContent(content());

        setContentHeight(580);
        setFooterHeight(0);
    }

    private Node content() {
        List<VendorModel> vendor = UserServices.getVendors().stream().filter(v -> !v.isDeleted())
                .collect(java.util.stream.Collectors.toList());

        DynamicSearchBarUI.RenderTemplate<VendorModel> renderTemplate = item -> {
            return new VendorCard().render(item);
        };

        DynamicSearchBarUI<VendorModel> searchWidget = new DynamicSearchBarUI<>(
                vendor, "shopName", CustomerViewModel.getHomeViewModel().getShopSelection(), renderTemplate);

        return searchWidget;
    }
}
