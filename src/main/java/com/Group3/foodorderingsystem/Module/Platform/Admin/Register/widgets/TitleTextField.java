package com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets;

import javafx.scene.control.TextField;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class TitleTextField extends VBox {

    private final String title;
    private final String promptText;
    private final String defaultText;
    private final TitleTextFieldEnum fieldType;
    private javafx.scene.Node inputField; // Hold reference to the input field

    public TitleTextField(String title, String promptText, String defaultText, TitleTextFieldEnum fieldType) {
        super();
        this.title = title;
        this.promptText = promptText;
        this.defaultText = defaultText;
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
        TextField textField;

        switch (fieldType) {
            case PasswordField:
                PasswordField passwordField = new PasswordField();
                passwordField.setPromptText(promptText);
                passwordField.setText(defaultText);
                passwordField.setStyle("-fx-min-height: 30px; -fx-font-size: 14px; -fx-padding: 4px;");
                return passwordField;

            case TextArea:
                TextArea textArea = new TextArea();
                textArea.setPromptText(promptText);
                textArea.setText(defaultText);
                return textArea;

            case IntegerField:
                textField = new TextField();
                textField.setPromptText(promptText);
                textField.setText(defaultText);
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*")) {
                        textField.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                });
                textField.setStyle("-fx-min-height: 30px; -fx-font-size: 14px; -fx-padding: 4px;");
                return textField;

            case DecimalField:
                textField = new TextField();
                textField.setPromptText(promptText);
                textField.setText(defaultText);
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*(\\.\\d{0,2})?")) {
                        textField.setText(oldValue);
                    }
                });
                textField.setStyle("-fx-min-height: 30px; -fx-font-size: 14px; -fx-padding: 4px;");
                return textField;

            case EmailField:
                textField = new TextField();
                textField.setPromptText(promptText);
                textField.setText(defaultText);
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("^[\\w.%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
                        textField.setStyle(
                                "-fx-border-color: red; -fx-min-height: 30px; -fx-font-size: 14px; -fx-padding: 4px;");
                    } else {
                        textField.setStyle("-fx-min-height: 30px; -fx-font-size: 14px; -fx-padding: 4px;");
                    }
                });
                return textField;

            case TextField:
            default:
                textField = new TextField();
                textField.setPromptText(promptText);
                textField.setText(defaultText);
                textField.setStyle("-fx-min-height: 30px; -fx-font-size: 14px; -fx-padding: 4px;");
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

    public StringProperty textProperty() {
        if (inputField instanceof TextField) {
            return ((TextField) inputField).textProperty();
        } else if (inputField instanceof PasswordField) {
            return ((PasswordField) inputField).textProperty();
        } else if (inputField instanceof TextArea) {
            return ((TextArea) inputField).textProperty();
        }
        return null;
    }

    public TextField getTextField() {
        if (inputField instanceof TextField) {
            return (TextField) inputField;
        }
        return null;
    }
}
