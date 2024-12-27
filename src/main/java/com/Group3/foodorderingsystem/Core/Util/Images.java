package com.Group3.foodorderingsystem.Core.Util;

import javax.swing.ImageIcon;

import com.Group3.foodorderingsystem.Core.Storage.Storage;

import javafx.scene.image.ImageView;

import java.awt.Image;
import java.awt.MediaTracker;

public class Images {

    @Deprecated
    public static ImageIcon getImage(String fileName, int width, int height) {
        ImageIcon imageIcon = null;
        try {
            imageIcon = new ImageIcon(Images.class.getResource(Storage.RESOURCE_PATH + "/" + fileName));
        } catch (Exception e) {
            imageIcon = new ImageIcon(fileName);
        }

        if (imageIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            Image scaledImage = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } else {
            System.err
                    .println("Error: Image not found at the specified path: " + Storage.RESOURCE_PATH + "/" + fileName);
            return null;
        }
    }

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
