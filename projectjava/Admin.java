package projectjava;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class Admin {
    private String username;
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Admin(){
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
        displayallItems();

    }

    public static void addItem(){
        String fileName = "items.txt"; // Replace with the actual file path
        try (Scanner sc = new Scanner(System.in);
             PrintWriter pw = new PrintWriter(new FileWriter(fileName, true)))
        {
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
                pw.printf("%s\t%s\t%s\t%s\t%d\t%.2f\t%s\n", ID, title, rentalType, loanType, quantity, rentalFee, genre);
                System.out.println("Do you wish to continue entering new item to the database  Y/N ?");
            }
            while (Character.toUpperCase(sc.nextLine().charAt(0)) == 'Y');
        }
        // Note that FileWriter throws a different exception to the previous
        // FileInputStream/FileInputStream examples
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void updateItem() {
        String fileName = "items.txt"; // Replace with the actual file path
        try (Scanner sc = new Scanner(System.in);
             BufferedReader br = new BufferedReader(new FileReader(fileName));
             PrintWriter pw = new PrintWriter(new FileWriter(fileName + ".tmp")))
        {
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
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void deleteItem() {
        String fileName = "items.txt"; // Replace with the actual file path
        boolean continueDeleting = true;

        try (Scanner sc = new Scanner(System.in)) {
            do {
                try (BufferedReader br = new BufferedReader(new FileReader(fileName));
                     PrintWriter pw = new PrintWriter(new FileWriter(fileName + ".tmp")))
                {
                    System.out.println("Enter the ID of the item you want to delete: ");
                    String searchID = sc.nextLine();
                    String line;
                    boolean found = false;

                    while ((line = br.readLine()) != null) {
                        String[] itemData = line.split("\\s+");
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



    public static void displayallItems() {
        String fileName = "items.txt"; // Replace with the actual file path
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] itemData = line.split("\t");
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
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                pw.printf("%s\t%s\t%s\t%s\t%d\t%s\t%s\t%s\n", ID, name, address, phone, numRentals, customerType, username, password);

                System.out.println("Do you wish to continue entering a new customer to the database (Y/N)?");
            } while (Character.toUpperCase(sc.nextLine().charAt(0)) == 'Y');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateCus(Customer cus){

    }

    public void promoteCus(Customer cus){

    }




public static void displayItemsnostock() {
        String fileName = "items.txt"; // Replace with the actual file path
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] itemData = line.split("\t");
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

public static void displayallCus() {
        String fileName = "customers.txt"; // Replace with the actual file path
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] customerData = line.split(",");
                System.out.println("Customer ID: " + customerData[0]);
                System.out.println("Name: " + customerData[1]);
                System.out.println("Address: " + customerData[2]);
                System.out.println("Phone: " + customerData[3]);
                System.out.println("Number of Rentals: " + customerData[4]);
                System.out.println("Customer Type: " + customerData[5]);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

public static void displayCusbylevel() {
        String fileName = "customers.txt"; // Replace with the actual file path
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            String currentCustomerType = ""; // Track the current customer type

            while ((line = br.readLine()) != null) {
                String[] customerData = line.split(",");
                String customerType = customerData[5];

                // Check if customer type has changed
                if (!customerType.equals(currentCustomerType)) {
                    currentCustomerType = customerType;
                    System.out.println("---------------");
                    System.out.println("Customer Type: " + customerType);
                    System.out.println("---------------");
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchItem(Item items){

    }

    public void searchCus(Customer cus){

    }
}
