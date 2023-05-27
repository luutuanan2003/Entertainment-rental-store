package com.example.demo2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.*;

public class CustomerController {
    @FXML
    private TextField customerIDField;

    @FXML
    private TextField itemIDField;

    @FXML
    private ImageView image;
    @FXML
    private TextField returnTotalField;


    @FXML
    public String returned() {
        String customerID = customerIDField.getText().trim();
        boolean validCustomerID = validateCustomerID(customerID);

        if (validCustomerID) {
            boolean customerExists = checkCustomerExists(customerID);

            if (!customerExists) {
                displayAlert("Customer not found with ID: " + customerID);
            } else {
                String itemID = itemIDField.getText().trim();
                boolean validItemID = validateItemID(itemID);

                if (validItemID) {
                    boolean itemReturned = returnItem(customerID, itemID);

                    if (itemReturned) {
                        displayReturnOrderConfirmation(customerID, itemID);
                    } else {
                        displayAlert("Failed to process return order. Please try again.");
                    }
                } else {
                    displayAlert("Invalid item ID. Please enter a valid item ID.");
                }
            }
        } else {
            displayAlert("Invalid customer ID format. Please provide a valid customer ID.");
        }
        return customerID;
    }

    private boolean validateCustomerID(String customerID) {
        // Validate customer ID logic
        if (!customerID.matches("C\\d{3}")) {
            return false;
        } else {
            boolean customerFound = false;

            try (BufferedReader br = new BufferedReader(new FileReader("src/main/customers.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] customerInfo = line.split(",");
                    if (customerInfo[0].equals(customerID)) {
                        customerFound = true;
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (customerFound) {
                return true;
            } else {
                System.out.println("Customer not found with ID: " + customerID);
                return false;
            }
        }
    }

    private boolean checkCustomerExists(String customerID) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/customers.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] info = line.split(",");
                if (info[0].equals(customerID)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean validateItemID(String itemID) {
        // Validate item ID logic
        if (!itemID.matches("I\\d{3}-\\d{4}(,.+)?")) {
            return false;
        } else {
            // Additional validation logic if needed
            return true;
        }
    }

    private boolean returnItem(String customerID, String itemID) {
        // Perform the return operation
        try {
            File inputFile = new File("src/main/customers.txt");
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] info = line.split(",");
                if (info[0].equals(customerID)) {
                    StringBuilder updatedLine = new StringBuilder(info[0]);
                    for (int i = 1; i < info.length; i++) {
                        if (info[i].equals(itemID)) {
                            updatedLine.append(",RETURNED");
                        } else {
                            updatedLine.append(",").append(info[i]);
                        }
                    }
                    writer.write(updatedLine.toString());
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }

            writer.close();
            reader.close();

            // Replace the original file with the updated file
            if (inputFile.delete()) {
                if (!tempFile.renameTo(inputFile)) {
                    System.out.println("Failed to rename the temporary file.");
                    return false;
                }
            } else {
                System.out.println("Failed to delete the original file.");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void displayAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void displayReturnOrderConfirmation(String customerID, String itemID) {
        String confirmationMessage = "Return order confirmed:\n" +
                "Customer ID: " + customerID + "\n" +
                "Item ID: " + itemID + "\n" +
                "------------";

        // Display the confirmation message in the appropriate text fields or labels
        // ...
    }

    public void Vreturned() {
        String CID = customerIDField.getText().trim();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the managementCus.txt file");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] customerInfo = line.split(",");
                    if (customerInfo[0].equals(CID)) {
                        int rewardPoints = Integer.parseInt(customerInfo[5]);
                        rewardPoints += 10; // Add 10 to the current reward points
                        customerInfo[5] = String.valueOf(rewardPoints);
                        line = String.join(",", customerInfo);
                    }
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void initialize() {
        Image img = new Image(getClass().getResourceAsStream("logo.png"));
        image.setImage(img);
    }

    @FXML
    public void rent() {
        String customerID = customerIDField.getText().trim();
        String itemID = itemIDField.getText().trim();

        // Validate customer ID and item ID (you can reuse the validation logic from your existing code)
        boolean validCustomerID = validateCustomerID(customerID);
        boolean validItemID = validateItemID(itemID);

        if (validCustomerID && validItemID) {
            // Perform the rent operation and handle the result
            String result = performRentOperation(customerID, itemID);
            handleRentResult(result);
        } else {
            displayInvalidInputAlert();
        }
    }

    private String performRentOperation(String customerID, String itemID) {
        // Perform the rent operation
        StringBuilder resultBuilder = new StringBuilder();

        // Retrieve information about the item from the items.txt file
        boolean itemFound = false;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/items.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] itemInfo = line.split(",");
                if (itemInfo[0].equals(itemID)) {
                    resultBuilder.append("Item ID: ").append(itemInfo[0]).append("\n");
                    resultBuilder.append("Item Name: ").append(itemInfo[1]).append("\n");
                    resultBuilder.append("Item Price: ").append(itemInfo[5]).append("\n");

                    // Store the customer ID, item ID, and quantity in the orders array
                    String[][] orders = new String[1][2];
                    orders[0][0] = customerID;
                    orders[0][1] = itemID;
                    itemFound = true;
                    break;
                }
            }

            if (!itemFound) {
                resultBuilder.append("Item not found with ID: ").append(itemID);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultBuilder.toString();
    }

    private void handleRentResult(String result) {
        // Handle the result of the rent operation
        // Set the text of the appropriate text fields or display it in a dialog box
        displayResultAlert(result);
    }

    private void displayInvalidInputAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText("Invalid Customer ID or Item ID");
        alert.setContentText("Please provide valid IDs.");
        alert.showAndWait();
    }

    private void displayResultAlert(String result) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rent Result");
        alert.setHeaderText(null);
        alert.setContentText(result);
        alert.showAndWait();
    }

    public int getNumReturnFX(String customerID) {
        customerID = customerIDField.getText().trim();
        boolean validCustomerID = validateCustomerID(customerID);
        int returnTotal = 0;
        if (validCustomerID) {
            returnTotal = retrieveReturnTotal(customerID);

            if (returnTotal != -1) {
                displayReturnTotal(returnTotal);
            } else {
                displayAlert("Customer not found with ID: " + customerID);
            }
        } else {
            displayAlert("Invalid customer ID format. Please provide a valid customer ID.");
        }
        return returnTotal;
    }

    private int retrieveReturnTotal(String customerID) {
        try (BufferedReader br = new BufferedReader(new FileReader("managementCus.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] customerInfo = line.split(",");
                if (customerInfo[0].equals(customerID)) {
                    return Integer.parseInt(customerInfo[4]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if the customer ID is not found
    }

    private void displayReturnTotal(int returnTotal) {
        returnTotalField.setText(Integer.toString(returnTotal));
    }

    public static int getTotalRentalItems(String customerID) {
        int rentalItemCount = 0;
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("managementCus.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4 && data[0].equals(customerID)) {
                    rentalItemCount = Integer.parseInt(data[3]);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rentalItemCount;
    }

    public void updateRentalItemCountLabel(String customerID, Label rentalItemCountLabel) {
        int rentalItemCount = getTotalRentalItems(customerID);
        rentalItemCountLabel.setText(String.valueOf(rentalItemCount));
    }

    private String getLoanType(String itemID) {
        try {
            File file = new File("src/main/items.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] itemInfo = line.split(",");
                if (itemInfo[0].equals(itemID)) {
                    reader.close();
                    String loanType = itemInfo[3]; // Get the loan type
                    return loanType;
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ""; // Return an empty string if the item ID is not found
    }

    private String getAccountType(String customerID) {
        try {
            File file = new File("managementCus.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] customerInfo = line.split(",");
                if (customerInfo[0].equals(customerID)) {
                    reader.close();
                    return customerInfo[2]; // Return the account type
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ""; // Return an empty string if the customer ID is not found
    }

    public void addTotalReturn(String customerID) {
        try {
            File inputFile = new File("managementCus.txt");
            File tempFile = new File("tempManagementCus.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] customerInfo = line.split(",");
                if (customerInfo[0].equals(customerID)) {
                    int totalReturn = Integer.parseInt(customerInfo[4]); // Get the current total return
                    totalReturn++; // Increment the total return by 1
                    customerInfo[4] = String.valueOf(totalReturn); // Update the total return
                    line = String.join(",", customerInfo);
                }
                writer.write(line);
                writer.newLine();
            }

            reader.close();
            writer.close();

            inputFile.delete();
            tempFile.renameTo(inputFile);

            // Display a success message
            displayAlert("Total return for customer " + customerID + " has been incremented.");
        } catch (IOException e) {
            e.printStackTrace();
            // Display an error message
            displayAlert("Failed to increment the total return for customer " + customerID + ".");
        }
    }

    public void RA_promoteManagement(String customerID) {
        try {
            File inputFile = new File("managementCus.txt");
            File tempFile = new File("tempManagementCus.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] customerInfo = line.split(",");
                if (customerInfo[0].equals(customerID)) {
                    customerInfo[2] = "VIP"; // Change the account type to VIP
                    line = String.join(",", customerInfo);
                }
                writer.write(line);
                writer.newLine();
            }

            reader.close();
            writer.close();

            inputFile.delete();
            tempFile.renameTo(inputFile);

            Platform.runLater(() -> {
                // Show a success alert for promotion in the management file
                showSuccessAlert("Promotion Successful", "Customer " + customerID + " has been promoted to VIP in the management file");

                // Update the UI or perform any necessary actions
                // ...
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void promoteCustomer(String customerID) {
        try {
            File inputFile = new File("src/main/customers.txt");
            File tempFile = new File("tempCustomers.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] customerInfo = line.split(",");
                if (customerInfo[0].equals(customerID)) {
                    customerInfo[5] = "VIP"; // Change the account type to VIP
                    line = String.join(",", customerInfo);
                }
                writer.write(line);
                writer.newLine();
            }

            reader.close();
            writer.close();

            inputFile.delete();
            tempFile.renameTo(inputFile);

            Platform.runLater(() -> {
                // Show a success alert for promotion
                showSuccessAlert("Promotion Successful", "Customer " + customerID + " has been promoted to VIP");

                // Update the UI or perform any necessary actions
                // ...
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void RAreturned() {
        String customerID = returned();
        int totalReturns = getNumReturnFX(customerID);

        if (totalReturns > 5) {
            Platform.runLater(() -> {
                // Show a success alert for VIP promotion
                showSuccessAlert("Promotion Successful", "Congratulations!! You have been promoted to VIP");

                // Promote the customer to VIP in customers.txt and managementCus.txt
                promoteCustomer(customerID);
                RA_promoteManagement(customerID);

                // Reset the total returns
                resetTotalReturn(customerID);

                // Show a notification to the user
                showNotification("VIP Promotion", "Customer " + customerID + " has been promoted to VIP");

                // Update the UI or perform any necessary actions
                // ...
            });
        } else {
            Platform.runLater(() -> {
                // Add the return and show the total returns
                addTotalReturn(customerID);

                // Show the total returns in a dialog
                showAlert(Alert.AlertType.INFORMATION, "Total Returns", "Total returns of " + customerID + " is " + totalReturns);

                // Update the UI or perform any necessary actions
                // ...
            });
        }
    }

    private void showSuccessAlert(String title, String message) {
        showAlert(Alert.AlertType.INFORMATION, title, message);
    }

    private void showNotification(String title, String message) {
        showAlert(Alert.AlertType.INFORMATION, title, message);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void resetTotalReturn(String customerID) {
        customerID = customerIDField.getText().trim();
        boolean validCustomerID = validateCustomerID(customerID);

        if (validCustomerID) {
            boolean success = resetReturnTimes(customerID);

            if (success) {
                displayAlert("Number of return times reset to 0 for customer ID: " + customerID);
            } else {
                displayAlert("Failed to reset return times. Please try again.");
            }
        } else {
            displayAlert("Invalid customer ID format. Please provide a valid customer ID.");
        }
    }

    private boolean resetReturnTimes(String customerID) {
        try {
            File inputFile = new File("managementCus.txt");
            File tempFile = new File("tempManagementCus.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] customerInfo = line.split(",");
                if (customerInfo[0].equals(customerID)) {
                    customerInfo[4] = "0"; // Reset the number of return times to 0
                    line = String.join(",", customerInfo);
                }
                writer.write(line);
                writer.newLine();
            }

            reader.close();
            writer.close();

            if (!inputFile.delete()) {
                System.out.println("Failed to delete the original file.");
                return false;
            }
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Failed to rename the temporary file.");
                return false;
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void guest_promoteManagement(String customerID) {
        // Read the file and store the updated customer information in a StringBuilder
        StringBuilder updatedContent = new StringBuilder();
        boolean customerFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader("managementCus.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] customerInfo = line.split(",");
                if (customerInfo.length >= 2 && customerInfo[0].equals(customerID)) {
                    // Change the account type to "Regular" (assuming it is at index 2)
                    customerInfo[2] = "Regular";
                    customerFound = true;
                }
                updatedContent.append(String.join(",", customerInfo)).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (customerFound) {
            // Write the updated content back to the file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("managementCus.txt"))) {
                bw.write(updatedContent.toString());
                guest_showSuccessAlert("Customer Promoted", "Customer has been promoted to Regular.");
                // Update the UI or perform any necessary actions
                Platform.runLater(() -> {
                    // Update the UI or perform any necessary actions
                    // ...
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showErrorAlert("Customer Not Found", "Customer not found with ID: " + customerID);
        }
    }

    public void guest_promoteCustomer(String customerID) {
        // Read the file and store the updated customer information in a StringBuilder
        StringBuilder updatedContent = new StringBuilder();
        boolean customerFound = false;

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/customers.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] customerInfo = line.split(",");
                if (customerInfo.length >= 5 && customerInfo[0].equals(customerID)) {
                    // Change the account type to "Regular" (assuming it is at index 5)
                    customerInfo[4] = "Regular";
                    customerFound = true;
                }
                updatedContent.append(String.join(",", customerInfo)).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (customerFound) {
            // Write the updated content back to the file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/customers.txt"))) {
                bw.write(updatedContent.toString());
                guest_showSuccessAlert("Customer Promoted", "Customer has been promoted to Regular.");
                // Update the UI or perform any necessary actions
                Platform.runLater(() -> {
                    // Update the UI or perform any necessary actions
                    // ...
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showErrorAlert("Customer Not Found", "Customer not found with ID: " + customerID);
        }
    }

    public void Greturned() {
        String customerID = returned();
        if (getNumReturnFX(customerID) > 3) {
            Platform.runLater(() -> {
                // Show a success alert for promotion
                guest_showSuccessAlert("Congratulations!", "You have been promoted to Regular Account");

                // Promote the customer in both customer and management files
                guest_promoteCustomer(customerID);
                guest_promoteManagement(customerID);

                // Reset the total return times to 0
                resetTotalReturn(customerID);

                // Update the UI or perform any necessary actions
                // ...
            });
        } else {
            Platform.runLater(() -> {
                // Increment the total return times by 1
                addTotalReturn(customerID);

                // Show a confirmation alert with the current return count
                showInfoAlert("Return Successful", "Total returns of " + customerID + " is " + getNumReturnFX(customerID));

                // Update the UI or perform any necessary actions
                // ...
            });
        }
    }

    public String Grent(TextField customerIDField, TextField itemIDField) {
        String customerID = customerIDField.getText().trim();

        boolean validCustomerID = validateCustomerID(customerID);

        if (!validCustomerID) {
            displayAlert("Invalid customer ID format. Please provide a valid customer ID.");
            return customerID;
        }

        boolean customerFound = checkCustomerExists(customerID);

        if (!customerFound) {
            displayAlert("Customer not found with ID: " + customerID);
            return customerID;
        }

        String accountType = getAccountType(customerID);
        int numberOfRentals = getTotalRentalItems(customerID);

        if (accountType.equals("Guest") && numberOfRentals == 2) {
            displayAlert("You have reached your maximum rental items.");
            return customerID;
        }

        // Display customer ID verified message
        displayAlert("Customer ID verified");
        // ...

        // Prompt user for item ID
        String itemID = itemIDField.getText().trim();

        boolean validItemID = validateItemID(itemID);

        if (!validItemID) {
            displayAlert("Invalid item ID format. Please provide a valid item ID.");
            return customerID;
        }

        String loanType = getLoanType(itemID);

        if (accountType.equals("Guest") && loanType.equals("2-day")) {
            displayAlert("You are not allowed to rent this kind of item");
            return customerID;
        }

        boolean itemFound = checkItemExists(itemID);

        if (!itemFound) {
            displayAlert("Item not found with ID: " + itemID);
            return customerID;
        }

        // Display item details
        displayAlert("Item ID: " + itemID);
        // ...

        // Store the customer ID, item ID, and quantity in the orders array
        String[][] orders = new String[1][2];
        orders[0][0] = customerID;
        orders[0][1] = itemID;
        // ...

        // Print the order details
        displayAlert("Order Details:\nCustomer ID: " + customerID + "\nItem ID: " + itemID);
        // ...

        // Save the order details to the orders.txt file
        saveOrderDetails(customerID, itemID);

        return customerID;
    }


    private boolean checkItemExists(String itemID) {
        boolean itemExists = false;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/items.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] itemInfo = line.split(",");
                if (itemInfo[0].equals(itemID)) {
                    itemExists = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemExists;
    }


    private void saveOrderDetails(String customerID, String itemID) {
        // Save the order details to the orders.txt file
        // ...

        // Example code to save order details to a file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("orders.txt", true))) {
            bw.write(customerID + "," + itemID);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void guest_showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void addRewardPoint(String CID) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the managementCus.txt file");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] customerInfo = line.split(",");
                    if (customerInfo[0].equals(CID)) {
                        int rewardPoints = Integer.parseInt(customerInfo[5]);
                        rewardPoints += 10; // Add 10 to the current reward points
                        customerInfo[5] = String.valueOf(rewardPoints);
                        line = String.join(",", customerInfo);
                    }
                    writer.write(line);
                    writer.newLine();
                }

                System.out.println("Reward points for customer " + CID + " have been updated in the management file.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
