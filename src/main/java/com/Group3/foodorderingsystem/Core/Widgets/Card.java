package com.Group3.foodorderingsystem.Core.Widgets;

import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Node;

public class Card extends VBox {

    public Card() {
        super();
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);

        this.setBorder(new Border(new BorderStroke(
                Color.DARKGRAY,
                BorderStrokeStyle.SOLID,
                new CornerRadii(10),
                new BorderWidths(2))));
    }

    public Card(Node content, double width, double height, Color backgroundColor) {
        super();
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
        this.setPadding(new Insets(20));
        this.setPrefSize(width, height);

        this.setBackground(new Background(new BackgroundFill(backgroundColor, new CornerRadii(10), Insets.EMPTY)));

        this.setBorder(new Border(new BorderStroke(
                Color.DARKGRAY,
                BorderStrokeStyle.SOLID,
                new CornerRadii(10),
                new BorderWidths(2))));

        this.getChildren().add(content);
    }

    public void setBackgroundColor(Color color) {
        this.setBackground(new Background(new BackgroundFill(color, new CornerRadii(10), Insets.EMPTY)));
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
