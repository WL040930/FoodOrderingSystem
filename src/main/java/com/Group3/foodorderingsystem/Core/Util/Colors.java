package com.Group3.foodorderingsystem.Core.Util;

import java.util.Map;

import com.Group3.foodorderingsystem.Module.SetupViewModel;

public class Colors {
    
    public static String black = "\u001B[30m";
    public static String white = "\u001B[37m";
    public static String lightBlue = "\u001B[34m";

    static Map<String, String> colors = Map.of(
        "black", black,
        "white", white,
        "lightBlue", lightBlue
    );

    public static String getColor(String color) {
        SetupViewModel setupViewModel = new SetupViewModel();
        if (setupViewModel.appearance.isDarkMode()) {
            return colors.get(color);
        }
        return color;
    }
}
