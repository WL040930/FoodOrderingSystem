package com.Group3.foodorderingsystem.Core.Widgets;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class BaseContentPanel extends BorderPane {

    protected Node header;
    protected Node footer;
    protected Node content;

    private double headerHeight = 50;
    private double footerHeight = 50;
    private double contentHeight = 500;

    public BaseContentPanel() {
        super();
    }

    public void setHeader(Node header) {
        this.header = header;
        this.setTop(header);
        setSectionHeights();
    }

    public void setFooter(Node footer) {
        VBox footerBox = new VBox(footer);
        this.footer = footer;
        this.setBottom(footerBox);

        footerBox.setStyle("-fx-padding: 10px;");
        setSectionHeights();
    }

    public void setContent(Node content) {
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        scrollPane.setStyle("-fx-border-color: transparent; -fx-background-color:transparent;");

        this.content = scrollPane;
        this.setCenter(scrollPane);
        setSectionHeights();
    }

    private void setSectionHeights() {
        if (header instanceof Region) {
            ((Region) header).setMaxHeight(headerHeight);
            ((Region) header).setMinHeight(headerHeight);
        }

        if (footer instanceof Region) {
            ((Region) footer).setMaxHeight((footerHeight));
            ((Region) footer).setMinHeight(footerHeight);
        }

        if (content instanceof Region) {
            ((Region) content).setMaxHeight(contentHeight);
            ((Region) content).setMinHeight(contentHeight);
        }
    }

    public Node getHeader() {
        return header;
    }

    public Node getFooter() {
        return footer;
    }

    public Node getContent() {
        return content;
    }

    public void setHeaderHeight(double height) {
        this.headerHeight = height;
        if (header instanceof Region) {
            ((Region) header).setMinHeight(headerHeight);
            ((Region) header).setMaxHeight(headerHeight);
        }
    }

    public void setFooterHeight(double height) {
        this.footerHeight = height;
        if (footer instanceof Region) {
            ((Region) footer).setMinHeight(footerHeight);
            ((Region) footer).setMaxHeight(footerHeight);
        }
    }

    public void setContentHeight(double height) {
        this.contentHeight = height;
        if (content instanceof Region) {
            ((Region) content).setMinHeight(contentHeight);
            ((Region) content).setMaxHeight(contentHeight);
        }
    }
}
