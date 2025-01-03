package com.Group3.foodorderingsystem.Core.Model.Entity.Config;

import javafx.scene.Node;

public abstract class ViewModelConfig {
    
    protected abstract void navigate(Node node); 

    private Node node;

    public ViewModelConfig() {
    }

    public ViewModelConfig(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
