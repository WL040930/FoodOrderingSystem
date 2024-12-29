package com.Group3.foodorderingsystem.Core.Model.Entity.Config;

public class BottomNavigationClass {
    private String title; 
    private String iconName; 
    private Runnable action; 
    private Boolean isSelected; 

    public BottomNavigationClass(String title, String iconName, Runnable action, Boolean isSelected) {
        this.title = title; 
        this.iconName = iconName; 
        this.action = action; 
        this.isSelected = isSelected;
    }

    public String getTitle() {
        return title; 
    }

    public String getIconName() {
        return iconName; 
    }

    public Runnable getAction() {
        return action; 
    }

    public Boolean getIsSelected() {
        return isSelected; 
    }
}
