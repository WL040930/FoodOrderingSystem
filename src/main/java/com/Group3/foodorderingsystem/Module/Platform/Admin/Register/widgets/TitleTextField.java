package com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets;

import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class TitleTextField extends VBox {

    private final String title;
    private final String promptText;
    private final TitleTextFieldEnum fieldType;
    private javafx.scene.Node inputField; // Hold reference to the input field

    public TitleTextField(String title, String promptText, TitleTextFieldEnum fieldType) {
        super();
        this.title = title;
        this.promptText = promptText;
        this.fieldType = fieldType;
        init();
    }

    private void init() {
        // Create the label
        Label titleLabel = new Label(title);

        // Create the appropriate input field based on the fieldType
        inputField = createInputField();

        // Add the label and input field to the VBox
        this.getChildren().addAll(titleLabel, inputField);
    }

    private javafx.scene.Node createInputField() {
        switch (fieldType) {
            case PasswordField:
                PasswordField passwordField = new PasswordField();
                passwordField.setPromptText(promptText);
                return passwordField;
            case TextArea:
                TextArea textArea = new TextArea();
                textArea.setPromptText(promptText);
                return textArea;
            case IntegerField:
                // Custom TextField for integers
                TextField integerField = new TextField();
                integerField.setPromptText(promptText);
                integerField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        integerField.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });
                return integerField;
            case DecimalField:
                // Custom TextField for decimal numbers
                TextField decimalField = new TextField();
                decimalField.setPromptText(promptText);
                decimalField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*(\\.\\d*)?")) {
                        decimalField.setText(newValue.replaceAll("[^\\d.]", ""));
                    }
                });
                return decimalField;
            case EmailField:
                TextField emailField = new TextField();
                emailField.setPromptText(promptText);
                emailField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
                        emailField.setStyle("-fx-border-color: red;");
                    } else {
                        emailField.setStyle(null);
                    }
                });
                return emailField;
            case TextField:
            default:
                TextField textField = new TextField();
                textField.setPromptText(promptText);
                return textField;
        }
    }

    public String getInputValue() {
        if (inputField instanceof TextField) {
            return ((TextField) inputField).getText();
        } else if (inputField instanceof PasswordField) {
            return ((PasswordField) inputField).getText();
        } else if (inputField instanceof TextArea) {
            return ((TextArea) inputField).getText();
        }
        return ""; 
    }
}
