package com.Group3.foodorderingsystem.Core.Model.Entity.Config;

public class HeaderClass {
    
    private String title; 
    private Boolean isSelected; 
    private Runnable action;

    public HeaderClass(String title, Boolean isSelected, Runnable action) {
        this.title = title; 
        this.isSelected = isSelected; 
        this.action = action;
    }

    public String getTitle() {
        return title; 
    }

    public Boolean getIsSelected() {
        return isSelected; 
    }

    public Runnable getAction() {
        return action; 
    }
}
