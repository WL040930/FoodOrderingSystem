package com.Group3.foodorderingsystem.Module.Platform.Admin.Database;

import javafx.scene.layout.HBox;

public class AdminDatabase extends HBox{
    
    public AdminDatabase(){
        super();
        init();
    }

    private void init(){
        HBox middleContent = new HBox(20);
        middleContent.setStyle("-fx-padding: 20px; -fx-background-color: #f0f0f0;");

        for (int i = 0; i < 20; i++) {
            HBox contentItem = new HBox();
            contentItem.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
            middleContent.getChildren().add(contentItem);
        }

        this.getChildren().addAll(middleContent);
    }
}
