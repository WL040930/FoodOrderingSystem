package com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class BottomButton extends Button {

        private String text;
        private Runnable action;

        public BottomButton(String text, Runnable action) {
                super();
                this.text = text;
                this.action = action;

                try {
                        String cssPath = "/com/Group3/foodorderingsystem/Module/Common/Common.css";
                        String cssResource = getClass().getResource(cssPath).toExternalForm();
                        if (cssResource != null) {
                                this.getStylesheets().add(cssResource);
                        } else {
                                System.err.println("CSS file not found: " + cssPath);
                        }
                } catch (Exception e) {
                        System.err.println("Error loading CSS: " + e.getMessage());
                }

                this.setText(this.text);
                this.setMaxWidth(Double.MAX_VALUE);

                this.getStyleClass().add("button");

                this.setOnMouseClicked(event -> this.action.run());

                this.setStyle("-fx-cursor: hand;");
        }

        // Setter for the action
        public void setAction(Runnable action) {
                this.action = action;
        }

        public void setDisabled(Boolean isDisabled) {
                if (isDisabled) {
                        this.setDisable(true);
                        this.setOpacity(0.5);
                        this.setStyle("-fx-cursor: not-allowed;");
                } else {
                        // Enable the button functionality
                        this.setDisable(false);
                        this.setOpacity(1.0); // Restore original opacity
                        this.setStyle("-fx-cursor: hand;"); // Restore cursor style
                }
        }
}
