package com.Group3.foodorderingsystem.Module.Common.Transaction.Widgets;

import javafx.scene.control.Separator;

public class KSeparator extends Separator {

    public KSeparator() {
        super();

        this.getStylesheets().add(getClass()
                .getResource("/com/Group3/foodorderingsystem/Module/Common/Common.css")
                .toExternalForm());

        this.getStyleClass().add("separator");
    }
    
}
