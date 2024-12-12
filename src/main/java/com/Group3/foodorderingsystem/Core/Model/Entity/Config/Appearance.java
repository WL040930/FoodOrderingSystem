package com.Group3.foodorderingsystem.Core.Model.Entity.Config;

public class Appearance {
    
    boolean isDarkMode;

    public Appearance(boolean isDarkMode) {
        this.isDarkMode = isDarkMode;
    }

    public boolean isDarkMode() {
        return isDarkMode;
    }

    public void setDarkMode(boolean isDarkMode) {
        this.isDarkMode = isDarkMode;
    }

    public void toggleDarkMode() {
        isDarkMode = !isDarkMode;
    }
}
