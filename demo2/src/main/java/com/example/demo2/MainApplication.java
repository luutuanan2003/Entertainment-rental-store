package com.example.demo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    private Stage loginStage;
    private Stage adminStage;

    @Override
    public void start(Stage stage) throws IOException {
        loginStage = stage;

        // Login screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        double preferredWidth = Region.USE_PREF_SIZE;
        double preferredHeight = Region.USE_PREF_SIZE;
        Scene scene = new Scene(root, preferredWidth, preferredHeight);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.show();

        LoginControllerADMIN loginController = loader.getController();
        loginController.setMainApplication(this);
    }

    public boolean openAdminUI() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminUI.fxml"));
            Parent rootadmin = loader.load();
            double preferredWidth = Region.USE_PREF_SIZE;
            double preferredHeight = Region.USE_PREF_SIZE;
            Scene scene = new Scene(rootadmin, preferredWidth, preferredHeight);
            adminStage = new Stage();
            adminStage.setScene(scene);
            adminStage.setTitle("Admin access");
            adminStage.setResizable(false);
            adminStage.show();
            AdminController adminController = loader.getController();
            adminController.setMainApplicationADMIN(this);

            // Close the login stage
            loginStage.close();
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred while loading the admin user interface.");
            e.printStackTrace();
        }
        return false;
    }

    public void openAdminUI2() {
        if (adminStage != null && adminStage.isShowing()) {
            // Admin stage is already open
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminUI2.fxml"));
            Parent rootadmin = loader.load();
            double preferredWidth = Region.USE_PREF_SIZE;
            double preferredHeight = Region.USE_PREF_SIZE;
            Scene scene = new Scene(rootadmin, preferredWidth, preferredHeight);
            adminStage = new Stage();
            adminStage.setScene(scene);
            adminStage.setTitle("Admin access 2");
            adminStage.setResizable(false);
            adminStage.show();
            AdminController adminController = loader.getController();
            adminController.setMainApplicationADMIN(this);
        } catch (IOException e) {
            System.out.println("An error occurred while loading the admin user interface.");
            e.printStackTrace();
        }
    }

    public void closeAdminUI() {
        if (adminStage != null && adminStage.isShowing()) {
            adminStage.close();
        }
    }

    public boolean isOpenAdminUI() {
        return adminStage != null && adminStage.isShowing();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
