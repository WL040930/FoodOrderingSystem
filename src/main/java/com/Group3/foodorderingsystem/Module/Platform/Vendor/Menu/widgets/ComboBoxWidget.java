package com.Group3.foodorderingsystem.Module.Platform.Vendor.Menu.widgets;

import javafx.scene.control.ComboBox;

public class ComboBoxWidget extends ComboBox<String> {

    public ComboBoxWidget(String promptText, String... items) {
        super();
        this.setPromptText(promptText);
        this.getItems().addAll(items);
        this.setPrefWidth(200); // Optional: Set a default width
        this.setStyle("-fx-background-color: #ffffff; " +
                "-fx-border-color: #cccccc; " +
                "-fx-border-radius: 5px; " +
                "-fx-padding: 5px; " +
                "-fx-font-size: 14px;");

        // Automatically select the first item if available
        if (!this.getItems().isEmpty()) {
            this.getSelectionModel().select(0);
        }
    }

    // Method to get the selected value
    public String getSelectedValue() {
        return this.getSelectionModel().getSelectedItem();
    }

    public void setSelectedValue(String value) {
        this.getSelectionModel().select(value);
    }
}
