package com.Group3.foodorderingsystem.Module.Platform.Manager.Runner.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.RunnerModel;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.Card;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.DynamicSearchBarUI;
import com.Group3.foodorderingsystem.Module.Platform.Manager.ManagerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Vouchers.widgets.KButton;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RunnerListUI extends BaseContentPanel {

    public RunnerListUI() {
        super();

        setHeader(header());
        setContent(content());

        setFooterHeight(0);
        setContentHeight(580);
    }

    private Node header() {
        return new TitleBackButton("Runner List");
    }

    private Node content() {
        DynamicSearchBarUI searchBarUI = new DynamicSearchBarUI<RunnerModel>(UserServices.getRunners(), "name",
                null, this::runnerCard);

        return searchBarUI;
    }

    private Node runnerCard(RunnerModel user) {
        // Horizontal box for the main user panel
        HBox mainPanel = new HBox();
        mainPanel.setSpacing(10);

        // User profile picture
        ImageView imageView = Images.getImageView(user.getProfilePicture(), 100, 100);

        // Vertical box for user details
        VBox userDetailsBox = new VBox(10);
        Label nameLabel = new Label(user.getName());
        Label emailLabel = new Label(user.getEmail());
        Label roleLabel = new Label(user.getRole().toString());

        userDetailsBox.getChildren().addAll(nameLabel, emailLabel, roleLabel);

        // Add profile picture and details to the main panel
        mainPanel.getChildren().addAll(imageView, userDetailsBox);

        VBox mainPaneVBox = new VBox();
        mainPaneVBox.getChildren().add(mainPanel);

        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setSpacing(10);

        Button ratings = new KButton("Ratings", () -> {
            ManagerViewModel.getRunnerPerformanceViewModel().setRunnerRating(new RunnerRating(user));
            ManagerViewModel.getRunnerPerformanceViewModel()
                    .navigate(ManagerViewModel.getRunnerPerformanceViewModel().getRunnerRating());
        });

        buttonBox.getChildren().addAll(ratings);
        mainPaneVBox.getChildren().add(buttonBox);

        // Create the card to hold the user panel
        Card userCard = new Card(mainPaneVBox, 470, 100, null);
        userCard.setSpacing(0);

        // Wrap the card in a centered container
        HBox cardContainer = new HBox(userCard);
        cardContainer.setAlignment(Pos.CENTER);

        return cardContainer;
    }
}