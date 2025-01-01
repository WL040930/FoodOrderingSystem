package com.Group3.foodorderingsystem.Module.Common.settings.ui;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Widgets.Card;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class UserCard extends Card {

    private User user;

    public UserCard(String userId) {
        super();
        this.user = UserServices.findUserById(userId); 

        setCardSize(400, 100);
        setBackgroundColor(Color.web("#f5f5f5"));
        setContent(buildContent()); 
    }

    public Node buildContent() {
        HBox content = new HBox(10);
        content.setStyle("-fx-alignment: center-left; -fx-padding: 10px;");

        ImageView profileImage = Images.getImageView(user.getProfilePicture(), 70, 70);

        VBox profileBox = new VBox();
        profileBox.setStyle("-fx-alignment: center-left;");
        Label nameLabel = new Label(user.getName());
        nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Label email = new Label(user.getEmail());
        Label roleLabel = new Label("Role: " + user.getRole());
        roleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
        profileBox.getChildren().addAll(nameLabel, email, roleLabel);

        content.getChildren().addAll(profileImage, profileBox);

        return content;
    }
}
