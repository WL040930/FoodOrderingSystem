package com.Group3.foodorderingsystem.Module.Common.Login;

import com.Group3.foodorderingsystem.Core.Model.Entity.User.CustomerModel;
import com.Group3.foodorderingsystem.Core.Model.Entity.User.User;
import com.Group3.foodorderingsystem.Core.Model.Enum.RoleEnum;
import com.Group3.foodorderingsystem.Core.Services.UserServices;
import com.Group3.foodorderingsystem.Core.Util.Images;
import com.Group3.foodorderingsystem.Core.Util.SessionUtil;
import com.Group3.foodorderingsystem.Module.Platform.Admin.AdminViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Customer.CustomerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Manager.ManagerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Runner.RunnerViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.VendorViewModel;
import com.Group3.foodorderingsystem.Module.Platform.Vendor.Vouchers.widgets.KButton;

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
        grid.setHgap(12); // Slightly increased horizontal gap for better spacing
        grid.setVgap(8); // Adjusted vertical gap for improved alignment
        grid.setPadding(new Insets(30, 35, 30, 35)); // More spacious padding

        // Title with enhanced styling
        Text scenetitle = new Text("Welcome to Food Orbit");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 24));
        grid.add(scenetitle, 0, 0, 2, 1);

        // Email label and field
        Label userName = new Label("Email:");
        userName.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        grid.add(userName, 0, 1);

        userTextField = new TextField();
        userTextField.setPrefWidth(280);
        userTextField.setStyle("-fx-border-radius: 5px; -fx-padding: 5px;");
        grid.add(userTextField, 0, 2);

        // Password label and field
        Label pw = new Label("Password:");
        pw.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        grid.add(pw, 0, 3);

        pwBox = new PasswordField();
        pwBox.setPrefWidth(280);
        pwBox.setStyle("-fx-border-radius: 5px; -fx-padding: 5px;");
        grid.add(pwBox, 0, 4);

        return grid;
    }

    private Node _buildLoginButton() {
        VBox buttonContainer = new VBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10));

        final Text actiontarget = new Text();
        actiontarget.setId("actiontarget");

        Button btn = new KButton("Sign in", () -> {
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
        } else if (user.getRole() == RoleEnum.MANAGER) {
            SessionUtil.setManagerInSession(user);

            ManagerViewModel managerViewModel = new ManagerViewModel();
            Stage stage = new Stage();
            managerViewModel.getMainFrame().start(stage);

            Stage currentStage = (Stage) userTextField.getScene().getWindow();
            currentStage.close();

            stage.show();
        }
    }
}
