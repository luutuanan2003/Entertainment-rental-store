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

public class AdminController {


    //Additems
    @FXML
    private ComboBox<String> typeComboBox;

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

    public void addItem() {
        String fileName = "items.txt"; // Replace with the actual file path

        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, true))) {
            do {
                String type = typeComboBox.getValue();
                String ID = idTextField.getText();
                String title = titleTextField.getText();
                String rentalType = rentalTypeTextField.getText();
                String loanType = loanTypeTextField.getText();
                int quantity = Integer.parseInt(quantityTextField.getText());
                double rentalFee = Double.parseDouble(rentalFeeTextField.getText());
                String genre = genreTextField.getText();

                pw.printf("%s,%s,%s,%s,%d,%.2f,%s\n", ID, title, rentalType, loanType, quantity, rentalFee, genre);

                System.out.println("Do you wish to continue entering a new item to the database? (Y/N)");
            } while (Character.toUpperCase(getUserConfirmation()) == 'Y');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private char getUserConfirmation() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine().charAt(0);
    }

    //Update Item
    @FXML
    private TextField idTextField;

    @FXML
    private ComboBox<String> fieldComboBox;

    @FXML
    private TextField newValueTextField;

    public void updateItem() {
        String fileName = "items.txt"; // Replace with the actual file path

        try (BufferedReader br = new BufferedReader(new FileReader(fileName));
             PrintWriter pw = new PrintWriter(new FileWriter(fileName + ".tmp"))) {
            System.out.println("Enter the ID of the item you want to update: ");
            String searchID = idTextField.getText();
            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] itemData = line.split(",");
                String ID = itemData[0];
                if (ID.equalsIgnoreCase(searchID)) {
                    found = true;
                    int choice = Integer.parseInt(fieldComboBox.getValue());

                    switch (choice) {
                        case 1:
                            itemData[1] = newValueTextField.getText();
                            break;
                        case 2:
                            String temp2;
                            do {
                                temp2 = newValueTextField.getText();
                                if (!temp2.equalsIgnoreCase("Record") && !temp2.equalsIgnoreCase("DVD") && !temp2.equalsIgnoreCase("Game")) {
                                    System.out.println("Please enter 'Record', 'DVD', or 'Game'.");
                                }
                            } while (!temp2.equalsIgnoreCase("Record") && !temp2.equalsIgnoreCase("DVD") && !temp2.equalsIgnoreCase("Game"));
                            itemData[2] = temp2;
                            break;

                        case 3:
                            String temp3;
                            do {
                                temp3 = newValueTextField.getText();
                                if (!temp3.equalsIgnoreCase("1-week") && !temp3.equalsIgnoreCase("2-day")) {
                                    System.out.println("Please enter '1-week', '2-day'.");
                                }
                            } while (!temp3.equalsIgnoreCase("1-week") && !temp3.equalsIgnoreCase("2-day"));
                            itemData[3] = temp3;
                            break;

                        case 4:
                            boolean validQuantity4 = false;
                            while (!validQuantity4) {
                                String newQuantity = newValueTextField.getText();
                                if (newQuantity.matches("\\d+")) {
                                    itemData[4] = newQuantity;
                                    validQuantity4 = true;
                                } else {
                                    System.out.println("Invalid input. Please enter a valid quantity.");
                                }
                            }
                            break;

                        case 5:
                            boolean validQuantity5 = false;
                            while (!validQuantity5) {
                                String newQuantity = newValueTextField.getText();
                                if (newQuantity.matches("\\d+(\\.\\d+)?")) {
                                    itemData[5] = newQuantity;
                                    validQuantity5 = true;
                                } else {
                                    System.out.println("Invalid input. Please enter a valid rental fee.");
                                }
                            }
                            break;

                        case 6:
                            if (itemData[6].equalsIgnoreCase("Record") || itemData[6].equalsIgnoreCase("DVD")) {
                                String newGenre = newValueTextField.getText();
                                if (itemData[6].equalsIgnoreCase("Game")) {
                                    System.out.println("Genre cannot be updated for Game items.");
                                } else {
                                    itemData[6] = newGenre;
                                }
                            } else {
                                System.out.println("Genre can only be updated for Record/DVD items.");
                            }
                            break;

                        default:
                            System.out.println("Invalid choice!");
                            break;
                    }
                    line = String.join(",", itemData); // Update the line
                }
                pw.println(line);
            }

            if (!found) {
                System.out.println("No item found with the specified ID.");
            }

            br.close();
            pw.close();

            // Rename the temporary file to the original file
            File originalFile = new File(fileName);
            File tmpFile = new File(fileName + ".tmp");
            if (originalFile.delete()) {
                if (!tmpFile.renameTo(originalFile)) {
                    System.out.println("Failed to rename the temporary file.");
                }
            } else {
                System.out.println("Failed to delete the original file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // DeleteItem
    @FXML
    private TextField itemIDTextField;

    public void deleteItem() {
        String itemID = itemIDTextField.getText();
        String fileName = "items.txt";

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
}

    @FXML
    private TextArea itemsTextArea;

    public void initialize() {
        // Call the method to display all rental items and set the result to the text area
        String itemsInfo = displayAllItems();
        itemsTextArea.setText(itemsInfo);
    }

    private String displayAllItems() {
        String fileName = "items.txt";
        StringBuilder itemsInfo = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] itemData = line.split(",");
                itemsInfo.append("Item ID: ").append(itemData[0]).append("\n");
                itemsInfo.append("Title: ").append(itemData[1]).append("\n");
                itemsInfo.append("Rental Type: ").append(itemData[2]).append("\n");
                itemsInfo.append("Loan Type: ").append(itemData[3]).append("\n");
                itemsInfo.append("Quantity: ").append(itemData[4]).append("\n");
                itemsInfo.append("Rental Fee: ").append(itemData[5]).append("\n");
                if (itemData.length > 6) {
                    itemsInfo.append("Genre: ").append(itemData[6]).append("\n");
                }
                itemsInfo.append("---------------\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return itemsInfo.toString();
    }

    //Addcustomer

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

    public void addCustomer() {
        String fileName = "customers.txt";
        try (Scanner sc = new Scanner(System.in);
             PrintWriter pw = new PrintWriter(new FileWriter(fileName, true))) {
            do {
                String ID = idField.getText();
                String name = nameField.getText();
                String address = addressField.getText();
                String phone = phoneField.getText();
                int numRentals = Integer.parseInt(rentalsField.getText());
                String customerType = customerTypeField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();

                // Write customer data to file
                pw.printf("%s,%s,%s,%s,%d,%s,%s,%s\n", ID, name, address, phone, numRentals, customerType, username, password);

                System.out.println("Do you wish to continue entering a new customer to the database (Y/N)?");
            } while (Character.toUpperCase(sc.nextLine().charAt(0)) == 'Y');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//Update cus
    @FXML
    private TextField idField;

    @FXML
    private TextField fieldToUpdateField;

    @FXML
    private TextField newValueField;

    public void updateCustomer() {
        String fileName = "customers.txt";
        try (Scanner sc = new Scanner(System.in);
             BufferedReader br = new BufferedReader(new FileReader(fileName));
             PrintWriter pw = new PrintWriter(new FileWriter(fileName + ".tmp"))) {
            System.out.println("Enter the ID of the customer you want to update: ");
            String searchID = idField.getText();
            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] customerData = line.split(",");
                String ID = customerData[0];
                if (ID.equalsIgnoreCase(searchID)) {
                    found = true;
                    String fieldToUpdate = fieldToUpdateField.getText();
                    String newValue = newValueField.getText();

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
                        default:
                            System.out.println("Invalid field to update.");
                            return; // Exit the method
                    }

                    pw.println(String.join(",", customerData));
                } else {
                    pw.println(line);
                }
            }

            if (!found) {
                System.out.println("Customer not found.");
            }

            boolean success = new File(fileName).delete();
            if (success) {
                boolean renamed = new File(fileName + ".tmp").renameTo(new File(fileName));
                if (!renamed) {
                    System.out.println("Failed to rename the updated file.");
                }
            } else {
                System.out.println("Failed to delete the original file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //promoteCus
    @FXML
    private TextField idField;

    @FXML
    private TextField newTypeField;

    public void promoteCustomer() {
        try {
            // Read the contents of the file
            File inputFile = new File("customers.txt");
            Scanner scanner = new Scanner(inputFile);
            StringBuilder sb = new StringBuilder();

            // Store customer data in a map for easy access
            Map<String, String[]> customerDataMap = new HashMap<>();

            // Iterate through each line in the file and populate the map
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] customerData = line.split(",");
                customerDataMap.put(customerData[0], customerData);
            }

            scanner.close();

            String continuePromotion = "Y";

            while (continuePromotion.equalsIgnoreCase("Y")) {
                // Prompt the admin to enter the customer ID to promote
                String customerId = idField.getText().trim();

                // Check if the entered customer ID exists in the map
                if (!customerDataMap.containsKey(customerId)) {
                    System.out.println("Invalid customer ID. Promotion aborted.");
                    continue;
                }

                String[] customerData = customerDataMap.get(customerId);

                // Display the customer details
                System.out.println("Customer ID: " + customerData[0]);
                System.out.println("Name: " + customerData[1]);
                System.out.println("Address: " + customerData[2]);
                System.out.println("Phone: " + customerData[3]);
                System.out.println("Type: " + customerData[5]);

                // Prompt the admin to update the customer type
                String newType = newTypeField.getText().trim();

                // Validate the new type
                if (!newType.equalsIgnoreCase("Guest") &&
                        !newType.equalsIgnoreCase("Regular") &&
                        !newType.equalsIgnoreCase("VIP")) {
                    System.out.println("Invalid customer type. Promotion aborted.");
                    continue;
                }

                // Check if the new type is higher than the current type
                if (getCustomerTypeLevel(newType) <= getCustomerTypeLevel(customerData[5])) {
                    System.out.println("The new customer type must be higher than the current type. Promotion aborted.");
                    continue;
                }

                // Update the customer type
                customerData[5] = newType;

                // Reconstruct the line with modified data
                String modifiedLine = String.join(",", customerData);

                // Update the customer data in the map
                customerDataMap.put(customerId, customerData);

                // Reconstruct the file content with modified data
                sb.setLength(0);
                for (String[] data : customerDataMap.values()) {
                    sb.append(String.join(",", data)).append("\n");
                }

                // Write the modified contents back to the file
                FileWriter writer = new FileWriter("customers.txt");
                writer.write(sb.toString());
                writer.close();

                System.out.println("Customer promotion completed.");

                // Ask if the admin wants to continue promoting customers
                continuePromotion = adminInput.nextLine().trim();
            }

        } catch (IOException e) {
            System.out.println("An error occurred while promoting the customer: " + e.getMessage());
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

    //DisplayItemNostock

    @FXML
    private VBox itemsContainer;

    public void displayItemsNoStock() {
        String fileName = "items.txt"; // Replace with the actual file path
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
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

                    // Add the labels to the itemsContainer VBox
                    itemsContainer.getChildren().addAll(
                            itemIdLabel,
                            titleLabel,
                            rentalTypeLabel,
                            loanTypeLabel,
                            quantityLabel,
                            rentalFeeLabel,
                            genreLabel,
                            separatorLabel
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Display all customer

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

    public void displayAllCustomers() {
        String fileName = "customers.txt";
        List<Customer> customers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] customerData = line.split(",");
                String customerID = customerData[0];
                String name = customerData[1];
                String address = customerData[2];
                String phone = customerData[3];
                int numOfRentals = Integer.parseInt(customerData[4]);
                String accountType = customerData[5];
                String username = customerData[6];
                String password = customerData[7];
                List<String> rentals = new ArrayList<>();

                // Check if there are items associated with the customer
                if (customerData.length > 8) {
                    for (int i = 8; i < customerData.length; i++) {
                        rentals.add(customerData[i]);
                    }
                }

                Customer customer = new Customer(customerID, name, address, phone, numOfRentals, accountType,
                        username, password, rentals);
                customers.add(customer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set up the table columns
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        numRentalsColumn.setCellValueFactory(new PropertyValueFactory<>("numOfRentals"));
        accountTypeColumn.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        // Add the customers to the table
        customersTable.getItems().addAll(customers);
    }

    //Display Cus by level
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

    public void displayCusbylevel() {
        String fileName = "customers.txt"; // Replace with the actual file path
        Scanner sc = new Scanner(System.in);

        boolean validType = false;
        String customerTypeToDisplay = "";

        while (!validType) {
            System.out.println("Enter the customer type to display (e.g., VIP, Regular, etc.): ");
            customerTypeToDisplay = sc.nextLine();

            // Add conditions to validate the customer type
            if (customerTypeToDisplay.equalsIgnoreCase("VIP") ||
                    customerTypeToDisplay.equalsIgnoreCase("Regular") ||
                    customerTypeToDisplay.equalsIgnoreCase("Guest")) {
                validType = true;
            } else {
                System.out.println("Invalid customer type. Please try again.");
            }
        }

        List<Customer> customers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean foundCustomers = false;

            while ((line = br.readLine()) != null) {
                String[] customerData = line.split(",");
                String customerType = customerData[5];

                if (customerType.equalsIgnoreCase(customerTypeToDisplay)) {
                    if (!foundCustomers) {
                        System.out.println("---------------");
                        System.out.println("Customers of type: " + customerTypeToDisplay);
                        System.out.println("---------------");
                        foundCustomers = true;
                    }

                    String customerID = customerData[0];
                    String name = customerData[1];
                    String address = customerData[2];
                    String phone = customerData[3];
                    int numOfRentals = Integer.parseInt(customerData[4]);
                    String username = customerData[6];
                    String password = customerData[7];
                    List<String> rentals = new ArrayList<>();

                    // Check if there are items associated with the customer
                    if (customerData.length > 8) {
                        System.out.println("Items: ");
                        for (int i = 8; i < customerData.length; i++) {
                            rentals.add(customerData[i]);
                            System.out.println("- " + customerData[i]);
                        }
                    }

                    Customer customer = new Customer(customerID, name, address, phone, numOfRentals,
                            customerType, username, password, rentals);
                    customers.add(customer);
                    System.out.println("---------------");
                }
            }

            if (!foundCustomers) {
                System.out.println("No customers found with the specified type: " + customerTypeToDisplay);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        sc.close();

        // Set up the table columns
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        numRentalsColumn.setCellValueFactory(new PropertyValueFactory<>("numOfRentals"));
        accountTypeColumn.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        // Add the customers to the table
        customersTable.getItems().addAll(customers);
    }

    //SearchItem
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

    public void searchItem() {
        String fileName = "items.txt"; // Replace with the actual file path
        Scanner sc = new Scanner(System.in);
        BufferedReader br = null;
        boolean continueSearch = true;

        do {
            try {
                br = new BufferedReader(new FileReader(fileName));

                System.out.println("Enter the ID of the item you want to search: ");
                String searchID = sc.nextLine();
                String line;
                boolean found = false;

                while ((line = br.readLine()) != null) {
                    String[] itemData = line.split(",");
                    String ID = itemData[0];
                    if (ID.equalsIgnoreCase(searchID)) {
                        found = true;
                        System.out.println("Item ID: " + itemData[0]);
                        System.out.println("Title: " + itemData[1]);
                        System.out.println("Rental Type: " + itemData[2]);
                        System.out.println("Loan Type: " + itemData[3]);
                        System.out.println("Quantity: " + itemData[4]);
                        System.out.println("Rental Fee: " + itemData[5]);
                        if (itemData.length > 6) {
                            System.out.println("Genre: " + itemData[6]);
                        }
                        System.out.println("---------------");
                        break; // Exit the loop after finding the item
                    }
                }

                if (!found) {
                    System.out.println("No item found with the specified ID.");
                }

                System.out.println("Do you want to search for another item? (Y/N): ");
                String continueChoice = sc.nextLine();
                if (!continueChoice.equalsIgnoreCase("Y")) {
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

        sc.close();
    }

    // Other methods in the controller class

    @FXML
    public void initialize() {
        // Set up the table columns
        itemIDColumn.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        rentalTypeColumn.setCellValueFactory(new PropertyValueFactory<>("rentalType"));
        loanTypeColumn.setCellValueFactory(new PropertyValueFactory<>("loanType"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        rentalFeeColumn.setCellValueFactory(new PropertyValueFactory<>("rentalFee"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

        // Populate the items table
        itemsTable.getItems().addAll(displayAllItems());
    }

    private List<Item> displayAllItems() {
        // Implement the logic to read items from the file and return a list of Item objects
        // Similar to the displayAllCustomers() method implementation
    }

    //SearchItemByTitle
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

    public void searchItemByTitle() {
        String fileName = "items.txt"; // Replace with the actual file path
        Scanner sc = new Scanner(System.in);
        BufferedReader br = null;
        boolean continueSearch = true;

        do {
            try {
                br = new BufferedReader(new FileReader(fileName));

                System.out.println("Enter the title of the item you want to search: ");
                String searchTitle = sc.nextLine();
                String line;
                boolean found = false;

                while ((line = br.readLine()) != null) {
                    String[] itemData = line.split(",");
                    String title = itemData[1];
                    if (title.equalsIgnoreCase(searchTitle)) {
                        found = true;
                        System.out.println("Item ID: " + itemData[0]);
                        System.out.println("Title: " + itemData[1]);
                        System.out.println("Rental Type: " + itemData[2]);
                        System.out.println("Loan Type: " + itemData[3]);
                        System.out.println("Quantity: " + itemData[4]);
                        System.out.println("Rental Fee: " + itemData[5]);
                        if (itemData.length > 6) {
                            System.out.println("Genre: " + itemData[6]);
                        }
                        System.out.println("---------------");
                        break; // Exit the loop after finding the item
                    }
                }

                if (!found) {
                    System.out.println("No item found with the specified title.");
                }

                System.out.println("Do you want to search for another item? (Y/N): ");
                String continueChoice = sc.nextLine();
                if (!continueChoice.equalsIgnoreCase("Y")) {
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

        sc.close();
    }

    //addCusRentItem

    @FXML
    public void initialize() {
        // Set up the table columns
        itemIDColumn.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        rentalTypeColumn.setCellValueFactory(new PropertyValueFactory<>("rentalType"));
        loanTypeColumn.setCellValueFactory(new PropertyValueFactory<>("loanType"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        rentalFeeColumn.setCellValueFactory(new PropertyValueFactory<>("rentalFee"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

        // Populate the items table
        itemsTable.getItems().addAll(displayAllItems());
    }

    private List<Item> displayAllItems() {
        // Implement the logic to read items from the file and return a list of Item objects
    }
    @FXML
    private TextField customerIDField;
    @FXML
    private TextField itemIDField;
    @FXML
    private Button addButton;

    @FXML
    public void addCusRentItem() {
        try {
            String customerId = customerIDField.getText();
            String itemId = itemIDField.getText();

            // Read the contents of the customers.txt file
            File customerFile = new File("customers.txt");
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

                    // Read the contents of the items.txt file
                    File itemsFile = new File("items.txt");
                    Scanner itemScanner = new Scanner(itemsFile);
                    StringBuilder itemSb = new StringBuilder();
                    boolean itemExists = false;

                    // Iterate through each line in the items file and update the quantity
                    while (itemScanner.hasNextLine()) {
                        String itemLine = itemScanner.nextLine();
                        String[] itemData = itemLine.split(",");
                        String currentItemID = itemData[0];

                        if (currentItemID.equals(itemId)) {
                            itemExists = true;
                            int quantityIndex = 4; // Index of quantity in the item data array
                            int quantity = Integer.parseInt(itemData[quantityIndex]);

                            if (quantity <= 0) {
                                displayErrorAlert("Item is out of stock. Failed to add rented item.");
                                itemScanner.close();
                                customerScanner.close();
                                return;
                            }

                            quantity--;
                            itemData[quantityIndex] = String.valueOf(quantity);
                        }

                        itemSb.append(String.join(",", itemData)).append("\n");
                    }

                    itemScanner.close();

                    // Write the modified item contents back to the file
                    FileWriter itemWriter = new FileWriter("items.txt");
                    itemWriter.write(itemSb.toString());
                    itemWriter.close();

                    // Check if the item exists in the items.txt file
                    if (!itemExists) {
                        displayErrorAlert("Invalid item ID. Failed to add rented item.");
                        customerScanner.close();
                        return;
                    }

                    // Check if the customer has already rented the item
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
            FileWriter customerWriter = new FileWriter("customers.txt");
            customerWriter.write(customerSb.toString());
            customerWriter.close();

            if (customerExists) {
                displaySuccessAlert("Rented item added to the customer record successfully.");
                increasetotalRentals(customerId);

            } else {
                displayErrorAlert("Invalid customer ID. Failed to add rented item.");
            }

        } catch (IOException e) {
            displayErrorAlert("An error occurred while adding the rented item to the customer record: " + e.getMessage());
        }
    }

    private void displaySuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void displayErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void increasetotalRentals(String customerId) {
        // Implement the logic to increase total rentals
    }


    //increaseTotalRentals()
    @FXML
    private TextField customerIDField;
    @FXML
    private Button increaseRentalsButton;

    @FXML
    public void increaseTotalRentals() {
        try {
            String customerId = customerIDField.getText();
            File inputFile = new File("customers.txt");
            File tempFile = new File("tempCustomers.txt");

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

    private void displaySuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void displayErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // returnCusItem()
    @FXML
    private TextField customerIDField;
    @FXML
    private TextField itemIDField;
    @FXML
    private Button returnItemButton;

    @FXML
    public void returnCustomerItem() {
        try {
            String customerId = customerIDField.getText();
            String itemId = itemIDField.getText();

            // Read the contents of the customers.txt file
            File customerFile = new File("customers.txt");
            Scanner customerScanner = new Scanner(customerFile);
            StringBuilder customerSb = new StringBuilder();

            // Store customer data in a map for easy access
            Map<String, List<String>> customerDataMap = new HashMap<>();

            // Iterate through each line in the customer file and populate the map
            while (customerScanner.hasNextLine()) {
                String line = customerScanner.nextLine();
                String[] customerData = line.split(",");
                String customerIdKey = customerData[0];
                List<String> itemList = new ArrayList<>();

                // Start from index 1 to skip the customer ID
                for (int i = 1; i < customerData.length; i++) {
                    itemList.add(customerData[i]);
                }

                customerDataMap.put(customerIdKey, itemList);
            }

            customerScanner.close();

            // Check if the customer ID exists in the map
            if (!customerDataMap.containsKey(customerId)) {
                displayErrorAlert("Invalid customer ID. Failed to return item.");
                return;
            }

            List<String> itemList = customerDataMap.get(customerId);

            // Check if the customer has rented the item
            if (!itemList.contains(itemId)) {
                displayErrorAlert("Customer has not rented this item. Failed to return item.");
                return;
            }

            itemList.remove(itemId);

            // Update the customer data in the map
            customerDataMap.put(customerId, itemList);

            // Update the quantity of the returned item in the items.txt file
            File itemsFile = new File("items.txt");
            BufferedReader reader = new BufferedReader(new FileReader(itemsFile));
            StringBuilder fileContents = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] itemData = line.split(",");
                String currentItemId = itemData[0];

                if (currentItemId.equals(itemId)) {
                    int quantityIndex = 4; // Assuming the quantity index is always 4 in the items.txt file
                    int quantity = Integer.parseInt(itemData[quantityIndex]);
                    quantity++;
                    itemData[quantityIndex] = String.valueOf(quantity);
                }

                fileContents.append(String.join(",", itemData)).append("\n");
            }

            reader.close();

            // Write the modified item contents back to the file
            FileWriter writer = new FileWriter("items.txt");
            writer.write(fileContents.toString());
            writer.close();

            // Reconstruct the customers.txt file content with modified data
            customerSb.setLength(0);
            for (Map.Entry<String, List<String>> entry : customerDataMap.entrySet()) {
                String customerIdKey = entry.getKey();
                List<String> items = entry.getValue();
                StringBuilder lineBuilder = new StringBuilder(customerIdKey);

                for (String item : items) {
                    lineBuilder.append(",").append(item);
                }

                customerSb.append(lineBuilder.toString()).append("\n");
            }

            // Write the modified customer contents back to the file
            FileWriter customerWriter = new FileWriter("customers.txt");
            customerWriter.write(customerSb.toString());
            customerWriter.close();

            displaySuccessAlert("Item returned successfully.");

        } catch (IOException e) {
            displayErrorAlert("An error occurred while returning the item: " + e.getMessage());
        }
    }

    private void displaySuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void displayErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    //SearchCus
    @FXML
    private TextField customerIDField;
    @FXML
    private Label resultLabel;

    @FXML
    public void searchCustomer() {
        String fileName = "customers.txt"; // Replace with the actual file path
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

    private void displayErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    //searchCustomerByTitle()
    @FXML
    private TextField customerNameField;
    @FXML
    private Label resultLabel;

    @FXML
    public void searchCustomerByTitle() {
        String fileName = "customers.txt"; // Replace with the actual file path
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

    private void displayErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //increaseStock()
    @FXML
    private TextField itemIDField;
    @FXML
    private TextField quantityField;

    @FXML
    public void increaseStock() {
        String fileName = "items.txt"; // Replace with the actual file path
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
