package com.Group3.foodorderingsystem.Module.Common.Login;

import com.Group3.foodorderingsystem.Core.Util.Images;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20); // Add spacing between components
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20)); // Add padding around the layout

        root.getChildren().addAll(_buildLogo(), _buildFormField(), _buildLoginButton());

        Scene scene = new Scene(root, 500, 780);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Node _buildLogo() {
        return Images.getImageView("logo.png", 200, 200);
    }

    private Node _buildFormField() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        return grid;
    }

    private Node _buildLoginButton() {
        VBox buttonContainer = new VBox(10);
        buttonContainer.setAlignment(Pos.CENTER); // Align the button to the center
        buttonContainer.setPadding(new Insets(10));

        Button btn = new Button("Sign in");

        final Text actiontarget = new Text();
        actiontarget.setId("actiontarget");

        btn.setOnAction(e -> {
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("Sign in button pressed");
        });

        buttonContainer.getChildren().addAll(btn, actiontarget); // Add the button and action message
        return buttonContainer;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
