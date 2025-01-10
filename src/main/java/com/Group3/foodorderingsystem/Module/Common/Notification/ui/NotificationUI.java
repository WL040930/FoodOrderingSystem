package com.Group3.foodorderingsystem.Module.Common.Notification.ui;

import java.util.List;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.NotificationModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.Card;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.DynamicSearchBarUI;
import com.Group3.foodorderingsystem.Module.Platform.Runner.RunnerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebView;

public class NotificationUI extends BaseContentPanel {

    private User user;
    List<NotificationModel> notifications;

    public NotificationUI(User user) {
        super();
        this.user = user;
        initNotification();

        setHeader(header());
        setContent(content());

        setFooterHeight(0);
        setContentHeight(570);
    }

    private void initNotification() {
        switch (user.getRole()) {
            case CUSTOMER:
                this.notifications = CustomerViewModel.getNotificationViewModel().getNotifications();
                break;

            case RUNNER:
                this.notifications = RunnerViewModel.getNotificationViewModel().getNotifications();
                break;

            case VENDOR:
                this.notifications = VendorViewModel.getNotificationViewModel().getNotifications();
                break;

            case ADMIN:
                this.notifications = AdminViewModel.getNotificationViewModel().getNotifications();
                break;

            default:
                break;
        }
    }

    private Node header() {
        return new TitleBackButton("Notification");
    }

    private Node content() {
        VBox contentBox = new VBox(5);

        Label totalLabel = new Label("Total Notifications: " + notifications.size());
        totalLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        contentBox.getChildren().add(totalLabel);
        contentBox.setPadding(new Insets(0, 10, 0, 10));

        DynamicSearchBarUI<NotificationModel> searchBar = new DynamicSearchBarUI<>(notifications, "content", null,
                this::initNotificationCard);
        searchBar.setSearchBarVisible(false);
        contentBox.getChildren().add(searchBar);

        return contentBox;
    }

    private Node initNotificationCard(NotificationModel notification) {
        // Create the main container
        VBox notificationCard = new VBox();
        notificationCard.setSpacing(10);
        notificationCard.setPadding(new Insets(10));

        // Display date
        Label dateLabel = new Label(notification.getDate().toString());
        dateLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

        String htmlContent = notification.getContent();
        TextFlow contentFlow = parseHtmlToTextFlow(htmlContent);
        contentFlow.setPrefHeight(50);

        // Add date and content to the VBox
        notificationCard.getChildren().addAll(dateLabel, contentFlow);

        return new Card(notificationCard, 450, 100, null);
    }

    // Method to parse basic HTML and return a TextFlow
    private TextFlow parseHtmlToTextFlow(String htmlContent) {
        TextFlow textFlow = new TextFlow();
        textFlow.setStyle("-fx-font-size: 14px;");

        // Replace <br> tags with \n to handle line breaks
        htmlContent = htmlContent.replaceAll("(?i)<br\\s*/?>", "\n");

        // Simple parsing for <b> tags and handling newlines
        String[] parts = htmlContent.split("(<b>|</b>)");
        boolean bold = false;

        for (String part : parts) {
            if (part.isEmpty())
                continue;

            String[] lines = part.split("\n"); // Split by newlines
            for (int i = 0; i < lines.length; i++) {
                Text text = new Text(lines[i]);
                if (bold) {
                    text.setStyle("-fx-font-weight: bold;");
                }

                textFlow.getChildren().add(text);

                // Add a newline if it's not the last line
                if (i < lines.length - 1) {
                    textFlow.getChildren().add(new Text("\n"));
                }
            }
            bold = !bold;
        }

        return textFlow;
    }

}
