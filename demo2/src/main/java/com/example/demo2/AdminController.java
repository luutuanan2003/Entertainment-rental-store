package com.example.demo2;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import projectjava.Customer;
import projectjava.Item;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

import static projectjava.Admin.displayCusbylevel;

public class AdminController {
    @FXML
    private TextField newTypeField;
    @FXML
    private TextField customerpasswordField;

    @FXML
    private TableView<Customer> customersTable;
    @FXML
    private TableColumn<Customer, String> customerIDColumn;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, String> phoneColumn;
    @FXML
    private TableColumn<Customer, Integer> numRentalsColumn;
    @FXML
    private TableColumn<Customer, String> accountTypeColumn;
    @FXML
    private TableColumn<Customer, String> usernameColumn;
    @FXML
    private TableColumn<Customer, String> passwordColumn;
    @FXML
    private TableView<Item> itemsTable;
    @FXML
    private TableColumn<Item, String> itemIDColumn;
    @FXML
    private TableColumn<Item, String> titleColumn;
    @FXML
    private TableColumn<Item, String> rentalTypeColumn;
    @FXML
    private TableColumn<Item, String> loanTypeColumn;
    @FXML
    private TableColumn<Item, Integer> quantityColumn;
    @FXML
    private TableColumn<Item, Double> rentalFeeColumn;
    @FXML
    private TableColumn<Item, String> genreColumn;
    @FXML
    private TextField customerIDField;
    @FXML
    private TextField itemIDField;
    @FXML
    private Button addButton;
    @FXML
    private Button increaseRentalsButton;
    @FXML
    private Button returnItemButton;
    @FXML
    private Label resultLabel;
    @FXML
    private TextField customerNameField;
    @FXML
    private TextField quantityField;

    @FXML
    private TextField fieldToUpdateField;

    @FXML
    private TextField newValueField;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField rentalsField;

    @FXML
    private TextField customerTypeField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;


    @FXML
    private TextField idTextField;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField rentalTypeTextField;
    @FXML
    private TextField loanTypeTextField;
    @FXML
    private TextField quantityTextField;

    @FXML
    private TextField rentalFeeTextField;

    @FXML
    private TextField genreTextField;

    @FXML
    private ComboBox<String> fieldComboBox;

    @FXML
    private TextField newValueTextField;
    @FXML
    private TextField itemIDTextField;
    private MainApplication mainApplication;

    @FXML
    private Button switchStageButton;

    public void setMainApplicationADMIN(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    @FXML
    private void handleSwitchStage1to2() {
        if (mainApplication != null) {
            if (mainApplication.isOpenAdminUI()) {
                mainApplication.closeAdminUI();
                mainApplication.openAdminUI2();
            }
        }
    }

    @FXML
    private void handleSwitchStage2to1() {
        if (mainApplication != null) {
            if (mainApplication.isOpenAdminUI()) {
                mainApplication.closeAdminUI();
                mainApplication.openAdminUI();
            }
        }
    }





    @FXML
    public void addItem() {
        String fileName = "src/main/items.txt"; // Replace with the actual file path

        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, true))) {
            boolean continueAdding = true;
            do {
                try {
                    String ID = idTextField.getText();
                    String title = titleTextField.getText();
                    String rentalType = rentalTypeTextField.getText();
                    String loanType = loanTypeTextField.getText();
                    int quantity = Integer.parseInt(quantityTextField.getText());
                    double rentalFee = Double.parseDouble(rentalFeeTextField.getText());
                    String genre = genreTextField.getText();

                    pw.printf("%s,%s,%s,%s,%d,%.2f,%s\n", ID, title, rentalType, loanType, quantity, rentalFee, genre);

                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Add Item");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Do you wish to continue entering a new item to the database?");

                    ButtonType yesButton = new ButtonType("Yes");
                    ButtonType noButton = new ButtonType("No");

                    confirmationAlert.getButtonTypes().setAll(yesButton, noButton);
                    ButtonType choice = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

                    if (choice == noButton) {
                        continueAdding = false;
                    }
                } catch (NumberFormatException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Invalid Input");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Please enter valid numerical values for quantity and rental fee.");
                    errorAlert.showAndWait();
                }
            } while (continueAdding);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void updateItem() {
        String fileName = "src/main/items.txt"; // Replace with the actual file path

        try (BufferedReader br = new BufferedReader(new FileReader(fileName));
             PrintWriter pw = new PrintWriter(new FileWriter(fileName + ".tmp"))) {

            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Update Item");
            dialog.setHeaderText(null);
            dialog.setContentText("Enter the ID of the item you want to update:");
            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                String searchID = result.get();
                String line;
                boolean found = false;

                while ((line = br.readLine()) != null) {
                    String[] itemData = line.split(",");
                    String ID = itemData[0];

                    if (ID.equalsIgnoreCase(searchID)) {
                        found = true;

                        ChoiceDialog<String> fieldDialog = new ChoiceDialog<>("1", "1", "2", "3", "4", "5", "6");
                        fieldDialog.setTitle("Update Item");
                        fieldDialog.setHeaderText(null);
                        fieldDialog.setContentText("Choose a field to update:\n" +
                                "1. Title\n" +
                                "2. Rental Type\n" +
                                "3. Loan Type\n" +
                                "4. Quantity\n" +
                                "5. Rental Fee\n" +
                                "6. Genre");
                        Optional<String> fieldResult = fieldDialog.showAndWait();

                        if (fieldResult.isPresent()) {
                            int choice = Integer.parseInt(fieldResult.get());

                            TextInputDialog newValueDialog = new TextInputDialog();
                            newValueDialog.setTitle("Update Item");
                            newValueDialog.setHeaderText(null);

                            switch (choice) {
                                case 1:
                                    newValueDialog.setContentText("Enter the new title:");
                                    break;
                                case 2:
                                    newValueDialog.setContentText("Enter the new rental type ('Record', 'DVD', or 'Game'):");
                                    break;
                                case 3:
                                    newValueDialog.setContentText("Enter the new loan type ('1-week' or '2-day'):");
                                    break;
                                case 4:
                                    newValueDialog.setContentText("Enter the new quantity:");
                                    break;
                                case 5:
                                    newValueDialog.setContentText("Enter the new rental fee:");
                                    break;
                                case 6:
                                    newValueDialog.setContentText("Enter the new genre:");
                                    break;
                                default:
                                    Alert invalidChoiceAlert = new Alert(Alert.AlertType.ERROR);
                                    invalidChoiceAlert.setTitle("Invalid Choice");
                                    invalidChoiceAlert.setHeaderText(null);
                                    invalidChoiceAlert.setContentText("Invalid choice!");
                                    invalidChoiceAlert.showAndWait();
                                    continue;
                            }

                            Optional<String> newValueResult = newValueDialog.showAndWait();

                            if (newValueResult.isPresent()) {
                                String newValue = newValueResult.get();

                                switch (choice) {
                                    case 1:
                                        itemData[1] = newValue;
                                        break;
                                    case 2:
                                        if (!newValue.equalsIgnoreCase("Record") &&
                                                !newValue.equalsIgnoreCase("DVD") &&
                                                !newValue.equalsIgnoreCase("Game")) {
                                            Alert invalidRentalTypeAlert = new Alert(Alert.AlertType.ERROR);
                                            invalidRentalTypeAlert.setTitle("Invalid Rental Type");
                                            invalidRentalTypeAlert.setHeaderText(null);
                                            invalidRentalTypeAlert.setContentText("Please enter 'Record', 'DVD', or 'Game'.");
                                            invalidRentalTypeAlert.showAndWait();
                                            continue;
                                        }
                                        itemData[2] = newValue;
                                        break;
                                    case 3:
                                        if (!newValue.equalsIgnoreCase("1-week") &&
                                                !newValue.equalsIgnoreCase("2-day")) {
                                            Alert invalidLoanTypeAlert = new Alert(Alert.AlertType.ERROR);
                                            invalidLoanTypeAlert.setTitle("Invalid Loan Type");
                                            invalidLoanTypeAlert.setHeaderText(null);
                                            invalidLoanTypeAlert.setContentText("Please enter '1-week' or '2-day'.");
                                            invalidLoanTypeAlert.showAndWait();
                                            continue;
                                        }
                                        itemData[3] = newValue;
                                        break;
                                    case 4:
                                        if (!newValue.matches("\\d+")) {
                                            Alert invalidQuantityAlert = new Alert(Alert.AlertType.ERROR);
                                            invalidQuantityAlert.setTitle("Invalid Quantity");
                                            invalidQuantityAlert.setHeaderText(null);
                                            invalidQuantityAlert.setContentText("Invalid input. Please enter a valid quantity.");
                                            invalidQuantityAlert.showAndWait();
                                            continue;
                                        }
                                        itemData[4] = newValue;
                                        break;
                                    case 5:
                                        if (!newValue.matches("\\d+(\\.\\d+)?")) {
                                            Alert invalidRentalFeeAlert = new Alert(Alert.AlertType.ERROR);
                                            invalidRentalFeeAlert.setTitle("Invalid Rental Fee");
                                            invalidRentalFeeAlert.setHeaderText(null);
                                            invalidRentalFeeAlert.setContentText("Invalid input. Please enter a valid rental fee.");
                                            invalidRentalFeeAlert.showAndWait();
                                            continue;
                                        }
                                        itemData[5] = newValue;
                                        break;
                                    case 6:
                                        if (itemData[6].equalsIgnoreCase("Record") || itemData[6].equalsIgnoreCase("DVD")) {
                                            if (itemData[6].equalsIgnoreCase("Game")) {
                                                Alert genreUpdateNotAllowedAlert = new Alert(Alert.AlertType.ERROR);
                                                genreUpdateNotAllowedAlert.setTitle("Genre Update Not Allowed");
                                                genreUpdateNotAllowedAlert.setHeaderText(null);
                                                genreUpdateNotAllowedAlert.setContentText("Genre cannot be updated for Game items.");
                                                genreUpdateNotAllowedAlert.showAndWait();
                                                continue;
                                            } else {
                                                itemData[6] = newValue;
                                            }
                                        } else {
                                            Alert genreUpdateNotAllowedAlert = new Alert(Alert.AlertType.ERROR);
                                            genreUpdateNotAllowedAlert.setTitle("Genre Update Not Allowed");
                                            genreUpdateNotAllowedAlert.setHeaderText(null);
                                            genreUpdateNotAllowedAlert.setContentText("Genre can only be updated for Record/DVD items.");
                                            genreUpdateNotAllowedAlert.showAndWait();
                                            continue;
                                        }
                                        break;
                                }

                                line = String.join(",", itemData); // Update the line
                            }
                        }
                    }
                    pw.println(line);
                }

                if (!found) {
                    Alert itemNotFoundAlert = new Alert(Alert.AlertType.INFORMATION);
                    itemNotFoundAlert.setTitle("Item Not Found");
                    itemNotFoundAlert.setHeaderText(null);
                    itemNotFoundAlert.setContentText("No item found with the specified ID.");
                    itemNotFoundAlert.showAndWait();
                }

                br.close();
                pw.close();

                // Rename the temporary file to the original file
                File originalFile = new File(fileName);
                File tmpFile = new File(fileName + ".tmp");
                if (originalFile.delete()) {
                    if (!tmpFile.renameTo(originalFile)) {
                        Alert renameFileFailedAlert = new Alert(Alert.AlertType.ERROR);
                        renameFileFailedAlert.setTitle("Rename File Failed");
                        renameFileFailedAlert.setHeaderText(null);
                        renameFileFailedAlert.setContentText("Failed to rename the temporary file.");
                        renameFileFailedAlert.showAndWait();
                    }
                } else {
                    Alert deleteFileFailedAlert = new Alert(Alert.AlertType.ERROR);
                    deleteFileFailedAlert.setTitle("Delete File Failed");
                    deleteFileFailedAlert.setHeaderText(null);
                    deleteFileFailedAlert.setContentText("Failed to delete the original file.");
                    deleteFileFailedAlert.showAndWait();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteItem() {
        String itemID = itemIDTextField.getText();
        String fileName = "src/main/items.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName));
             PrintWriter pw = new PrintWriter(new FileWriter(fileName + ".tmp"))) {

            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] itemData = line.split(",");
                String ID = itemData[0];
                if (ID.equalsIgnoreCase(itemID)) {
                    found = true;
                    continue; // Skip writing the line to the temporary file (effectively deleting the item)
                }
                pw.println(line);
            }

            if (found) {
                // Delete the original file
                Files.delete(Path.of(fileName));
                // Rename the temporary file to the original file
                Files.move(Path.of(fileName + ".tmp"), Path.of(fileName), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Item deleted successfully.");
            } else {
                System.out.println("No item found with the specified ID.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void displayAllItems() {
        String fileName = "src/main/items.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            VBox itemsVBox = new VBox(); // Create a new VBox for the items

            while ((line = br.readLine()) != null) {
                String[] itemData = line.split(",");

                // Create labels for each item property
                Label idLabel = new Label("Item ID: " + itemData[0]);
                Label titleLabel = new Label("Title: " + itemData[1]);
                Label rentalTypeLabel = new Label("Rental Type: " + itemData[2]);
                Label loanTypeLabel = new Label("Loan Type: " + itemData[3]);
                Label quantityLabel = new Label("Quantity: " + itemData[4]);
                Label rentalFeeLabel = new Label("Rental Fee: " + itemData[5]);
                Label genreLabel = null;

                if (itemData.length > 6) {
                    genreLabel = new Label("Genre: " + itemData[6]);
                }

                // Add labels to the VBox
                itemsVBox.getChildren().addAll(idLabel, titleLabel, rentalTypeLabel,
                        loanTypeLabel, quantityLabel, rentalFeeLabel);

                if (genreLabel != null) {
                    itemsVBox.getChildren().add(genreLabel);
                }

                // Add a separator
                Separator separator = new Separator();
                separator.setPadding(new Insets(5));
                itemsVBox.getChildren().add(separator);
            }

            // Create a new window to display the items
            Stage stage = new Stage();
            stage.setTitle("Items");
            Scene scene = new Scene(itemsVBox, 400, 400);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addCustomer() {
        String fileName = "src/main/customers.txt";

        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, true))) {
            String ID = customerIDField.getText();
            String name = nameField.getText();
            String address = addressField.getText();
            String phone = phoneField.getText();
            int numRentals = Integer.parseInt(rentalsField.getText());
            String customerType = customerTypeField.getText();
            String username = usernameField.getText();
            String password = customerpasswordField.getText();

            // Write customer data to file
            pw.printf("%s,%s,%s,%s,%d,%s,%s,%s\n", ID, name, address, phone, numRentals, customerType, username, password);

            // Clear input fields
            customerIDField.clear();
            nameField.clear();
            addressField.clear();
            phoneField.clear();
            rentalsField.clear();
            customerTypeField.clear();
            usernameField.clear();
            customerpasswordField.clear();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Customer Added");
            alert.setHeaderText(null);
            alert.setContentText("The customer has been added to the database.");
            alert.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    public void updateCustomer() {
        String fileName = "src/main/customers.txt";
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Update Customer");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the ID of the customer you want to update:");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String searchID = result.get();

            try (BufferedReader br = new BufferedReader(new FileReader(fileName));
                 PrintWriter pw = new PrintWriter(new FileWriter(fileName + ".tmp"))) {

                String line;
                boolean found = false;

                while ((line = br.readLine()) != null) {
                    String[] customerData = line.split(",");
                    String ID = customerData[0];

                    if (ID.equalsIgnoreCase(searchID)) {
                        found = true;

                        ChoiceDialog<String> fieldDialog = new ChoiceDialog<>("name", "name", "address", "phone number", "number of rentals", "customer type", "username", "password");
                        fieldDialog.setTitle("Update Customer");
                        fieldDialog.setHeaderText(null);
                        fieldDialog.setContentText("Choose a field to update:");
                        Optional<String> fieldResult = fieldDialog.showAndWait();

                        if (fieldResult.isPresent()) {
                            String fieldToUpdate = fieldResult.get();

                            TextInputDialog newValueDialog = new TextInputDialog();
                            newValueDialog.setTitle("Update Customer");
                            newValueDialog.setHeaderText(null);

                            switch (fieldToUpdate.toLowerCase()) {
                                case "name":
                                    newValueDialog.setContentText("Enter the new name:");
                                    break;
                                case "address":
                                    newValueDialog.setContentText("Enter the new address:");
                                    break;
                                case "phone number":
                                    newValueDialog.setContentText("Enter the new phone number:");
                                    break;
                                case "number of rentals":
                                    newValueDialog.setContentText("Enter the new number of rentals:");
                                    break;
                                case "customer type":
                                    newValueDialog.setContentText("Enter the new customer type:");
                                    break;
                                case "username":
                                    newValueDialog.setContentText("Enter the new username:");
                                    break;
                                case "password":
                                    newValueDialog.setContentText("Enter the new password:");
                                    break;
                                default:
                                    Alert invalidChoiceAlert = new Alert(Alert.AlertType.ERROR);
                                    invalidChoiceAlert.setTitle("Invalid Field");
                                    invalidChoiceAlert.setHeaderText(null);
                                    invalidChoiceAlert.setContentText("Invalid field to update.");
                                    invalidChoiceAlert.showAndWait();
                                    continue;
                            }

                            Optional<String> newValueResult = newValueDialog.showAndWait();

                            if (newValueResult.isPresent()) {
                                String newValue = newValueResult.get();

                                switch (fieldToUpdate.toLowerCase()) {
                                    case "name":
                                        customerData[1] = newValue;
                                        break;
                                    case "address":
                                        customerData[2] = newValue;
                                        break;
                                    case "phone number":
                                        customerData[3] = newValue;
                                        break;
                                    case "number of rentals":
                                        customerData[4] = newValue;
                                        break;
                                    case "customer type":
                                        customerData[5] = newValue;
                                        break;
                                    case "username":
                                        customerData[6] = newValue;
                                        break;
                                    case "password":
                                        customerData[7] = newValue;
                                        break;
                                }

                                line = String.join(",", customerData); // Update the line
                            }
                        }
                    }
                    pw.println(line);
                }

                if (!found) {
                    Alert customerNotFoundAlert = new Alert(Alert.AlertType.INFORMATION);
                    customerNotFoundAlert.setTitle("Customer Not Found");
                    customerNotFoundAlert.setHeaderText(null);
                    customerNotFoundAlert.setContentText("No customer found with the specified ID.");
                    customerNotFoundAlert.showAndWait();
                }

                br.close();
                pw.close();

                // Rename the temporary file to the original file
                File originalFile = new File(fileName);
                File tmpFile = new File(fileName + ".tmp");
                if (originalFile.delete()) {
                    if (!tmpFile.renameTo(originalFile)) {
                        Alert renameFileFailedAlert = new Alert(Alert.AlertType.ERROR);
                        renameFileFailedAlert.setTitle("Rename File Failed");
                        renameFileFailedAlert.setHeaderText(null);
                        renameFileFailedAlert.setContentText("Failed to rename the temporary file.");
                        renameFileFailedAlert.showAndWait();
                    }
                } else {
                    Alert deleteFileFailedAlert = new Alert(Alert.AlertType.ERROR);
                    deleteFileFailedAlert.setTitle("Delete File Failed");
                    deleteFileFailedAlert.setHeaderText(null);
                    deleteFileFailedAlert.setContentText("Failed to delete the original file.");
                    deleteFileFailedAlert.showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public int getCustomerTypeLevel(String type) {
        if (type.equalsIgnoreCase("Guest")) {
            return 0;
        } else if (type.equalsIgnoreCase("Regular")) {
            return 1;
        } else if (type.equalsIgnoreCase("VIP")) {
            return 2;
        }
        return -1; // Invalid type
    }


    @FXML
    public void displayItemsNoStock() {
        String fileName = "src/main/items.txt"; // Replace with the actual file path
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            VBox itemsVBox = new VBox(); // Create a new VBox for the items

            while ((line = br.readLine()) != null) {
                String[] itemData = line.split(",");
                int quantity = Integer.parseInt(itemData[4]);

                if (quantity == 0) {
                    Label itemIdLabel = new Label("Item ID: " + itemData[0]);
                    Label titleLabel = new Label("Title: " + itemData[1]);
                    Label rentalTypeLabel = new Label("Rental Type: " + itemData[2]);
                    Label loanTypeLabel = new Label("Loan Type: " + itemData[3]);
                    Label quantityLabel = new Label("Quantity: " + itemData[4]);
                    Label rentalFeeLabel = new Label("Rental Fee: " + itemData[5]);

                    Label genreLabel = null;
                    if (itemData.length > 6) {
                        genreLabel = new Label("Genre: " + itemData[6]);
                    }

                    Label separatorLabel = new Label("---------------");

                    // Add the labels to the itemsVBox
                    itemsVBox.getChildren().addAll(
                            itemIdLabel,
                            titleLabel,
                            rentalTypeLabel,
                            loanTypeLabel,
                            quantityLabel,
                            rentalFeeLabel
                    );

                    if (genreLabel != null) {
                        itemsVBox.getChildren().add(genreLabel);
                    }

                    itemsVBox.getChildren().add(separatorLabel);
                }
            }

            // Create a ScrollPane and set its content to the itemsVBox
            ScrollPane scrollPane = new ScrollPane(itemsVBox);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);

            // Create a new window to display the items
            Stage stage = new Stage();
            stage.setTitle("Items with No Stock");
            Scene scene = new Scene(scrollPane, 400, 400);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void displayAllCustomersSortedByID() {
        String fileName = "src/main/customers.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            List<String> lines = new ArrayList<>();

            // Read all lines from the file
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

            // Sort the lines based on customer IDs
            Collections.sort(lines, Comparator.comparingInt(this::getCustomerIDFromLine));

            Stage stage = new Stage(); // Create a new stage for the new window
            stage.setResizable(true); // Make the window resizable

            VBox vbox = new VBox(); // Create a new VBox to hold the customer information
            vbox.setSpacing(10); // Set the spacing between labels

            for (String sortedLine : lines) {
                String[] customerData = sortedLine.split(",");

                // Check if the line has the expected number of values
                if (customerData.length >= 8) {
                    // Create labels for each customer property
                    Label customerIDLabel = new Label("Customer ID: " + customerData[0]);
                    Label nameLabel = new Label("Name: " + customerData[1]);
                    Label addressLabel = new Label("Address: " + customerData[2]);
                    Label phoneLabel = new Label("Phone: " + customerData[3]);
                    Label numOfRentalsLabel = new Label("Number of Rentals: " + customerData[4]);
                    Label accountTypeLabel = new Label("Account Type: " + customerData[5]);
                    Label usernameLabel = new Label("Username: " + customerData[6]);
                    Label passwordLabel = new Label("Password: " + customerData[7]);
                    Label rentalsLabel = null;

                    if (customerData.length > 8) {
                        StringBuilder rentalsBuilder = new StringBuilder();
                        for (int i = 8; i < customerData.length; i++) {
                            rentalsBuilder.append(customerData[i]);
                            if (i < customerData.length - 1) {
                                rentalsBuilder.append(", ");
                            }
                        }
                        rentalsLabel = new Label("Rentals: " + rentalsBuilder.toString());
                    }

                    // Add labels to the VBox
                    vbox.getChildren().addAll(customerIDLabel, nameLabel, addressLabel, phoneLabel,
                            numOfRentalsLabel, accountTypeLabel, usernameLabel, passwordLabel);

                    if (rentalsLabel != null) {
                        vbox.getChildren().add(rentalsLabel);
                    }

                    // Add a separator
                    Separator separator = new Separator();
                    separator.setPadding(new Insets(5));
                    vbox.getChildren().add(separator);
                } else {
                    // Handle error or skip the line
                    System.out.println("Invalid line: " + sortedLine);
                }
            }

            ScrollPane scrollPane = new ScrollPane(vbox); // Wrap the VBox in a ScrollPane
            scrollPane.setFitToWidth(true); // Allow the ScrollPane to resize horizontally
            scrollPane.setFitToHeight(true); // Allow the ScrollPane to resize vertically

            // Create a new scene with the ScrollPane
            Scene scene = new Scene(scrollPane);

            stage.setScene(scene); // Set the scene in the stage
            stage.setTitle("Customer Information (Sorted by ID)"); // Set the window title
            stage.show(); // Display the new window
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getCustomerIDFromLine(String line) {
        String[] customerData = line.split(",");
        if (customerData.length >= 1) {
            try {
                return Integer.parseInt(customerData[0]); // Assuming the ID is at index 0
            } catch (NumberFormatException e) {
                // Handle parsing error
            }
        }
        return -1; // Return a default value for invalid or missing IDs
    }
    @FXML
    public void displayAllCustomersSortedByName() {
        String fileName = "src/main/customers.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            List<String> lines = new ArrayList<>();

            // Read all lines from the file
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

            // Sort the lines based on customer names
            Collections.sort(lines, Comparator.comparing(this::getCustomerNameFromLine));

            Stage stage = new Stage(); // Create a new stage for the new window
            stage.setResizable(true); // Make the window resizable

            VBox vbox = new VBox(); // Create a new VBox to hold the customer information
            vbox.setSpacing(10); // Set the spacing between labels

            for (String sortedLine : lines) {
                String[] customerData = sortedLine.split(",");

                // Check if the line has the expected number of values
                if (customerData.length >= 8) {
                    // Create labels for each customer property
                    Label customerIDLabel = new Label("Customer ID: " + customerData[0]);
                    Label nameLabel = new Label("Name: " + customerData[1]);
                    Label addressLabel = new Label("Address: " + customerData[2]);
                    Label phoneLabel = new Label("Phone: " + customerData[3]);
                    Label numOfRentalsLabel = new Label("Number of Rentals: " + customerData[4]);
                    Label accountTypeLabel = new Label("Account Type: " + customerData[5]);
                    Label usernameLabel = new Label("Username: " + customerData[6]);
                    Label passwordLabel = new Label("Password: " + customerData[7]);
                    Label rentalsLabel = null;

                    if (customerData.length > 8) {
                        StringBuilder rentalsBuilder = new StringBuilder();
                        for (int i = 8; i < customerData.length; i++) {
                            rentalsBuilder.append(customerData[i]);
                            if (i < customerData.length - 1) {
                                rentalsBuilder.append(", ");
                            }
                        }
                        rentalsLabel = new Label("Rentals: " + rentalsBuilder.toString());
                    }

                    // Add labels to the VBox
                    vbox.getChildren().addAll(customerIDLabel, nameLabel, addressLabel, phoneLabel,
                            numOfRentalsLabel, accountTypeLabel, usernameLabel, passwordLabel);

                    if (rentalsLabel != null) {
                        vbox.getChildren().add(rentalsLabel);
                    }

                    // Add a separator
                    Separator separator = new Separator();
                    separator.setPadding(new Insets(5));
                    vbox.getChildren().add(separator);
                } else {
                    // Handle error or skip the line
                    System.out.println("Invalid line: " + sortedLine);
                }
            }

            ScrollPane scrollPane = new ScrollPane(vbox); // Wrap the VBox in a ScrollPane
            scrollPane.setFitToWidth(true); // Allow the ScrollPane to resize horizontally
            scrollPane.setFitToHeight(true); // Allow the ScrollPane to resize vertically

            // Create a new scene with the ScrollPane
            Scene scene = new Scene(scrollPane);

            stage.setScene(scene); // Set the scene in the stage
            stage.setTitle("Customer Information (Sorted by Name)"); // Set the window title
            stage.show(); // Display the new window
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCustomerNameFromLine(String line) {
        String[] customerData = line.split(",");
        if (customerData.length >= 2) {
            return customerData[1]; // Assuming the name is at index 1
        }
        return ""; // Return an empty string if the line doesn't have the expected format
    }
    @FXML
    public void displayAllCustomers() {
        String fileName = "src/main/customers.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            Stage stage = new Stage(); // Create a new stage for the new window
            stage.setResizable(true); // Make the window resizable

            VBox vbox = new VBox(); // Create a new VBox to hold the customer information
            vbox.setSpacing(10); // Set the spacing between labels

            String line;

            while ((line = br.readLine()) != null) {
                String[] customerData = line.split(",");

                // Check if the line has the expected number of values
                if (customerData.length >= 8) {
                    // Create labels for each customer property
                    Label customerIDLabel = new Label("Customer ID: " + customerData[0]);
                    Label nameLabel = new Label("Name: " + customerData[1]);
                    Label addressLabel = new Label("Address: " + customerData[2]);
                    Label phoneLabel = new Label("Phone: " + customerData[3]);
                    Label numOfRentalsLabel = new Label("Number of Rentals: " + customerData[4]);
                    Label accountTypeLabel = new Label("Account Type: " + customerData[5]);
                    Label usernameLabel = new Label("Username: " + customerData[6]);
                    Label passwordLabel = new Label("Password: " + customerData[7]);
                    Label rentalsLabel = null;

                    if (customerData.length > 8) {
                        StringBuilder rentalsBuilder = new StringBuilder();
                        for (int i = 8; i < customerData.length; i++) {
                            rentalsBuilder.append(customerData[i]);
                            if (i < customerData.length - 1) {
                                rentalsBuilder.append(", ");
                            }
                        }
                        rentalsLabel = new Label("Rentals: " + rentalsBuilder.toString());
                    }

                    // Add labels to the VBox
                    vbox.getChildren().addAll(customerIDLabel, nameLabel, addressLabel, phoneLabel,
                            numOfRentalsLabel, accountTypeLabel, usernameLabel, passwordLabel);

                    if (rentalsLabel != null) {
                        vbox.getChildren().add(rentalsLabel);
                    }

                    // Add a separator
                    Separator separator = new Separator();
                    separator.setPadding(new Insets(5));
                    vbox.getChildren().add(separator);
                }
            }

            ScrollPane scrollPane = new ScrollPane(vbox); // Wrap the VBox in a ScrollPane
            scrollPane.setFitToWidth(true); // Allow the ScrollPane to resize horizontally
            scrollPane.setFitToHeight(true); // Allow the ScrollPane to resize vertically

            // Create a new scene with the ScrollPane
            Scene scene = new Scene(scrollPane);

            stage.setScene(scene); // Set the scene in the stage
            stage.setTitle("Customer Information"); // Set the window title
            stage.show(); // Display the new window
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private ComboBox<String> customerTypeComboBox;

    @FXML
    public void displayCusbylevel() {
        String fileName = "src/main/customers.txt";
        String customerTypeToDisplay = customerTypeComboBox.getValue();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            Stage stage = new Stage(); // Create a new stage for the new window
            stage.setResizable(true); // Make the window resizable

            VBox vbox = new VBox(); // Create a new VBox to hold the customer information
            vbox.setSpacing(10); // Set the spacing between labels

            String line;
            boolean foundCustomers = false;

            while ((line = br.readLine()) != null) {
                String[] customerData = line.split(",");

                // Check if the line has the expected number of values
                if (customerData.length >= 8) {
                    String customerType = customerData[5];

                    if (customerType.equalsIgnoreCase(customerTypeToDisplay)) {
                        // Create labels for each customer property
                        Label customerIDLabel = new Label("Customer ID: " + customerData[0]);
                        Label nameLabel = new Label("Name: " + customerData[1]);
                        Label addressLabel = new Label("Address: " + customerData[2]);
                        Label phoneLabel = new Label("Phone: " + customerData[3]);
                        Label numOfRentalsLabel = new Label("Number of Rentals: " + customerData[4]);
                        Label accountTypeLabel = new Label("Account Type: " + customerData[5]);
                        Label usernameLabel = new Label("Username: " + customerData[6]);
                        Label passwordLabel = new Label("Password: " + customerData[7]);
                        Label rentalsLabel = null;

                        if (customerData.length > 8) {
                            StringBuilder rentalsBuilder = new StringBuilder();
                            for (int i = 8; i < customerData.length; i++) {
                                rentalsBuilder.append(customerData[i]);
                                if (i < customerData.length - 1) {
                                    rentalsBuilder.append(", ");
                                }
                            }
                            rentalsLabel = new Label("Rentals: " + rentalsBuilder.toString());
                        }

                        // Add labels to the VBox
                        vbox.getChildren().addAll(customerIDLabel, nameLabel, addressLabel, phoneLabel,
                                numOfRentalsLabel, accountTypeLabel, usernameLabel, passwordLabel);

                        if (rentalsLabel != null) {
                            vbox.getChildren().add(rentalsLabel);
                        }

                        // Add a separator
                        Separator separator = new Separator();
                        separator.setPadding(new Insets(5));
                        vbox.getChildren().add(separator);

                        foundCustomers = true;
                    }
                }
            }

            if (!foundCustomers) {
                Label noCustomersLabel = new Label("No customers found with the specified type: " + customerTypeToDisplay);
                vbox.getChildren().add(noCustomersLabel);
            }

            ScrollPane scrollPane = new ScrollPane(vbox); // Wrap the VBox in a ScrollPane
            scrollPane.setFitToWidth(true); // Allow the ScrollPane to resize horizontally
            scrollPane.setFitToHeight(true); // Allow the ScrollPane to resize vertically
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Enable vertical scroll bar
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Disable horizontal scroll bar

            // Create a new scene with the ScrollPane
            Scene scene = new Scene(scrollPane);

            stage.setScene(scene); // Set the scene in the stage
            stage.setTitle("Customers of Type: " + customerTypeToDisplay); // Set the window title
            stage.show(); // Display the new window
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void searchItem() {
        String fileName = "src/main/items.txt";
        BufferedReader br = null;
        boolean continueSearch = true;

        Stage stage = new Stage(); // Create a new stage for the new window
        stage.setResizable(true); // Make the window resizable

        VBox vbox = new VBox(); // Create a new VBox to hold the item information
        vbox.setSpacing(10); // Set the spacing between labels

        do {
            try {
                br = new BufferedReader(new FileReader(fileName));

                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Search Item");
                dialog.setHeaderText("Enter the ID of the item you want to search:");
                Optional<String> result = dialog.showAndWait();

                if (result.isPresent()) {
                    String searchID = result.get();
                    String line;
                    boolean found = false;

                    while ((line = br.readLine()) != null) {
                        String[] itemData = line.split(",");
                        String ID = itemData[0];
                        if (ID.equalsIgnoreCase(searchID)) {
                            found = true;
                            Label itemIDLabel = new Label("Item ID: " + itemData[0]);
                            Label titleLabel = new Label("Title: " + itemData[1]);
                            Label rentalTypeLabel = new Label("Rental Type: " + itemData[2]);
                            Label loanTypeLabel = new Label("Loan Type: " + itemData[3]);
                            Label quantityLabel = new Label("Quantity: " + itemData[4]);
                            Label rentalFeeLabel = new Label("Rental Fee: " + itemData[5]);
                            Label genreLabel = null;

                            if (itemData.length > 6) {
                                genreLabel = new Label("Genre: " + itemData[6]);
                            }

                            // Add labels to the VBox
                            vbox.getChildren().addAll(itemIDLabel, titleLabel, rentalTypeLabel, loanTypeLabel,
                                    quantityLabel, rentalFeeLabel);

                            if (genreLabel != null) {
                                vbox.getChildren().add(genreLabel);
                            }

                            // Add a separator
                            Separator separator = new Separator();
                            separator.setPadding(new Insets(5));
                            vbox.getChildren().add(separator);

                            break; // Exit the loop after finding the item
                        }
                    }

                    if (!found) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Item Not Found");
                        alert.setHeaderText(null);
                        alert.setContentText("No item found with the specified ID.");
                        alert.showAndWait();
                    }
                }

                Alert continueAlert = new Alert(Alert.AlertType.CONFIRMATION);
                continueAlert.setTitle("Continue Search");
                continueAlert.setHeaderText(null);
                continueAlert.setContentText("Do you want to search for another item?");
                Optional<ButtonType> continueResult = continueAlert.showAndWait();

                if (continueResult.isPresent() && continueResult.get() != ButtonType.OK) {
                    continueSearch = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } while (continueSearch);

        ScrollPane scrollPane = new ScrollPane(vbox); // Wrap the VBox in a ScrollPane
        scrollPane.setFitToWidth(true); // Allow the ScrollPane to resize horizontally
        scrollPane.setFitToHeight(true); // Allow the ScrollPane to resize vertically

        // Create a new scene with the ScrollPane
        Scene scene = new Scene(scrollPane);

        stage.setScene(scene); // Set the scene in the stage
        stage.setTitle("Item Information"); // Set the window title
        stage.show(); // Display the new window
    }


    @FXML
    public void searchItemByTitle() {
        String fileName = "src/main/items.txt";
        BufferedReader br = null;
        boolean continueSearch = true;

        Stage stage = new Stage(); // Create a new stage for the new window
        stage.setResizable(true); // Make the window resizable

        VBox vbox = new VBox(); // Create a new VBox to hold the item information
        vbox.setSpacing(10); // Set the spacing between labels

        do {
            try {
                br = new BufferedReader(new FileReader(fileName));

                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Search Item");
                dialog.setHeaderText("Enter the title of the item you want to search:");
                Optional<String> result = dialog.showAndWait();

                if (result.isPresent()) {
                    String searchTitle = result.get();
                    String line;
                    boolean found = false;

                    while ((line = br.readLine()) != null) {
                        String[] itemData = line.split(",");
                        String title = itemData[1];
                        if (title.equalsIgnoreCase(searchTitle)) {
                            found = true;
                            Label itemIDLabel = new Label("Item ID: " + itemData[0]);
                            Label titleLabel = new Label("Title: " + itemData[1]);
                            Label rentalTypeLabel = new Label("Rental Type: " + itemData[2]);
                            Label loanTypeLabel = new Label("Loan Type: " + itemData[3]);
                            Label quantityLabel = new Label("Quantity: " + itemData[4]);
                            Label rentalFeeLabel = new Label("Rental Fee: " + itemData[5]);
                            Label genreLabel = null;

                            if (itemData.length > 6) {
                                genreLabel = new Label("Genre: " + itemData[6]);
                            }

                            // Add labels to the VBox
                            vbox.getChildren().addAll(itemIDLabel, titleLabel, rentalTypeLabel, loanTypeLabel,
                                    quantityLabel, rentalFeeLabel);

                            if (genreLabel != null) {
                                vbox.getChildren().add(genreLabel);
                            }

                            // Add a separator
                            Separator separator = new Separator();
                            separator.setPadding(new Insets(5));
                            vbox.getChildren().add(separator);

                            break; // Exit the loop after finding the item
                        }
                    }

                    if (!found) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Item Not Found");
                        alert.setHeaderText(null);
                        alert.setContentText("No item found with the specified title.");
                        alert.showAndWait();
                    }
                }

                Alert continueAlert = new Alert(Alert.AlertType.CONFIRMATION);
                continueAlert.setTitle("Continue Search");
                continueAlert.setHeaderText(null);
                continueAlert.setContentText("Do you want to search for another item?");
                Optional<ButtonType> continueResult = continueAlert.showAndWait();

                if (continueResult.isPresent() && continueResult.get() != ButtonType.OK) {
                    continueSearch = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } while (continueSearch);

        ScrollPane scrollPane = new ScrollPane(vbox); // Wrap the VBox in a ScrollPane
        scrollPane.setFitToWidth(true); // Allow the ScrollPane to resize horizontally
        scrollPane.setFitToHeight(true); // Allow the ScrollPane to resize vertically

        // Create a new scene with the ScrollPane
        Scene scene = new Scene(scrollPane);

        stage.setScene(scene); // Set the scene in the stage
        stage.setTitle("Item Information"); // Set the window title
        stage.show(); // Display the new window
    }
    File FILE_NAME = new File("src/main/rentorders.txt");
    private List<String> readRentOrders() {
        List<String> rentOrders = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                rentOrders.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rentOrders;
    }
    private void deleteRentOrder() {
        String customerID = customerIDField.getText();
        String itemID = itemIDField.getText();

        List<String> rentOrders = readRentOrders();

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (String rentOrder : rentOrders) {
                String[] parts = rentOrder.split(",");
                String storedCustomerID = parts[0];
                String storedItemID = parts[1];

                if (!storedCustomerID.equals(customerID) || !storedItemID.equals(itemID)) {
                    writer.println(rentOrder);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Clear input fields
        customerIDField.clear();
        itemIDField.clear();
    }

    @FXML
    public void addCusRentItem() {
        try {
            String customerId = customerIDField.getText();
            String itemId = itemIDField.getText();

            // Read the contents of the customers.txt file
            File customerFile = new File("src/main/customers.txt");
            Scanner customerScanner = new Scanner(customerFile);
            StringBuilder customerSb = new StringBuilder();
            boolean customerExists = false;

            // Iterate through each line in the customer file and update the rented item list and number of rentals
            while (customerScanner.hasNextLine()) {
                String line = customerScanner.nextLine();
                String[] customerData = line.split(",");
                String customerIdKey = customerData[0];

                if (customerIdKey.equals(customerId)) {
                    customerExists = true;
                    List<String> itemList = new ArrayList<>();

                    // Start from index 1 to skip the customer ID
                    for (int i = 1; i < customerData.length; i++) {
                        itemList.add(customerData[i]);
                    }

                    if (itemList.contains(itemId)) {
                        displayErrorAlert("Customer has already rented this item. Failed to add rented item.");
                        customerScanner.close();
                        return;
                    }

                    itemList.add(itemId);

                    int numRentalsIndex = 4; // Index of number of rentals in the customer data array
                    int numRentals = Integer.parseInt(customerData[numRentalsIndex]);
                    numRentals++;
                    customerData[numRentalsIndex] = String.valueOf(numRentals);

                    // Reconstruct the customer line with updated rented item list and number of rentals
                    StringBuilder lineBuilder = new StringBuilder(customerIdKey);
                    for (String item : itemList) {
                        lineBuilder.append(",").append(item);
                    }
                    customerSb.append(lineBuilder.toString()).append("\n");
                } else {
                    // Append the original line as is
                    customerSb.append(line).append("\n");
                }
            }

            customerScanner.close();

            // Write the modified customer contents back to the file
            FileWriter customerWriter = new FileWriter("src/main/customers.txt");
            customerWriter.write(customerSb.toString());
            customerWriter.close();

            if (customerExists) {
                displaySuccessAlert("Rented item added to the customer record successfully.");
                increaseTotalRentals(customerId);
                deleteRentOrder();
            } else {
                displayErrorAlert("Invalid customer ID. Failed to add rented item.");
            }

        } catch (IOException e) {
            displayErrorAlert("An error occurred while adding the rented item to the customer record: " + e.getMessage());
        }
    }

    @FXML
    public void increaseTotalRentals(String customerId) {
        try {
            File inputFile = new File("src/main/customers.txt");
            File tempFile = new File("src/main/tempCustomers.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] customerInfo = line.split(",");
                if (customerInfo[0].equals(customerId)) {
                    int numRentals = Integer.parseInt(customerInfo[4]); // Get the current number of rentals
                    numRentals++; // Increment the number of rentals by 1
                    customerInfo[4] = String.valueOf(numRentals); // Update the number of rentals
                    line = String.join(",", customerInfo);
                }
                writer.write(line);
                writer.newLine();
            }

            reader.close();
            writer.close();

            inputFile.delete();
            tempFile.renameTo(inputFile);

            displaySuccessAlert("Number of rentals for customer " + customerId + " has been increased.");
        } catch (IOException e) {
            displayErrorAlert("An error occurred while increasing the number of rentals: " + e.getMessage());
        }
    }



    @FXML
    public void returnRentedItem() {
        try {
            String customerId = customerIDField.getText();
            String itemId = itemIDField.getText();

            // Read the contents of the customers.txt file
            File customerFile = new File("src/main/customers.txt");
            Scanner customerScanner = new Scanner(customerFile);
            StringBuilder customerSb = new StringBuilder();
            boolean customerExists = false;

            // Iterate through each line in the customer file and update the rented item list and quantity
            while (customerScanner.hasNextLine()) {
                String line = customerScanner.nextLine();
                String[] customerData = line.split(",");
                String customerIdKey = customerData[0];

                if (customerIdKey.equals(customerId)) {
                    customerExists = true;
                    List<String> itemList = new ArrayList<>();

                    // Start from index 1 to skip the customer ID
                    for (int i = 1; i < customerData.length; i++) {
                        itemList.add(customerData[i]);
                    }

                    if (!itemList.contains(itemId)) {
                        displayErrorAlert("Customer has not rented this item. Failed to return the item.");
                        customerScanner.close();
                        return;
                    }

                    itemList.remove(itemId);

                    // Update the quantity of the returned item in items.txt file
                    if (updateItemQuantity(itemId, 1)) {
                        displayErrorAlert("An error occurred while updating the item quantity in items.txt.");
                        customerScanner.close();
                        return;
                    }

                    // Reconstruct the customer line with updated rented item list
                    StringBuilder lineBuilder = new StringBuilder(customerIdKey);
                    for (String item : itemList) {
                        lineBuilder.append(",").append(item);
                    }
                    customerSb.append(lineBuilder.toString()).append("\n");
                } else {
                    // Append the original line as is
                    customerSb.append(line).append("\n");
                }
            }

            customerScanner.close();

            // Write the modified customer contents back to the file
            FileWriter customerWriter = new FileWriter("src/main/customers.txt");
            customerWriter.write(customerSb.toString());
            customerWriter.close();

            if (customerExists) {
                displaySuccessAlert("Item returned successfully.");
                deleteReturnOrder(customerId, itemId);
            } else {
                displayErrorAlert("Invalid customer ID. Failed to return the item.");
            }

        } catch (IOException e) {
            displayErrorAlert("An error occurred while returning the item: " + e.getMessage());
        }
    }

    private boolean updateItemQuantity(String itemId, int quantityChange) {
        try {
            // Read the contents of the items.txt file
            File itemFile = new File("src/main/items.txt");
            Scanner itemScanner = new Scanner(itemFile);
            StringBuilder itemSb = new StringBuilder();
            boolean itemExists = false;

            // Iterate through each line in the items file and update the quantity of the item
            while (itemScanner.hasNextLine()) {
                String line = itemScanner.nextLine();
                String[] itemData = line.split(",");
                String itemIdKey = itemData[0];

                if (itemIdKey.equals(itemId)) {
                    itemExists = true;
                    int currentQuantity = Integer.parseInt(itemData[2]);
                    int updatedQuantity = currentQuantity + quantityChange;

                    // Update the quantity of the item
                    itemData[2] = String.valueOf(updatedQuantity);

                    // Reconstruct the item line with updated quantity
                    StringBuilder lineBuilder = new StringBuilder(itemIdKey);
                    for (int i = 1; i < itemData.length; i++) {
                        lineBuilder.append(",").append(itemData[i]);
                    }
                    itemSb.append(lineBuilder.toString()).append("\n");
                } else {
                    // Append the original line as is
                    itemSb.append(line).append("\n");
                }
            }

            itemScanner.close();

            // Write the modified item contents back to the file
            FileWriter itemWriter = new FileWriter("src/main/items.txt");
            itemWriter.write(itemSb.toString());
            itemWriter.close();

            return !itemExists; // Return true if the item was not found
        } catch (IOException e) {
            displayErrorAlert("An error occurred while updating the item quantity: " + e.getMessage());
            return true;
        }
    }

    private void deleteReturnOrder(String customerId, String itemId) {
        File returnOrdersFile = new File("src/main/returnorders.txt");
        List<String> returnOrders = readRentOrders(returnOrdersFile);

        try (PrintWriter writer = new PrintWriter(new FileWriter(returnOrdersFile))) {
            for (String returnOrder : returnOrders) {
                String[] parts = returnOrder.split(",");
                String storedCustomerId = parts[0];
                String storedItemId = parts[1];

                if (!storedCustomerId.equals(customerId) || !storedItemId.equals(itemId)) {
                    writer.println(returnOrder);
                }
            }
        } catch (IOException e) {
            displayErrorAlert("An error occurred while deleting the return order: " + e.getMessage());
        }

        // Clear input fields
        customerIDField.clear();
        itemIDField.clear();
    }

    private List<String> readRentOrders(File file) {
        List<String> rentOrders = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                rentOrders.add(line);
            }
        } catch (IOException e) {
            displayErrorAlert("An error occurred while reading the rent orders: " + e.getMessage());
        }

        return rentOrders;
    }
    private void displaySuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    //SearchCus


    @FXML
    public void searchCustomer() {
        String fileName = "src/main/customers.txt"; // Replace with the actual file path
        String searchID = customerIDField.getText();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));

            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] customerData = line.split(",");
                String ID = customerData[0];
                if (ID.equalsIgnoreCase(searchID)) {
                    found = true;
                    String result = "Customer ID: " + customerData[0] + "\n"
                            + "Name: " + customerData[1] + "\n"
                            + "Address: " + customerData[2] + "\n"
                            + "Phone: " + customerData[3] + "\n"
                            + "Membership Level: " + customerData[4] + "\n"
                            + "Username: " + customerData[5] + "\n"
                            + "Password: " + customerData[6];
                    resultLabel.setText(result);
                    break; // Exit the loop after finding the customer
                }
            }

            if (!found) {
                resultLabel.setText("No customer found with the specified ID.");
            }
        } catch (IOException e) {
            displayErrorAlert("An error occurred while searching for the customer: " + e.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                displayErrorAlert("An error occurred while closing the file: " + e.getMessage());
            }
        }
    }


    //searchCustomerByTitle()


    @FXML
    public void searchCustomerByTitle() {
        String fileName = "src/main/customers.txt"; // Replace with the actual file path
        String searchName = customerNameField.getText();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));

            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] customerData = line.split(",");
                String name = customerData[1];
                if (name.equalsIgnoreCase(searchName)) {
                    found = true;
                    StringBuilder resultBuilder = new StringBuilder();
                    resultBuilder.append("Customer ID: ").append(customerData[0]).append("\n")
                            .append("Name: ").append(customerData[1]).append("\n")
                            .append("Address: ").append(customerData[2]).append("\n")
                            .append("Phone: ").append(customerData[3]).append("\n")
                            .append("Number of rentals: ").append(customerData[4]).append("\n")
                            .append("Membership: ").append(customerData[5]).append("\n")
                            .append("Username: ").append(customerData[6]).append("\n")
                            .append("Password: ").append(customerData[7]).append("\n");

                    if (customerData.length > 8) {
                        resultBuilder.append("Rented Items: ").append("\n");
                        for (int i = 8; i < customerData.length; i++) {
                            resultBuilder.append(customerData[i]).append("\n");
                        }
                    }

                    resultLabel.setText(resultBuilder.toString());
                    break; // Exit the loop after finding the customer
                }
            }

            if (!found) {
                resultLabel.setText("No customer found with the specified name.");
            }
        } catch (IOException e) {
            displayErrorAlert("An error occurred while searching for the customer: " + e.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                displayErrorAlert("An error occurred while closing the file: " + e.getMessage());
            }
        }
    }


    //increaseStock()

    @FXML
    public void increaseStock() {
        String fileName = "src/main/items.txt"; // Replace with the actual file path
        String searchID = itemIDField.getText();

        try (Scanner sc = new Scanner(System.in)) {
            boolean validID = false;

            while (!validID) {
                if (searchID.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting the program.");
                    return;
                }

                List<String> lines = new ArrayList<>();

                try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                    String line;
                    boolean found = false;

                    while ((line = br.readLine()) != null) {
                        String[] itemData = line.split(",");
                        String ID = itemData[0];

                        if (ID.equals(searchID)) {
                            found = true;
                            System.out.println("Enter the quantity to add: ");
                            int quantityToAdd;
                            boolean validQuantity = false;

                            while (!validQuantity) {
                                String quantityString = quantityField.getText();
                                if (isNumeric(quantityString)) {
                                    quantityToAdd = Integer.parseInt(quantityString);
                                    int currentQuantity = Integer.parseInt(itemData[4].trim());
                                    int newQuantity = currentQuantity + quantityToAdd;
                                    itemData[4] = String.valueOf(newQuantity);
                                    validQuantity = true;
                                } else {
                                    displayErrorAlert("Invalid input. Please enter an integer.");
                                    return;
                                }
                            }
                        }

                        lines.add(String.join(",", itemData));
                    }

                    if (!found) {
                        displayErrorAlert("Item not found in the database. Please try again.");
                    } else {
                        validID = true; // Set validID to true if a valid item ID is found
                    }
                } catch (IOException e) {
                    displayErrorAlert("An error occurred while reading the file: " + e.getMessage());
                }

                try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
                    for (String line : lines) {
                        pw.println(line);
                    }
                } catch (IOException e) {
                    displayErrorAlert("An error occurred while writing to the file: " + e.getMessage());
                }
            }
        }
    }

    private void displayErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

}