package com.Group3.foodorderingsystem.Module.Platform.Admin.Finance.widgets;

import javafx.scene.control.Button;

public class DynamicButton extends Button {

    private boolean isSelected;
    private String value; // Add a value field to store the button value

    public DynamicButton(String title, Runnable action, Boolean isSelected, String value) {
        super(title);
        this.isSelected = isSelected;
        this.value = value; // Set the value when the button is created
        this.setPrefSize(120, 50);

        // Load the stylesheets
        this.getStylesheets().add(getClass()
                .getResource("/com/Group3/foodorderingsystem/Module/Common/HeaderCSS.css")
                .toExternalForm());

        // Apply initial style based on the isSelected state
        updateStyle();

        // Set action for button
        this.setOnAction(event -> {
            action.run();
        });
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        updateStyle();
    }

    private void updateStyle() {
        // Clear existing styles
        this.getStyleClass().removeAll("selected-button", "default-button");

        // Apply the appropriate style
        if (isSelected) {
            this.getStyleClass().add("selected-button");
        } else {
            this.getStyleClass().add("default-button");
        }
    }

    public String getValue() {
        return value; // Get the stored value
    }

    public void setValue(String value) {
        this.value = value; // Set a new value if needed
    }
}
