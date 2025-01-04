package com.Group3.foodorderingsystem.Module.Platform.Vendor.Menu.ui;

import com.Group3.foodorderingsystem.Core.Util.Images;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class SearchBarWithData<T> extends HBox {

    private TextField searchField;
    private Button searchButton;
    private List<T> objectList;
    private ObjectDisplayTemplate<T> displayTemplate;
    private Class<T> type;
    private String searchFieldName;

    // Constructor accepting Class<T>, objectList, ObjectDisplayTemplate, and the
    // field name to search
    public SearchBarWithData(List<T> objectList, Class<T> type, ObjectDisplayTemplate<T> displayTemplate,
            String searchFieldName) {
        this.objectList = objectList;
        this.type = type;
        this.displayTemplate = displayTemplate;
        this.searchFieldName = searchFieldName;

        // Create the search input field
        searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.setStyle("-fx-border-radius: 20px; -fx-font-size: 14px;");

        // Adding padding inside the TextField (top, right, bottom, left)
        searchField.setStyle(
                "-fx-background-color: #f8fafc; -fx-border-color: #d9eafc; -fx-border-width: 1px; -fx-padding: 5px 15px;");

        // Allow the TextField to grow to max width
        searchField.setMaxWidth(Double.MAX_VALUE);

        // Create the search button with an icon inside the TextField
        searchButton = new Button();
        ImageView searchIconView = Images.getImageView("search.png", 20, 20);
        searchButton.setGraphic(searchIconView);
        searchButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        // Set action when button is clicked
        searchButton.setOnMouseClicked((MouseEvent e) -> {
            performSearch();
        });

        // Make the search icon button appear inside the TextField container, aligned to
        // the right
        searchButton.setPrefWidth(40); // Adjust width of the button
        searchButton.setMinWidth(40);

        // Add the TextField and Button to the HBox
        this.setSpacing(0); // No space between TextField and button
        this.getChildren().addAll(searchField, searchButton);

        // Allow the searchField to expand while the button stays fixed
        HBox.setHgrow(searchField, Priority.ALWAYS);

        // Styling HBox to ensure the button is inside the text field and aligned
        // properly
        this.setStyle("-fx-padding: 10px;");
        this.setPrefHeight(40); // Ensure a consistent height
        this.setMinWidth(250); // Define a minimum width for the HBox to prevent resizing issues

        // Display all items initially
        displayTemplate.updateDisplay(objectList);

        // Add a listener to the searchField to update results in real-time
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            performSearch(); // Trigger search action when text changes
        });
    }

    // Example search action using reflection based on Class<T>
    private void performSearch() {
        String query = searchField.getText();
        List<T> filteredList = objectList.stream()
                .filter(item -> {
                    // Filter based on the type of object and reflection (searching a specified
                    // field)
                    try {
                        Field field = type.getDeclaredField(searchFieldName);
                        field.setAccessible(true);
                        String fieldValue = (String) field.get(item); // Assuming the field is of type String
                        return fieldValue != null && fieldValue.toLowerCase().contains(query.toLowerCase());
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        // Handle reflection errors
                        e.printStackTrace();
                    }
                    return false;
                })
                .collect(Collectors.toList());

        displayTemplate.updateDisplay(filteredList); // Update display with filtered list
    }

    // Example callback template interface for displaying filtered results
    public interface ObjectDisplayTemplate<T> {
        void updateDisplay(List<T> filteredList); // Method to update the UI with filtered results
    }
}
