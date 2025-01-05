package com.Group3.foodorderingsystem.Module.Platform.Customer.Home.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.CategoryEnum;
import com.Group3.foodorderingsystem.Core.Services.ItemServices;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.VendorSmallCard;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShopSelection extends VBox {

    public ShopSelection() {
        super();
        init();
    }

    public void init() {
        List<VendorModel> vendors = UserServices.getVendors();
        Map<CategoryEnum, List<VendorModel>> data = ItemServices.filterVendorItem();

        List<VendorModel> foodVendors = vendors.stream()
                .filter(vendor -> data.get(CategoryEnum.FOOD).stream().anyMatch(v -> v.getId().equals(vendor.getId())))
                .collect(Collectors.toList());

        List<VendorModel> drinkVendors = vendors.stream()
                .filter(vendor -> data.get(CategoryEnum.DRINK).stream().anyMatch(v -> v.getId().equals(vendor.getId())))
                .collect(Collectors.toList());

        Node foodSection = createVendorSection("Food Seller", foodVendors);
        Node drinkSection = createVendorSection("Drink Seller", drinkVendors);

        this.setSpacing(5);

        this.getChildren().addAll(foodSection, drinkSection);
    }

    private Node createVendorSection(String title, List<VendorModel> vendors) {
        VBox section = new VBox();

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-padding: 10px 0px 0px 10px;");
        section.getChildren().add(titleLabel);

        HBox vendorCards = new HBox(3);
        vendorCards.setStyle("-fx-padding: 0px 10px 10px 10px; -fx-hgap: 20px;");

        vendors.forEach(vendor -> {
            vendorCards.getChildren().add(new VendorSmallCard(vendor));
            vendorCards.getChildren().add(new Label(" "));
        });

        ScrollPane scrollPane = new ScrollPane(vendorCards);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setMinWidth(500);

        scrollPane.setStyle("-fx-background-color:transparent;");

        scrollPane.setMinHeight(217);
        scrollPane.setMaxHeight(217);

        HBox container = new HBox(scrollPane);
        container.setSpacing(20);

        section.getChildren().add(container);
        return section;
    }
}
