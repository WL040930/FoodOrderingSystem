package com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class BottomButton extends Button {

        public BottomButton(String text, Runnable action) {
                super();

                try {
                        String cssPath = "/com/Group3/foodorderingsystem/Module/Common/HeaderCSS.css";
                        String cssResource = getClass().getResource(cssPath).toExternalForm();
                        if (cssResource != null) {
                                this.getStylesheets().add(cssResource);
                        } else {
                                System.err.println("CSS file not found: " + cssPath);
                        }
                } catch (Exception e) {
                        System.err.println("Error loading CSS: " + e.getMessage());
                }

                this.setText(text);
                this.setMaxWidth(Double.MAX_VALUE);

                this.getStyleClass().add("selected-button");

                this.setOnMouseClicked(event -> action.run());

                this.setStyle("-fx-cursor: hand;"); 

        }

}
