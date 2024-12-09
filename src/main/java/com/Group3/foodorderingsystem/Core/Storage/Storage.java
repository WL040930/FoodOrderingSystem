package com.Group3.foodorderingsystem.Core.Storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class Storage {

    final static String DIRECTORY_PATH = "src/main/java/com/Group3/foodorderingsystem/Assets/Data";
    final static String RESOURCE_PATH = "src/main/java/com/Group3/foodorderingsystem/Assets/Resource";

    public static void init() {
        try {
            File directory = new File(DIRECTORY_PATH);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            for (StorageEnum storageEnum : StorageEnum.values()) {
                File file = new File(DIRECTORY_PATH + "/" + storageEnum.name().toLowerCase() + ".txt");
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("An error occurred while initializing the storage: " + e.getMessage());
        }
    }

    public static String saveFile(File sourceFile) {
        String fileName = sourceFile.getName();
        String uniqueName = generateUniqueName(fileName);

        try {
            Path sourcePath = sourceFile.toPath();
            Path destinationPath = Path.of(RESOURCE_PATH, uniqueName);

            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            return uniqueName;
        } catch (IOException e) {
            System.err.println("Error moving file: " + e.getMessage());
            return null;
        }
    }

    static String generateUniqueName(String originalFileName) {
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uniqueID = UUID.randomUUID().toString();
        return uniqueID + fileExtension;
    }

}
