package com.mycompany.foodorderingsystem.Core.Storage;

import java.util.ArrayList;

public class StorageConstant {
    
    static final String SEPERATOR = " ,;;||;;, ";

    static String toRow (Iterable<String> values) {
        StringBuilder row = new StringBuilder();
        for (String value : values) {
            row.append(value);
            row.append(SEPERATOR);
        }
        return row.toString();
    }

    ArrayList<String> toList (String row) {
        ArrayList<String> values = new ArrayList<>();
        String[] split = row.split(SEPERATOR);
        for (String value : split) {
            values.add(value);
        }
        return values;
    }
}
