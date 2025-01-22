package com.Group3.foodorderingsystem.Module.Platform.Vendor.Review.ui;

import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class ReviewUI extends BaseContentPanel {
    
    public ReviewUI() {
        super(); 

        setHeader(header());
        setContent(content());

        setContentHeight(580);
        setFooterHeight(0);
    }

    private Node header() {
        return new TitleBackButton("Review"); 
    }

    private Node content() {
        return new VBox();
    }
}
