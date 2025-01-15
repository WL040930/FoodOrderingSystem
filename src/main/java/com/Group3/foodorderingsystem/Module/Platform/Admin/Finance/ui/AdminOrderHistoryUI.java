package com.Group3.foodorderingsystem.Module.Platform.Admin.Finance.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class AdminOrderHistoryUI extends BaseContentPanel {

    public AdminOrderHistoryUI(CustomerModel customer) {
        super();

        setHeader(header());
        setContent(content());

        setContentHeight(570);
    }

    private Node header() {
        return new TitleBackButton("Order History", () -> {
            AdminViewModel.getFinanceViewModel()
                    .navigate(AdminViewModel.getFinanceViewModel().getFinanceCustomerListUI());
        });
    }

    private Node content() {
        return new VBox();
    }
}
