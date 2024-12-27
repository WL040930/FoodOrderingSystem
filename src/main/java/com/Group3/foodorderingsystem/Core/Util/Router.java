package com.Group3.foodorderingsystem.Core.Util;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Router {

    /**
     * Navigates by replacing the content in the parent pane.
     *
     * @param parentPane      The parent pane that holds the content (VBox,
     *                        BorderPane, etc.).
     * @param destinationPane The pane to display as the new content.
     */
    public static void navigate(Pane parentPane, Pane destinationPane) {
        // Clear the existing content and add the new content
        parentPane.getChildren().clear();
        parentPane.getChildren().add(destinationPane);
    }
}
