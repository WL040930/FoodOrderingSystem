package com.Group3.foodorderingsystem.Core.Util;

import javax.swing.ImageIcon;

import com.Group3.foodorderingsystem.Core.Storage.Storage;

import java.awt.Image;
import java.awt.MediaTracker;

public class Images {
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
                System.err.println("Error: Image not found at the specified path: " + Storage.RESOURCE_PATH + "/" + fileName);
                return null;
            }
        }
}
