import java.io.*;
import java.util.*;

public class Admin {
    private String username;
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Admin() {
        this.username = "default";
        this.password = "default";
    }

    @Override
    public String toString() {
        return "Admin{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static void main(String[] args) {
        //addItem();
        //updateItem();
        //deleteItem();
        //displayallItems();
        //addCus();
        //searchItem();
        //searchItemByTitle();
        //searchCus();
        //addCusRentItem();
        //displayCusbylevel();
        //returnCusItem();
        //searchCusByTitle();
        //increaseStock();

    }

    public static void addItem() {
        String fileName = "items.txt"; // Replace with the actual file path
        try (Scanner sc = new Scanner(System.in);
             PrintWriter pw = new PrintWriter(new FileWriter(fileName, true))) {
            do {
                System.out.println("What type of item that you want to enter to the database: ");
                String type = sc.nextLine();
                System.out.println("Enter the ID of the item: ");
                String ID = sc.nextLine();
                System.out.println("Enter the title of the item:");
                String title = sc.nextLine();
                System.out.println("Enter the rental type of the item : ");
                String rentalType = sc.nextLine();
                System.out.println("Enter the loan type of the item : ");
                String loanType = sc.nextLine();
                System.out.println("Enter the quantity of the item : ");
                int quantity = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter the rental fee of the item : ");
                double rentalFee = sc.nextDouble();
                sc.nextLine();
                String genre = "";
                if (type.equalsIgnoreCase("Record") || (type.equalsIgnoreCase("DVD"))) {
                    System.out.println("Enter the genre of the item: ");
                    genre = sc.nextLine();
                }
                // write input data to file
                pw.printf("%s,%s,%s,%s,%d,%.2f,%s\n", ID, title, rentalType, loanType, quantity, rentalFee, genre);
                System.out.println("Do you wish to continue entering new item to the database  Y/N ?");
            }
            while (Character.toUpperCase(sc.nextLine().charAt(0)) == 'Y');
        }
        // Note that FileWriter throws a different exception to the previous
        // FileInputStream/FileInputStream examples
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateItem() {
        String fileName = "items.txt"; // Replace with the actual file path
        try (Scanner sc = new Scanner(System.in);
             BufferedReader br = new BufferedReader(new FileReader(fileName));
             PrintWriter pw = new PrintWriter(new FileWriter(fileName + ".tmp"))) {
            System.out.println("Enter the ID of the item you want to update: ");
            String searchID = sc.nextLine();
            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] itemData = line.split(",");
                String ID = itemData[0];
                if (ID.equalsIgnoreCase(searchID)) {
                    found = true;
                    System.out.println("What part would you like to change?");
                    System.out.println("1. Title");
                    System.out.println("2. Rental Type");
                    System.out.println("3. Loan Type");
                    System.out.println("4. Quantity");
                    System.out.println("5. Rental Fee");
                    System.out.println("6. Genre (for Record/DVD)");

                    int choice = sc.nextInt();
                    sc.nextLine(); // Consume newline character

                    switch (choice) {
                        case 1:
                            System.out.println("Enter the new title: ");
                            itemData[1] = sc.nextLine();
                            break;
                        case 2:
                            String temp2;
                            do {
                                System.out.println("Enter the new rental type (Record/DVD/Game): ");
                                temp2 = sc.nextLine();
                                if (!temp2.equalsIgnoreCase("Record") && !temp2.equalsIgnoreCase("DVD") && !temp2.equalsIgnoreCase("Game")) {
                                    System.out.println("Please enter 'Record', 'DVD', or 'Game'.");
                                }
                            } while (!temp2.equalsIgnoreCase("Record") && !temp2.equalsIgnoreCase("DVD") && !temp2.equalsIgnoreCase("Game"));
                            itemData[2] = temp2;
                            break;

                        case 3:
                            String temp3;
                            do {
                                System.out.println("Enter the new loan type (1-week/2-day): ");
                                temp3 = sc.nextLine();
                                if (!temp3.equalsIgnoreCase("1-week") && !temp3.equalsIgnoreCase("2-day")) {
                                    System.out.println("Please enter '1-week', '2-day'.");
                                }
                            } while (!temp3.equalsIgnoreCase("1-week") && !temp3.equalsIgnoreCase("2-day"));
                            itemData[3] = temp3;
                            break;

                        case 4:
                            boolean validQuantity4 = false;
                            while (!validQuantity4) {
                                System.out.println("Enter the new quantity for the item: ");
                                if (sc.hasNextInt()) {
                                    itemData[4] = Integer.toString(sc.nextInt());
                                    sc.nextLine(); // Consume newline character
                                    validQuantity4 = true;
                                } else {
                                    System.out.println("Invalid input.");
                                }
                            }
                            break;

                        case 5:
                            boolean validQuantity5 = false;
                            while (!validQuantity5) {
                                System.out.println("Enter the new quantity for the item: ");
                                if (sc.hasNextDouble()) {
                                    itemData[5] = Integer.toString(sc.nextInt());
                                    sc.nextLine(); // Consume newline character
                                    validQuantity5 = true;
                                } else {
                                    System.out.println("Invalid input.");
                                }
                            }
                            break;

                        case 6:
                            if (itemData[6].equalsIgnoreCase("Record") || itemData[6].equalsIgnoreCase("DVD")) {
                                System.out.println("Enter the new genre: ");
                                String newGenre = sc.nextLine();
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


    public static void deleteItem() {
        String fileName = "items.txt"; // Replace with the actual file path
        boolean continueDeleting = true;

        try (Scanner sc = new Scanner(System.in)) {
            do {
                try (BufferedReader br = new BufferedReader(new FileReader(fileName));
                     PrintWriter pw = new PrintWriter(new FileWriter(fileName + ".tmp"))) {
                    System.out.println("Enter the ID of the item you want to delete: ");
                    String searchID = sc.nextLine();
                    String line;
                    boolean found = false;

                    while ((line = br.readLine()) != null) {
                        String[] itemData = line.split(",");
                        String ID = itemData[0];
                        if (ID.equalsIgnoreCase(searchID)) {
                            found = true;
                            continue; // Skip writing the line to the temporary file (effectively deleting the item)
                        }
                        pw.println(line);
                    }

                    if (!found) {
                        System.out.println("No item found with the specified ID.");
                    } else {
                        System.out.println("Item deleted successfully.");
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

                    System.out.println("Do you want to continue deleting items? (Y/N)");
                    String userInput = sc.nextLine();
                    if (!userInput.equalsIgnoreCase("Y")) {
                        continueDeleting = false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (continueDeleting);
        }
    }


    public static String displayallItems() {
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

    public static void addCus() {
        String fileName = "customers.txt"; // Replace with the actual file path
        try (Scanner sc = new Scanner(System.in);
             PrintWriter pw = new PrintWriter(new FileWriter(fileName, true))) {
            do {
                System.out.println("Enter the ID of the customer: ");
                String ID = sc.nextLine();
                System.out.println("Enter the name of the customer: ");
                String name = sc.nextLine();
                System.out.println("Enter the address of the customer: ");
                String address = sc.nextLine();
                System.out.println("Enter the phone number of the customer: ");
                String phone = sc.nextLine();
                System.out.println("Enter the number of rentals for the customer: ");
                int numRentals = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter the customer type: ");
                String customerType = sc.nextLine();
                System.out.println("Enter the username: ");
                String username = sc.nextLine();
                System.out.println("Enter the password: ");
                String password = sc.nextLine();

                // Write customer data to file
                pw.printf("%s,%s,%s,%s,%d,%s,%s,%s\n", ID, name, address, phone, numRentals, customerType, username, password);

                System.out.println("Do you wish to continue entering a new customer to the database (Y/N)?");
            } while (Character.toUpperCase(sc.nextLine().charAt(0)) == 'Y');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateCus() {
        String fileName = "customers.txt"; // Replace with the actual file path
        try (Scanner sc = new Scanner(System.in);
             BufferedReader br = new BufferedReader(new FileReader(fileName));
             PrintWriter pw = new PrintWriter(new FileWriter(fileName + ".tmp")))
        {
            System.out.println("Enter the ID of the customer you want to update: ");
            String searchID = sc.nextLine();
            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] customerData = line.split(",");
                String ID = customerData[0];
                if (ID.equalsIgnoreCase(searchID)) {
                    found = true;
                    boolean continueUpdating = true;
                    while (continueUpdating) {
                        System.out.println("What part would you like to change?");
                        System.out.println("1. Name");
                        System.out.println("2. Address");
                        System.out.println("3. Phone Number");
                        System.out.println("4. Number of Rentals");
                        System.out.println("5. Customer Type");
                        System.out.println("6. Username");
                        System.out.println("7. Password");

                        int choice = sc.nextInt();
                        sc.nextLine(); // Consume newline character

                        switch (choice) {
                            case 1:
                                System.out.println("Enter the new name: ");
                                customerData[1] = sc.nextLine();
                                break;
                            case 2:
                                System.out.println("Enter the new address: ");
                                customerData[2] = sc.nextLine();
                                break;
                            case 3:
                                System.out.println("Enter the new phone number: ");
                                customerData[3] = sc.nextLine();
                                break;
                            case 4:
                                boolean validNumRentals = false;
                                while (!validNumRentals) {
                                    System.out.println("Enter the new number of rentals: ");
                                    if (sc.hasNextInt()) {
                                        customerData[4] = Integer.toString(sc.nextInt());
                                        sc.nextLine(); // Consume newline character
                                        validNumRentals = true;
                                    } else {
                                        System.out.println("Invalid input.");
                                    }
                                }
                                break;
                            case 5:
                                System.out.println("Enter the new customer type: ");
                                customerData[5] = sc.nextLine();
                                break;
                            case 6:
                                System.out.println("Enter the new username: ");
                                customerData[6] = sc.nextLine();
                                break;
                            case 7:
                                System.out.println("Enter the new password: ");
                                customerData[7] = sc.nextLine();
                                break;
                            default:
                                System.out.println("Invalid choice!");
                                continue;
                        }

                        System.out.println("Do you want to continue updating this customer? (Y/N)");
                        String continueChoice = sc.nextLine();
                        if (continueChoice.equalsIgnoreCase("Y")) {
                            continueUpdating = true;
                        } else if (continueChoice.equalsIgnoreCase("N")) {
                            continueUpdating = false;
                            System.out.println("Exiting the program.");
                            return; // Exit the method
                        } else {
                            System.out.println("Invalid choice. Exiting the program.");
                            return; // Exit the method
                        }
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

    public static void promoteCus() {
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

            Scanner adminInput = new Scanner(System.in);
            String continuePromotion = "Y";

            while (continuePromotion.equalsIgnoreCase("Y")) {
                // Prompt the admin to enter the customer ID to promote
                System.out.print("Enter the customer ID to promote: ");
                String customerId = adminInput.nextLine().trim();

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
                System.out.print("Enter the new customer type (Guest, Regular, or VIP): ");
                String newType = adminInput.nextLine().trim();

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
                System.out.print("Do you wish to continue promoting customers (Y/N)? ");
                continuePromotion = adminInput.nextLine().trim();
            }

        } catch (IOException e) {
            System.out.println("An error occurred while promoting the customer: " + e.getMessage());
        }
    }
    public static int getCustomerTypeLevel(String type) {
        if (type.equalsIgnoreCase("Guest")) {
            return 0;
        } else if (type.equalsIgnoreCase("Regular")) {
            return 1;
        } else if (type.equalsIgnoreCase("VIP")) {
            return 2;
        }
        return -1; // Invalid type
    }

    public static void displayItemsnostock() {
        String fileName = "items.txt"; // Replace with the actual file path
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] itemData = line.split(",");
                int quantity = Integer.parseInt(itemData[4]);

                if (quantity == 0) {
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
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Customer> displayAllCustomers() {
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

        return customers;
    }

    public static void displayCusbylevel() {
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

                    System.out.println("Customer ID: " + customerData[0]);
                    System.out.println("Name: " + customerData[1]);
                    System.out.println("Address: " + customerData[2]);
                    System.out.println("Phone: " + customerData[3]);
                    System.out.println("Number of Rentals: " + customerData[4]);
                    System.out.println("Username: " + customerData[6]);
                    System.out.println("Password: " + customerData[7]);

                    // Check if there are items associated with the customer
                    if (customerData.length > 8) {
                        System.out.println("Items: ");
                        for (int i = 8; i < customerData.length; i++) {
                            System.out.println("- " + customerData[i]);
                        }
                    }

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
    }
    public static void searchItem() {
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

    public static void searchItemByTitle() {
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

    public static void addCusRentItem() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter customer ID: ");
            String customerId = scanner.nextLine();

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

                    System.out.println("Enter item ID: ");
                    String itemId = scanner.nextLine();

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
                                System.out.println("Item is out of stock. Failed to add rented item.");
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
                    FileWriter itemWriter = new FileWriter("rentorders.txt");
                    itemWriter.write(itemSb.toString());
                    itemWriter.close();

                    // Check if the item exists in the items.txt file
                    if (!itemExists) {
                        System.out.println("Invalid item ID. Failed to add rented item.");
                        customerScanner.close();
                        return;
                    }

                    // Check if the customer has already rented the item
                    if (itemList.contains(itemId)) {
                        System.out.println("Customer has already rented this item. Failed to add rented item.");
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
                System.out.println("Rented item added to the customer record successfully.");
                increasetotalRentals(customerId);

            } else {
                System.out.println("Invalid customer ID. Failed to add rented item.");
            }

        } catch (IOException e) {
            System.out.println("An error occurred while adding the rented item to the customer record: " + e.getMessage());
        }


    }
    public static void increasetotalRentals(String CID)
    {
        try {
            File inputFile = new File("customers.txt");
            File tempFile = new File("tempCustomers.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] customerInfo = line.split(",");
                if (customerInfo[0].equals(CID)) {
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

            System.out.println("Number of rentals for customer " + CID + " has been increased.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void returnCusItem() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter customer ID: ");
            String customerId = scanner.nextLine();
            System.out.println("Enter item ID: ");
            String itemId = scanner.nextLine();

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
                System.out.println("Invalid customer ID. Failed to return item.");
                return;
            }

            List<String> itemList = customerDataMap.get(customerId);

            // Check if the customer has rented the item
            if (!itemList.contains(itemId)) {
                System.out.println("Customer has not rented this item. Failed to return item.");
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

            System.out.println("Item returned successfully.");

        } catch (IOException e) {
            System.out.println("An error occurred while returning the item: " + e.getMessage());
        }
    }
    public static void searchCus() {
        String fileName = "customers.txt"; // Replace with the actual file path
        Scanner sc = new Scanner(System.in);
        BufferedReader br = null;
        boolean continueSearch = true;

        do {
            try {
                br = new BufferedReader(new FileReader(fileName));

                System.out.println("Enter the ID of the customer you want to search: ");
                String searchID = sc.nextLine();
                String line;
                boolean found = false;

                while ((line = br.readLine()) != null) {
                    String[] customerData = line.split(",");
                    String ID = customerData[0];
                    if (ID.equalsIgnoreCase(searchID)) {
                        found = true;
                        System.out.println("Customer ID: " + customerData[0]);
                        System.out.println("Name: " + customerData[1]);
                        System.out.println("Address: " + customerData[2]);
                        System.out.println("Phone: " + customerData[3]);
                        System.out.println("Membership Level: " + customerData[4]);
                        System.out.println("Username: " + customerData[5]);
                        System.out.println("Password: " + customerData[6]);
                        System.out.println("---------------");
                        break; // Exit the loop after finding the customer
                    }
                }

                if (!found) {
                    System.out.println("No customer found with the specified ID.");
                }

                System.out.println("Do you want to search for another customer? (Y/N): ");
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
    public static void searchCusByTitle() {
        String fileName = "customers.txt"; // Replace with the actual file path
        Scanner sc = new Scanner(System.in);
        BufferedReader br = null;
        boolean continueSearch = true;

        do {
            try {
                br = new BufferedReader(new FileReader(fileName));

                System.out.println("Enter the name of the customer you want to search: ");
                String searchName = sc.nextLine();
                String line;
                boolean found = false;

                while ((line = br.readLine()) != null) {
                    String[] customerData = line.split(",");
                    String name = customerData[1];
                    if (name.equalsIgnoreCase(searchName)) {
                        found = true;
                        System.out.println("Customer ID: " + customerData[0]);
                        System.out.println("Name: " + customerData[1]);
                        System.out.println("Address: " + customerData[2]);
                        System.out.println("Phone: " + customerData[3]);
                        System.out.println("Number of rentals: " + customerData[4]);
                        System.out.println("Membership: " + customerData[5]);
                        System.out.println("Username: " + customerData[6]);
                        System.out.println("Password: " + customerData[7]);
                        if (customerData.length > 8) {
                            System.out.println("Rented Items: ");
                            for (int i = 8; i < customerData.length; i++) {
                                System.out.println(customerData[i]);
                            }
                        }
                        System.out.println("---------------");
                        break; // Exit the loop after finding the customer
                    }
                }

                if (!found) {
                    System.out.println("No customer found with the specified name.");
                }

                System.out.println("Do you want to search for another customer? (Y/N): ");
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
    public static void increaseStock() {
        String fileName = "items.txt"; // Replace with the actual file path

        try (Scanner sc = new Scanner(System.in)) {
            boolean validID = false;

            while (!validID) {
                System.out.println("Enter the item ID you want to increase the stock for (or 'exit' to quit): ");
                String searchID = sc.nextLine();

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
                                if (sc.hasNextInt()) {
                                    quantityToAdd = sc.nextInt();
                                    sc.nextLine(); // Consume newline character
                                    int currentQuantity = Integer.parseInt(itemData[4].trim());
                                    int newQuantity = currentQuantity + quantityToAdd;
                                    itemData[4] = String.valueOf(newQuantity);
                                    validQuantity = true;
                                } else {
                                    System.out.println("Invalid input. Please enter an integer.");
                                    sc.nextLine(); // Consume invalid input
                                }
                            }
                        }

                        lines.add(String.join(",", itemData));
                    }

                    if (!found) {
                        System.out.println("Item not found in the database. Please try again.");
                    } else {
                        validID = true; // Set validID to true if a valid item ID is found
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
                    for (String line : lines) {
                        pw.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
