package com.Group3.foodorderingsystem.Module.Platform.Admin.Finance.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.BottomButton;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class AdminTopUpUI extends BaseContentPanel {

    public AdminTopUpUI(CustomerModel customer) {
        super();

        setHeader(header());
        setContent(content());
        setFooter(footer());
    }

    private Node header() {
        return new TitleBackButton("Top Up", () -> {
            AdminViewModel.getFinanceViewModel()
                    .navigate(AdminViewModel.getFinanceViewModel().getFinanceCustomerListUI());
        });
    }

    private Node content() {
        return new VBox();
    }

    private Node footer() {
        return new BottomButton("Top Up", () -> {
        });
    }
}
