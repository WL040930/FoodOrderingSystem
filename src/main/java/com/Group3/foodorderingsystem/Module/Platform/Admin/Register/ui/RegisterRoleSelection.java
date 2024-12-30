package com.Group3.foodorderingsystem.Module.Platform.Admin.Register.ui;

import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.BottomButton;

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

public class RegisterRoleSelection extends BaseContentPanel {

    public RegisterRoleSelection() {
        super();
        setHeader(header());
        setContent(content());
        setFooter(footer());
    }

    protected Node header() {
        return new TitleBackButton("Choose The Role To Register");
    }

    protected Node footer() {
        return new BottomButton("Next", this::handleRegisterAction);
    }

    protected Node content() {
        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setStyle("-fx-background-color: #f9f9f9; -fx-padding: 20px;");

        contentBox.getChildren().addAll(buildRoleSelectionModel(), buildRoleDetails());

        return contentBox;
    }

    private Node buildRoleSelectionModel() {
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

    private Node buildRoleDetails() {
        VBox roleDetailsBox = new VBox(15);
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

    private void handleRegisterAction() {
        AdminViewModel.getRegisterViewModel().setBasicInfoForm(new BasicInfoForm());
        AdminViewModel.getRegisterViewModel().navigate(AdminViewModel.getRegisterViewModel().getBasicInfoForm());
    }

    private void refreshRoleSelection() {
        Platform.runLater(() -> {
            setContent(content());
        });
    }
}