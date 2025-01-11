package com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets;

import com.Group3.foodorderingsystem.Core.Util.Images;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class PopupMessage {

    public static void showMessage(String message, String type, Runnable onCloseCallback) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Message");
        stage.setResizable(false);

        VBox vbox = new VBox(15);
        vbox.setStyle("-fx-padding: 20px; -fx-alignment: center; -fx-background-color: white;");

        ImageView icon = new ImageView();
        if (type.equals("success")) {
            icon = Images.getImageView("success_icon.png", 100, 100);
        } else if (type.equals("error")) {
            icon = Images.getImageView("error_icon.png", 100, 100);
        }
        icon.setFitWidth(50);
        icon.setFitHeight(50);

        Text messageText = new Text(message);
        messageText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");
        messageText.setWrappingWidth(250); // Set the width after which the text should wrap

        Button closeButton = new Button("OK");
        closeButton.setStyle("-fx-font-size: 14px; -fx-background-color: #0078D7; -fx-text-fill: white;");
        closeButton.setOnAction(e -> {
            stage.close();
            if (onCloseCallback != null) {
                onCloseCallback.run();
            }
        });

        vbox.getChildren().addAll(icon, messageText, closeButton);

        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);

        stage.setOnCloseRequest(e -> {
            if (onCloseCallback != null) {
                onCloseCallback.run();
            }
        });

        stage.showAndWait();
    }
}
