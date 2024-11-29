package com.Group3.foodorderingsystem.Core.Storage;

import java.io.File;
import java.io.IOException;

public class Storage {

    final static String DIRECTORY_PATH = "src/main/java/com/Group3/foodorderingsystem/Assets/Data";
    
    public static void init() {
        try {
            File directory = new File(DIRECTORY_PATH);
            if (!directory.exists()) {
                directory.mkdirs();
                System.out.println("Directory created: " + DIRECTORY_PATH);
            }

            for (StorageEnum storageEnum : StorageEnum.values()) {
                File file = new File(DIRECTORY_PATH + "/" + storageEnum.name().toLowerCase() + ".txt");
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getAbsolutePath());
                } else {
                    System.out.println("File already exists: " + file.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while initializing the storage: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
       init();
    }
}
