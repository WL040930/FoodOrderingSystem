package com.Group3.foodorderingsystem.Module.Platform.Admin.Finance.model;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.ViewModelConfig;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Finance.ui.FinanceCustomerListUI;

import javafx.scene.Node;

public class FinanceViewModel extends ViewModelConfig {

    @Override
    public void navigate(Node node) {
        setNode(node);
        AdminViewModel.navigate(node);
    } 

    public FinanceViewModel() {
        super();
    }

    public void init() {
        financeCustomerListUI = new FinanceCustomerListUI();
        setNode(financeCustomerListUI);
    }

    private FinanceCustomerListUI financeCustomerListUI;

    public FinanceCustomerListUI getFinanceCustomerListUI() {
        if (financeCustomerListUI == null) {
            financeCustomerListUI = new FinanceCustomerListUI();
        }
        return financeCustomerListUI;
    }
}
