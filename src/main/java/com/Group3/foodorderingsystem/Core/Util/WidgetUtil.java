package com.Group3.foodorderingsystem.Core.Util;

import javax.swing.JScrollPane;

public class WidgetUtil {

    public static JScrollPane toEmptyPane(JScrollPane pane) {
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        pane.setBorder(null);
        return pane;
    }
}
