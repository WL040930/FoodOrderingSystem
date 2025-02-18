package com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.function.Consumer;

import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Vouchers.widgets.KButton;

public class FileUploadWidget extends VBox {

    private final Label selectedFileLabel = new Label();
    private final ImageView avatarImageView = new ImageView();
    private ImageView defaultAvatarImageView;
    private File selectedFile;
    private Consumer<File> onFileSelectedCallback;

    public FileUploadWidget(FileUploadType type, String defaultAvatarPath) {
        setSpacing(10);
        this.defaultAvatarImageView = Images.getImageView(defaultAvatarPath, 100, 100);

        if (type == FileUploadType.BUTTON) {
            initButtonUpload();
        } else if (type == FileUploadType.CIRCLE_AVATAR) {
            initAvatarUpload();
        }
    }

    private void initButtonUpload() {
        Button uploadButton = new KButton("Choose File", () -> {
            handleFileUpload();
        });
        selectedFileLabel.setStyle("-fx-font-size: 12px;");

        getChildren().addAll(uploadButton, selectedFileLabel);
    }

    private void initAvatarUpload() {
        avatarImageView.setFitWidth(100);
        avatarImageView.setFitHeight(100);
        avatarImageView.setPreserveRatio(true);
        avatarImageView.setStyle("-fx-border-radius: 50%; -fx-background-radius: 50%;");

        // Set the default profile picture from ImageView
        avatarImageView.setImage(defaultAvatarImageView.getImage());

        Button uploadButton = new KButton("Upload Image", () -> {
            handleFileUpload();
        });

        getChildren().addAll(avatarImageView, uploadButton);
    }

    private void handleFileUpload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            if (avatarImageView.getParent() != null) {
                avatarImageView.setImage(new Image(selectedFile.toURI().toString()));
            }
            selectedFileLabel.setText("Selected file: " + selectedFile.getName());
            if (onFileSelectedCallback != null) {
                onFileSelectedCallback.accept(selectedFile);
            }
        } else {
            selectedFileLabel.setText("No file selected");
        }
    }

    public void setDefaultAvatar(String defaultAvatarPath) {
        this.defaultAvatarImageView = Images.getImageView(defaultAvatarPath, 100, 100);
        avatarImageView.setImage(defaultAvatarImageView.getImage());
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    public ImageView getDefaultAvatarImageView() {
        return defaultAvatarImageView;
    }

    public void setOnFileSelectedCallback(Consumer<File> callback) {
        this.onFileSelectedCallback = callback;
    }
}
