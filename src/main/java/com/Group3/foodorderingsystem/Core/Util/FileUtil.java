package com.Group3.foodorderingsystem.Core.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FileUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    // Save a list of object to a file
    public static <T> void saveFile(String filepath, List<T> list) {
        try {

            // clear the file first before save
            Files.write(
                    Paths.get(filepath),
                    new byte[0], 
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);

            // write the obj into the file
            for (T object : list) {
                String json = objectMapper.writeValueAsString(object) + System.lineSeparator();

                Files.write(
                        Paths.get(filepath),
                        json.getBytes(),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load a list of objects from a file
    public static <T> List<T> loadFile(String filePath, Class<T> type) {
        List<T> objects = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                T object = objectMapper.readValue(line, type);
                objects.add(object);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return objects;
    }

    // Delete a record from a file
    public static <T> void deleteRecord(String filePath, Class<T> type, Predicate<T> predicate) {
        List<T> allData = loadFile(filePath, type);
        allData.removeIf(predicate);

        // Clear the file
        try {
            new PrintWriter(filePath).close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save the modified list back to the file
        saveFile(filePath, allData);
    }


    // get model list through selected field
    public static <T> List<T> getModelByField(String filePath, Class<T> type, Predicate<T> predicate) {
        List<T> allData = loadFile(filePath, type);
        List<T> result = new ArrayList<>();
        for (T data : allData) {
            if (predicate.test(data)) {
                result.add(data);
            }
        }
        return result;
    }
    


}
