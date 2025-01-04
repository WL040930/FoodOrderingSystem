package com.Group3.foodorderingsystem.Core.Widgets;

import com.Group3.foodorderingsystem.Core.Util.Images;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class TitleBackButton extends HBox {

    public TitleBackButton(String title) {
        init(title);
    }

    public TitleBackButton(String title, Runnable action) {
        init(title, action);
    }

    public void init(String title) {
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-padding: 10px; -fx-font-weight: bold; -fx-text-fill:rgb(0, 0, 0);");

        this.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().addAll(titleLabel);
    }
 
    public void init(String title, Runnable action) {
        Label backLabel = new Label();
        ImageView backIcon = Images.getImageView("left_arrow.png", 30, 30);
        backLabel.setGraphic(backIcon);
        backLabel.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        backLabel.setCursor(Cursor.HAND);

        backLabel.setOnMouseClicked(event -> {
            action.run();
        });

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill:rgb(0, 0, 0);");

        this.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().addAll(backLabel, titleLabel);
    }
}
