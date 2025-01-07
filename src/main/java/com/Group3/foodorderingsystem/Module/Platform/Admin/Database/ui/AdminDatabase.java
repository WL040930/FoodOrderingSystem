package com.Group3.foodorderingsystem.Module.Platform.Admin.Database.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Widgets.BaseContentPanel;
import com.Group3.foodorderingsystem.Core.Widgets.Card;
import com.Group3.foodorderingsystem.Core.Widgets.TitleBackButton;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Admin.Register.widgets.PopupMessage;
import com.Group3.foodorderingsystem.Module.Platform.Customer.Home.widgets.DynamicSearchBarUI;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AdminDatabase extends BaseContentPanel {

    // Constructor
    public AdminDatabase() {
        super();
        // Set up the UI components
        setHeader(createHeader());
        setContent(createContent());

        // Customize panel properties
        setFooterHeight(0);
        setContentHeight(570);
    }

    // Create the header with a back button and title
    private Node createHeader() {
        return new TitleBackButton("Database");
    }

    // Create the main content with a dynamic search bar
    private Node createContent() {
        DynamicSearchBarUI<User> searchBarUI = new DynamicSearchBarUI<>(
                UserServices.getBaseUsers(), // Data source
                "email", // Field for dynamic searching
                null, // Placeholder text (optional)
                this::createUserCard // Method reference for card creation
        );

        return searchBarUI;
    }

    // Method to create a user card
    private Node createUserCard(User user) {
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

        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> {
            AdminViewModel.getDatabaseViewModel().setEditUserInformation(new EditUserInformation(user));
            AdminViewModel.getDatabaseViewModel()
                    .navigate(AdminViewModel.getDatabaseViewModel().getEditUserInformation());
        });

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            UserServices.deleteUser(user.getId());
            
            PopupMessage.showMessage(user.getName()+ " has been deleted.", "success", () -> {
                AdminViewModel.initDatabaseViewModel();
                AdminViewModel.getDatabaseViewModel().navigate(AdminViewModel.getDatabaseViewModel().getNode());
            });
        });

        buttonBox.getChildren().addAll(deleteButton, editButton);
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
