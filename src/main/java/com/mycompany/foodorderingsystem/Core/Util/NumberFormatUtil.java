package com.mycompany.foodorderingsystem.Core.Util;

import java.text.DecimalFormat;

public class NumberFormatUtil {
    
    public static String to2Dec(double number) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(number);
    }
}
