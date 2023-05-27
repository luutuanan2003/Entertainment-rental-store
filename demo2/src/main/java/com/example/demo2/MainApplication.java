package com.example.demo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

import projectjava.Admin;

public class MainApplication extends Application {

    private Stage loginStage;

    @Override
    public void start(Stage stage) throws IOException {
        loginStage = stage;
        // login screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();
        double preferredWidth = Region.USE_PREF_SIZE;
        double preferredHeight = Region.USE_PREF_SIZE;
        Scene scene = new Scene(root, preferredWidth, preferredHeight);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setResizable(false); // Set resizable to false
        stage.show();
        LoginController loginController = loader.getController();
        loginController.setMainApplication(this);
    }

    public void openAdminUI() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminUI.fxml"));
            Parent rootadmin = loader.load();
            double preferredWidth = Region.USE_PREF_SIZE;
            double preferredHeight = Region.USE_PREF_SIZE;
            Scene scene = new Scene(rootadmin, preferredWidth, preferredHeight);
            Stage adminStage = new Stage();
            adminStage.setScene(scene);
            adminStage.setTitle("Admin access");
            adminStage.setResizable(false);
            adminStage.show();

            // Close the login stage
            loginStage.close();


        } catch (IOException e) {
            System.out.println("An error occurred while loading the admin user interface.");
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        launch();
    }
}