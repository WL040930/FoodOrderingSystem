package com.Group3.foodorderingsystem.Core.Widgets;

import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Entity.Config.HeaderClass;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class Header extends HBox {

    /**
     * Insert a List<HeaderClass> for automatically generating a header
     * 
     * @param config - consists of title, action, and isSelected
     */
    public Header(List<HeaderClass> config) {
        init(config);
    }

    private void init(List<HeaderClass> config) {
        this.setSpacing(10);
        this.setStyle("-fx-padding: 10px; -fx-background-color: #f8fafc;");
        this.getStylesheets().add(getClass()
                .getResource("/com/Group3/foodorderingsystem/Module/Common/HeaderCSS.css")
                .toExternalForm());

        for (HeaderClass headerItem : config) {
            Button button = createButton(headerItem);
            if (headerItem.getIsSelected()) {
                button.getStyleClass().add("selected-button");
            } else {
                button.getStyleClass().add("default-button");
            }
            this.getChildren().add(button);
        }
    }

    private Button createButton(HeaderClass headerItem) {
        Button button = new Button(headerItem.getTitle());
        button.setOnAction(event -> {
            headerItem.getAction().run();
        });
        return button;
    }
}