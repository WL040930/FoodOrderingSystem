package com.Group3.foodorderingsystem.Module.Platform.Admin.Finance.ui;

import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;

import javafx.scene.Node;

public class FinanceCustomerListUI extends BaseContentPanel {

    public FinanceCustomerListUI() {
        super(); 
        
        setHeader(header());
    }

    private Node header() {
        return new TitleBackButton("Finance Customer List");
    }
    
}
