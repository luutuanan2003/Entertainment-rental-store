import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public abstract class Customer {
    private String ID;
    private String name;
    private String phone;
    private ArrayList<Item> items = new ArrayList<Item>();
    private String username;
    private String password;

    private String[][] orders;

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Customer(String ID, String name, String phone, ArrayList<Item> items, String username, String password) {
        this.ID = ID;
        this.name = name;
        this.phone = phone;
        this.items = items;
        this.username = username;
        this.password = password;
    }

    public Customer() {
        this.ID = "default";
        this.name = "default";
        this.phone = "default";
        this.items = null;
        this.username = "default";
        this.password = "default";
    }

    public String rent() {
        Scanner scanner = new Scanner(System.in);
        String customerID = "";

        boolean validCustomerID = false;
        while (!validCustomerID) {
            System.out.println("Please enter your customer ID: ");
            customerID = scanner.nextLine().trim();

            if (!customerID.matches("C\\d{3}")) {
                System.out.println("Invalid customer ID format. Please provide a valid customer ID.");
            } else {
                boolean customerFound = false;

                try (BufferedReader br = new BufferedReader(new FileReader("customers.txt"))) {
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
                    validCustomerID = true;
                } else {
                    System.out.println("Customer not found with ID: " + customerID);
                }
            }
        }

        System.out.println("Customer ID verified");
        System.out.println("------------");

        // Prompt user for item ID
        String itemID = "";
        boolean validItemID = false;
        boolean itemFound = false; // Flag to indicate if the item with the provided ID is found
        while (!validItemID || !itemFound) {
            System.out.println("Please enter the item ID: ");
            if (scanner.hasNextLine()) {
                itemID = scanner.nextLine().trim(); // Trim any leading/trailing whitespace

                // Check if the item ID is valid
                if (!itemID.matches("I\\d{3}-\\d{4}(,.+)?")) {
                    System.out.println("Invalid item ID format. Please provide a valid item ID.");
                    scanner.skip(".+"); // Clear the buffer
                    validItemID = false;
                } else {
                    validItemID = true;
                }

                // Retrieve information about the item from the items.txt file
                try (BufferedReader br = new BufferedReader(new FileReader("items.txt"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] itemInfo = line.split(",");
                        if (itemInfo[0].equals(itemID)) {
                            System.out.println("------------");
                            System.out.println("Item ID: " + itemInfo[0]);
                            System.out.println("Item Name: " + itemInfo[1]);
                            System.out.println("Item Price: " + itemInfo[5]);

                            // Store the customer ID, item ID, and quantity in the orders array
                            String[][] orders = new String[1][2];
                            orders[0][0] = customerID;
                            orders[0][1] = itemID;
                            itemFound = true;
                            break;
                        }
                    }

                    if (!itemFound) {
                        System.out.println("Item not found with ID: " + itemID);
                        validItemID = false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Print the order details
        System.out.println("------------");
        System.out.println("Order Details: ");
        System.out.println("Customer ID: " + customerID);
        System.out.println("Item ID: " + itemID);

        // Save the order details to the orders.txt file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("rentorders.txt", true))) {
            bw.write(customerID + "," + itemID);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        scanner.close(); // Close the scanner
        return customerID;
    }

    public String returned() {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for customer ID
        String customerID = "";
        boolean validCustomerID = false;
        while (!validCustomerID) {
            System.out.println("Please enter your customer ID: ");
            customerID = scanner.nextLine().trim(); // Trim any leading/trailing whitespace

            // Check if the customer ID is valid
            if (!customerID.matches("C\\d{3}")) {
                System.out.println("Invalid customer ID format. Please provide a valid customer ID.");
            } else {
                // Check if the customer ID exists in the file
                boolean customerExists = false;
                try (BufferedReader br = new BufferedReader(new FileReader("customers.txt"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] info = line.split(",");
                        if (info[0].equals(customerID)) {
                            customerExists = true;
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (!customerExists) {
                    System.out.println("Customer not found with ID: " + customerID);
                } else {
                    validCustomerID = true;
                }
            }
        }

        boolean customerFound = false;
        List<String> rentedItems = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("customers.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] info = line.split(",");
                if (info[0].equals(customerID)) {
                    customerFound = true;
                    rentedItems = new ArrayList<>();
                    for (int i = 8; i < info.length; i++) {
                        rentedItems.add(info[i]);
                    }
                    System.out.println("Rented Items for Customer ID: " + customerID);
                    System.out.println("------------");
                    for (String itemID : rentedItems) {
                        System.out.println("Item ID: " + itemID);
                    }
                    System.out.println("------------");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!customerFound) {
            // Return if the customer is not found
            return null;
        }

        // Prompt user for item ID
        String itemID = "";
        boolean validItemID = false;
        while (!validItemID) {
            System.out.println("Please enter the item ID to return: ");
            if (scanner.hasNextLine()) {
                itemID = scanner.nextLine().trim(); // Trim any leading/trailing whitespace

                // Check if the item ID belongs to the customer
                if (!rentedItems.contains(itemID)) {
                    System.out.println("Invalid item ID. Please enter a valid item ID.");
                } else {
                    validItemID = true;
                }
            }
        }

        System.out.println("Return order confirmed:");
        System.out.println("Customer ID: " + customerID);
        System.out.println("Item ID: " + itemID);
        System.out.println("------------");

        // Save the return order details to the returnorders.txt file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("returnorders.txt", true))) {
            bw.write(customerID + "," + itemID);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        scanner.close(); // Close the scanner
        return customerID;
    }

    public int getnumReturn(String CID) {
        int returnTotal = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("managementCus.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] customerInfo = line.split(",");
                if (customerInfo[0].equals(CID)) {
                    returnTotal = Integer.parseInt(customerInfo[4]);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnTotal;
    }

        @Override
    public String toString() {
        return "Customer{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                ", items=" + items +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


    public static void main(String[] args)
    {
        Regularaccount C = new Regularaccount("C008", "Hai", "090123456", null, "haha", "password" );
        int c = C.getnumReturn("C003");
        //C.returned();
    }


}
