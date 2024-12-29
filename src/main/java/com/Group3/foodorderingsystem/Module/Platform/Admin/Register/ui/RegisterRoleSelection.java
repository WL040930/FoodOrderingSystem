package com.Group3.foodorderingsystem.Module.Platform.Admin.Register.ui;

import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;

public class RegisterRoleSelection extends VBox {

    public RegisterRoleSelection() {

        init();
    }

    public void init() {
        this.setSpacing(20); // Add some spacing between components
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #f9f9f9; -fx-padding: 20px;");

        // Add all components to the layout
        this.getChildren().addAll(buildTitle(), showRoleSelectionModel(), showRoleDetails(), buildActionButton());
    }

    private Node buildTitle() {
        return new TitleBackButton("Choose The Role To Register");
    }

    private Node showRoleSelectionModel() {
        HBox roleSelectionBox = new HBox(20);
        roleSelectionBox.setAlignment(Pos.CENTER);

        ImageView leftArrow = Images.getImageView("left_arrow.png", 50, 50);
        leftArrow.setStyle("-fx-padding: 10px; -fx-border-radius: 5px; -fx-cursor: hand;");
        leftArrow.setOnMouseClicked(event -> {
            AdminViewModel.getRegisterViewModel().setSelectedRole(-1);
            refreshRoleSelection();
        });

        ImageView roleImage = Images.getImageView(AdminViewModel.getRegisterViewModel().getSelectedRole().getImage(),
                230, 230);

        ImageView rightArrow = Images.getImageView("right_arrow.png", 50, 50);
        rightArrow.setStyle("-fx-padding: 10px; -fx-border-radius: 5px; -fx-cursor: hand;");
        rightArrow.setOnMouseClicked(event -> {
            AdminViewModel.getRegisterViewModel().setSelectedRole(1);
            refreshRoleSelection();
        });

        roleSelectionBox.getChildren().addAll(leftArrow, roleImage, rightArrow);

        return roleSelectionBox;
    }

    private Node showRoleDetails() {
        VBox roleDetailsBox = new VBox(15); // Increased spacing
        roleDetailsBox.setAlignment(Pos.CENTER);

        Label roleName = new Label(AdminViewModel.getRegisterViewModel().getSelectedRole().getRole());
        roleName.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Label roleDescription = new Label(AdminViewModel.getRegisterViewModel().getSelectedRole().getDescription());
        roleDescription.setStyle("-fx-font-size: 16px; -fx-text-fill: #666;");
        roleDescription.setWrapText(true);
        roleDescription.setMaxWidth(400);
        roleDescription.setAlignment(Pos.CENTER);

        roleDetailsBox.getChildren().addAll(roleName, new Region(), roleDescription);

        return roleDetailsBox;
    }

    private Node buildActionButton() {
        Button actionButton = new Button("Register");
        actionButton.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 5px;");
        actionButton.setOnMouseClicked(event -> handleRegisterAction());

        actionButton.setOnMouseEntered((MouseEvent e) -> actionButton.setStyle(
                "-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 5px;"));
        actionButton.setOnMouseExited((MouseEvent e) -> actionButton.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 5px;"));

        return actionButton;
    }

    private void handleRegisterAction() {
        System.out.println("Register button clicked");
    }

    private void refreshRoleSelection() {
        Platform.runLater(() -> {
            this.getChildren().clear();
            this.getChildren().addAll(buildTitle(), showRoleSelectionModel(), showRoleDetails(), buildActionButton());
        });
    }
}
