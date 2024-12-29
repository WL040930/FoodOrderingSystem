package com.Group3.foodorderingsystem.Core.Util;

import com.Group3.foodorderingsystem.Core.Storage.Storage;

import javafx.scene.image.ImageView;

public class Images {

    public static ImageView getImageView(String fileName, int width, int height) {
        ImageView imageView = null;
        try {
            String imagePath = Images.class.getResource(Storage.RESOURCE_PATH + "/" + fileName).toExternalForm();
            imageView = new ImageView(new javafx.scene.image.Image(imagePath));
        } catch (Exception e) {
            System.err.println("Error: Unable to load image. Please check the path.");
            imageView = new ImageView();
        }

        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }
}
