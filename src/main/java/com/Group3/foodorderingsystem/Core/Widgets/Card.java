package com.Group3.foodorderingsystem.Core.Widgets;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Card extends VBox {

    public Card() {
        super();
        this.setAlignment(Pos.CENTER); 
        this.setSpacing(10); 
        this.setPadding(new Insets(10, 15, 10, 15));

        this.setBackground(new Background(new BackgroundFill(
                Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        this.setBorder(new Border(new BorderStroke(
                Color.LIGHTGRAY,
                BorderStrokeStyle.SOLID, 
                new CornerRadii(20), 
                new BorderWidths(1)
        )));

        this.setPrefWidth(450);
    }

    public Card(Node content, double width, double height, Color backgroundColor) {
        this();
        this.setPrefSize(width, height);
        this.setBackground(new Background(new BackgroundFill(
                backgroundColor, new CornerRadii(20), Insets.EMPTY))); 
        this.getChildren().add(content);
    }

    public void setBackgroundColor(Color color) {
        this.setBackground(new Background(new BackgroundFill(
                color, new CornerRadii(20), Insets.EMPTY)));
    }

    public void setCardSize(double width, double height) {
        this.setMaxSize(width, height);
        this.setMinSize(width, height);
    }

    public void setContent(Node content) {
        this.getChildren().clear();
        this.getChildren().add(content);
    }
}
