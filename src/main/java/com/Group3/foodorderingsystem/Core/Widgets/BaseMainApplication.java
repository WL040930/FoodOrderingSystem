package com.Group3.foodorderingsystem.Core.Widgets;

import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.model.RegisterViewModel;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public abstract class BaseMainApplication extends Application {

    protected ScrollPane currentPane;
    protected BorderPane layout;

    protected abstract Header buildHeader();

    protected abstract BottomNavigation buildBottomNavigator();

    protected abstract String sceneHeader(); 

    @Override
    public void start(Stage primaryStage) {
        currentPane = new ScrollPane(AdminViewModel.getRegisterViewModel().getRegisterRoleSelection());
        currentPane.setFitToWidth(true);
        currentPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        currentPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        currentPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        layout = new BorderPane();
        layout.setTop(buildHeader());
        layout.setCenter(currentPane);
        layout.setBottom(buildBottomNavigator());

        layout.setPrefHeight(600);

        Scene scene = new Scene(layout, 500, 780);
        primaryStage.setTitle(sceneHeader());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setCurrentPane(Node pane) {
        if (currentPane.getContent() != null) {
            currentPane.setContent(null);
        }
        currentPane.setContent(pane); 
    }

    public void updateHeader() {
        layout.setTop(buildHeader());
    }

}
