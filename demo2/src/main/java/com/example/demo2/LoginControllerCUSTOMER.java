package com.example.demo2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginControllerCUSTOMER {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorText;

    // Path to the customers.txt file
    private static final String CUSTOMERS_FILE_PATH = "src/main/customers.txt";

    private MainApplicationCustomer mainApplication;

    public void setMainApplication(MainApplicationCustomer mainApplication) {
        this.mainApplication = mainApplication;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            if (isValidCredentials(username, password)) {
                // Perform successful login action
                errorText.setVisible(false);
                mainApplication.openCustomerUI();
            } else {
                // Display error message for incorrect credentials
                errorText.setText("Username or password is incorrect");
                errorText.setVisible(true);
            }
        } catch (IOException e) {
            // Handle file read error
            errorText.setText("An error occurred. Please try again later.");
            errorText.setVisible(true);
            e.printStackTrace();
        }
    }

    private boolean isValidCredentials(String username, String password) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMERS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 8) {
                    String storedUsername = parts[6].trim();
                    String storedPassword = parts[7].trim();

                    if (username.equals(storedUsername) && password.equals(storedPassword)) {
                        return true; // Found matching credentials
                    }
                }
            }
        }

        return false; // No matching credentials found
    }


}

