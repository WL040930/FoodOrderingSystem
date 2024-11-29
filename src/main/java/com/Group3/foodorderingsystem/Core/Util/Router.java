package com.Group3.foodorderingsystem.Core.Util;

import java.awt.Component;

public class Router {
    
    public static void navigate(Component parentComponent, Component destination) {
        int x = destination.getLocation().x;
        int y = destination.getLocation().y;

        int xOffset = parentComponent.getLocation().x - x;
        int yOffset = parentComponent.getLocation().y - y;
        x += xOffset;
        y += yOffset;

        destination.setLocation(x, y);
        destination.setVisible(true);

        if (parentComponent instanceof java.awt.Window) {
            ((java.awt.Window) parentComponent).dispose();
        }
    }
}
