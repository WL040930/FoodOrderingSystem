package com.Group3.foodorderingsystem.Module.Common.Login;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.VendorModel;
import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Runner.RunnerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginPage extends Application {

    private TextField userTextField;
    private PasswordField pwBox;

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        root.getChildren().addAll(_buildLogo(), _buildFormField(), _buildLoginButton());

        Scene scene = new Scene(root, 500, 780);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Node _buildLogo() {
        return Images.getImageView("logo.png", 200, 200);
    }

    private Node _buildFormField() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Email:");
        grid.add(userName, 0, 1);

        userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        return grid;
    }

    private Node _buildLoginButton() {
        VBox buttonContainer = new VBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10));

        final Text actiontarget = new Text();
        actiontarget.setId("actiontarget");

        Button btn = new Button("Sign in");

        btn.setOnAction(e -> {
            String email = userTextField.getText();
            String password = pwBox.getText();

            if (email.isEmpty() || password.isEmpty()) {
                actiontarget.setFill(Color.RED);
                actiontarget.setText("Please enter both email and password.");
            } else {
                User user = UserServices.findUserByEmailAndPassword(email, password);
                if (user != null) {
                    actiontarget.setFill(Color.GREEN);
                    actiontarget.setText("Login successful!");

                    openMainAppWindow(user);
                } else {
                    actiontarget.setFill(Color.RED);
                    actiontarget.setText("Invalid email or password. Please try again.");
                }
            }
        });

        buttonContainer.getChildren().addAll(actiontarget, btn);
        return buttonContainer;
    }

    private void openMainAppWindow(User user) {
        if (user.getRole() == RoleEnum.ADMIN) {
            SessionUtil.setAdminInSession(user);

            AdminViewModel adminViewModel = new AdminViewModel();

            Stage adminStage = new Stage();
            adminViewModel.getAdminMainFrame().start(adminStage);

            Stage currentStage = (Stage) userTextField.getScene().getWindow();
            currentStage.close();

            adminStage.show();
        } else if (user.getRole() == RoleEnum.CUSTOMER) {
            System.out.println("Customer login");
            CustomerModel customer = UserServices.findCustomerById(user.getId());
            SessionUtil.setCustomerInSession(customer);

            CustomerViewModel customerViewModel = new CustomerViewModel();
            Stage customerStage = new Stage();
            customerViewModel.getCustomerMainFrame().start(customerStage);

            Stage currentStage = (Stage) userTextField.getScene().getWindow();
            currentStage.close();

            customerStage.show();
        } else if (user.getRole() == RoleEnum.VENDOR) {
            System.out.println("Customer login");
            SessionUtil.setVendorInSession(user); 

            VendorViewModel vendorViewModel = new VendorViewModel();
            Stage customerStage = new Stage();
            vendorViewModel.getMainFrame().start(customerStage);

            Stage currentStage = (Stage) userTextField.getScene().getWindow();
            currentStage.close();

            customerStage.show();
        } else if (user.getRole() == RoleEnum.RUNNER) {
            SessionUtil.setRiderInSession(user); 

            RunnerViewModel runnerViewModel = new RunnerViewModel();
            Stage stage = new Stage();
            runnerViewModel.getMainFrame().start(stage);

            Stage currentStage = (Stage) userTextField.getScene().getWindow();
            currentStage.close();

            stage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
