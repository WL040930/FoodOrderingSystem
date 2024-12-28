package com.Group3.foodorderingsystem.Core.Model.Entity.Config;

import javafx.scene.Node;

public class ViewModelConfig {
    
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
