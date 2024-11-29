package com.Group3.foodorderingsystem.Core.Util;

import java.text.DecimalFormat;

public class NumberFormatUtil {
    
    public static String to2Dec(double number) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(number);
    }

    public static String toC2Dec(double number) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(number);
    }
}
