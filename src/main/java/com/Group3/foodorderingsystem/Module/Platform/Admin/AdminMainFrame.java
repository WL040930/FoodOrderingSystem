package com.Group3.foodorderingsystem.Module.Platform.Admin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.BottomNavigationClass;
import com.Group3.foodorderingsystem.Core.Model.Entity.Config.HeaderClass;
import com.Group3.foodorderingsystem.Core.Widgets.BottomNavigation;
import com.Group3.foodorderingsystem.Core.Widgets.Header;
import com.Group3.foodorderingsystem.Module.Common.settings.SettingsPage;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Assets.AdminNavigationEnum;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Database.AdminDatabase;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.AdminRegister;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class AdminMainFrame extends Application {

    private ScrollPane currentPane;
    private BorderPane layout;

    public void setCurrentPane(Node pane) {
        if (currentPane.getContent() != null) {
            currentPane.setContent(null);
        }
        currentPane.setContent(pane);
    }

    @Override
    public void start(Stage primaryStage) {
        // Main Pane
        currentPane = new ScrollPane(AdminViewModel.getAdminRegister());
        currentPane.setFitToWidth(true);
        currentPane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        currentPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        currentPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Layout
        layout = new BorderPane();
        layout.setTop(buildHeader());
        layout.setCenter(currentPane);
        layout.setBottom(buildBottomNavigator());

        layout.setPrefHeight(600);

        Scene scene = new Scene(layout, 500, 780);
        primaryStage.setTitle("Admin Dashboard");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Node buildHeader() {
        AdminNavigationEnum navigationList = AdminViewModel.getSelectedNavigation();
        Header header = new Header(
                navigationList.getTopNavigationItem().stream()
                        .map(e -> new HeaderClass(
                                e.getTitle(),
                                AdminViewModel.getSelectedTopNavigation() == e,
                                () -> {
                                    AdminViewModel.setSelectedTopNavigation(e);

                                    layout.setTop(buildHeader());

                                    e.getAction().run();
                                }))
                        .collect(Collectors.toList()));

        return header;
    }

    private Node buildBottomNavigator() {
        List<BottomNavigationClass> config = new ArrayList<>();

        for (AdminNavigationEnum component : AdminViewModel.getNavigationList()) {
            config.add(new BottomNavigationClass(
                    component.getTitle(),
                    component.getIcon(),
                    () -> handleNavigation(component),
                    AdminViewModel.getSelectedNavigation() == component));
        }

        BottomNavigation bottomNavigation = new BottomNavigation(config);
        bottomNavigation.setStyle(
                "-fx-background-color: #4CAF50; -fx-padding: 10px; -fx-min-height: 80px; -fx-max-height: 80px;");
        return bottomNavigation;
    }

    private void handleNavigation(AdminNavigationEnum selectedComponent) {
        AdminViewModel.setSelectedNavigation(selectedComponent);

        AdminViewModel.reset(selectedComponent);

        layout.setBottom(buildBottomNavigator());
        layout.setTop(buildHeader());
    }

    public void updateHeader() {
        layout.setTop(buildHeader());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
