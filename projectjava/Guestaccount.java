import java.io.*;
import java.util.List;
import java.util.Scanner;


public class Guestaccount extends Customer{
    public Guestaccount(String customerID, String name, String address, String phone, int numOfRentals, String accountType, String username, String password, List<String> rentals) {
        super(customerID, name, address, phone, numOfRentals, accountType, username, password, rentals);
    }


    public void promoteCustomer(String CID) {
        // Read the file and store the updated customer information in a StringBuilder
        StringBuilder updatedContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("customers.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] customerInfo = line.split(",");
                if (customerInfo.length >= 5 && customerInfo[0].equals(CID)) {
                    // Change the account type to "Regular" (assuming it is at index 5)
                    customerInfo[4] = "Regular";
                }
                updatedContent.append(String.join(",", customerInfo)).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write the updated content back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("customers.txt"))) {
            bw.write(updatedContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void promoteManagement(String CID) {
        // Read the file and store the updated customer information in a StringBuilder
        StringBuilder updatedContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("managementCus.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] customerInfo = line.split(",");
                if (customerInfo.length >= 2 && customerInfo[0].equals(CID)) {
                    // Change the account type to "Regular" (assuming it is at index 2)
                    customerInfo[2] = "Regular";
                }
                updatedContent.append(String.join(",", customerInfo)).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write the updated content back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("managementCus.txt"))) {
            bw.write(updatedContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Greturned(){

        String cID = super.returned();
    }

    public String Grent()
    {
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
        String accountType = getAccountType(customerID);
        int numberofRentals = getTotalRentalItems(customerID);
        if (accountType.equals("Guest") && numberofRentals == 2)
        {
            System.out.println("You have reached your maximum rental items.");
        }
        else {
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
                    String loanType = getLoanType(itemID);

                    if (accountType.equals("Guest") && loanType.equals("2-day")) {
                        System.out.println("You are not allowed to rent this kind of item");
                        return customerID; // Exit the method
                    } else {
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

            }
        }
        return customerID;
    }

}

