package com.example.demo2;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplicationCustomer extends Application {

    private Stage loginStage;
    private Stage customerStage;


    @Override
    public void start(Stage stage) throws IOException {
        loginStage = stage;

        // Login screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginCUSTOMER.fxml"));
        Parent root = loader.load();
        double preferredWidth = Region.USE_PREF_SIZE;
        double preferredHeight = Region.USE_PREF_SIZE;
        Scene scene = new Scene(root, preferredWidth, preferredHeight);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.show();

        LoginControllerCUSTOMER loginController = loader.getController();
        loginController.setMainApplication(this);
    }
    public boolean openCustomerUI() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("customerUI.fxml"));
            Parent rootadmin = loader.load();
            double preferredWidth = Region.USE_PREF_SIZE;
            double preferredHeight = Region.USE_PREF_SIZE;
            Scene scene = new Scene(rootadmin, preferredWidth, preferredHeight);
            customerStage = new Stage();
            customerStage.setScene(scene);
            customerStage.setTitle("Admin access");
            customerStage.setResizable(false);
            customerStage.show();
            CustomerController customerController = loader.getController();
            customerController.setMainApplicationCUSTOMER(this);

            // Close the login stage
            loginStage.close();
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred while loading the admin user interface.");
            e.printStackTrace();
        }
        return false;
    }
    
    public static void main(String[] args) {
        launch();
    }
}