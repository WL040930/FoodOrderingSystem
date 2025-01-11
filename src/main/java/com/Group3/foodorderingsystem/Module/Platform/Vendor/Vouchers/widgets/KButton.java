package com.Group3.foodorderingsystem.Module.Platform.Vendor.Vouchers.widgets;

import javafx.scene.control.Button;

public class KButton extends Button {

    private String text;
    private Runnable action;

    public KButton(String text, Runnable action) {
        this.text = text;
        this.action = action;
        init();
    }

    private void init() {
        this.getStylesheets().add(
                getClass().getResource("/com/Group3/foodorderingsystem/Module/Common/Common.css").toExternalForm());
        this.getStyleClass().add("small-button");
        this.setText(text);
        this.setOnAction(event -> {
            action.run();
        });
    }

    public static String red = "#FF0000";
    public static String green = "#32CD32";

    /**
     * Sets the background color of the button.
     * 
     * @param color the desired background color in CSS format (e.g., "#FF0000" for
     *              red)
     */
    public void setBackgroundColor(String color) {
        if (color != null && !color.isEmpty()) {
            this.setStyle("-fx-background-color: " + color + ";");
        }
    }
}
