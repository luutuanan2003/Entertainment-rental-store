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

public class MainApplicationCustomer extends Application {

    private Stage loginStage;

    @Override
    public void start(Stage stage) throws IOException {
        loginStage = stage;
        // login screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("customerUI.fxml"));
        Parent root = loader.load();
        double preferredWidth = Region.USE_PREF_SIZE;
        double preferredHeight = Region.USE_PREF_SIZE;
        Scene scene = new Scene(root, preferredWidth, preferredHeight);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setResizable(false); // Set resizable to false
        stage.show();
    }

    
    public static void main(String[] args) {
        launch();
    }
}