package com.Group3.foodorderingsystem.Module.Platform.Admin;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

public class AdminMainFrame extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the header
        Label header = new Label("Admin Dashboard");
        header.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        header.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px;");

        // Create some content for the middle (this will be scrollable)
        VBox middleContent = new VBox(20);
        middleContent.setAlignment(Pos.CENTER);
        middleContent.setStyle("-fx-padding: 20px; -fx-background-color: #f0f0f0;");

        // Add some sample content to middleContent (You can replace this with your
        // actual content)
        for (int i = 0; i < 20; i++) {
            Button contentItem = new Button("Content Item " + (i + 1));
            contentItem.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
            middleContent.getChildren().add(contentItem);
        }

        // Wrap the middle content in a ScrollPane to make it scrollable
        ScrollPane scrollView = new ScrollPane();
        scrollView.setContent(middleContent);
        scrollView.setFitToWidth(true); // Make the content fit to width

        // Create the bottom navigator (a simple HBox with buttons)
        HBox bottomNavigator = new HBox(15);
        bottomNavigator.setAlignment(Pos.CENTER);
        bottomNavigator.setStyle("-fx-background-color: #333; -fx-padding: 10px;");

        Button homeButton = new Button("Home");
        Button settingsButton = new Button("Settings");
        Button logoutButton = new Button("Logout");

        // Add buttons to the bottom navigator
        bottomNavigator.getChildren().addAll(homeButton, settingsButton, logoutButton);

        // Create the main layout using BorderPane
        BorderPane layout = new BorderPane();
        layout.setTop(header);
        layout.setCenter(scrollView);
        layout.setBottom(bottomNavigator);

        // Set fixed height for header and bottom navigator
        layout.setPrefHeight(600);
        layout.setTop(header);
        layout.setBottom(bottomNavigator);

        // Create and set the scene
        Scene scene = new Scene(layout, 500, 780);
        primaryStage.setTitle("Admin Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
