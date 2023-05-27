import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Customer {
    private String customerID;
    private String name;
    private String address;
    private String phone;
    private int numOfRentals;
    private String accountType;
    private String username;
    private String password;
    private List<String> rentals;

    public String getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public int getNumOfRentals() {
        return numOfRentals;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getRentals() {
        return rentals;
    }


    public static void getNumberofRental(String customerID) {
        String filePath = "managementCus.txt";
        String line;
        int totalRentals = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while ((line = reader.readLine()) != null) {
                String[] customerData = line.split(",");
                if (customerData.length >= 4 && customerData[0].trim().equals(customerID)) {
                    totalRentals = Integer.parseInt(customerData[3].trim());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Total number of rentals for customer ID " + customerID + ": " + totalRentals);
    }

    public Customer(String customerID, String name, String address, String phone, int numOfRentals,
                    String accountType, String username, String password, List<String> rentals) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.numOfRentals = numOfRentals;
        this.accountType = accountType;
        this.username = username;
        this.password = password;
        this.rentals = rentals;
    }

    public Customer() {
        this.customerID = "default";
        this.name = "default";
        this.address = "default";
        this.phone = "default";
        this.numOfRentals = 0;
        this.accountType = "default";
        this.username = "default";
        this.password = "default";
        this.rentals = null;
    }

    public String getAccountType(String CID) {
        try {
            File file = new File("managementCus.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] customerInfo = line.split(",");
                if (customerInfo[0].equals(CID)) {
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

    public String getLoanType(String itemID) {
        try {
            File file = new File("items.txt");
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
        String accountType = getAccountType(customerID);
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
                }
                else {
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

    public void resetTotalReturn(String CID) {
        try {
            File inputFile = new File("managementCus.txt");
            File tempFile = new File("tempManagementCus.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] customerInfo = line.split(",");
                if (customerInfo[0].equals(CID)) {
                    customerInfo[4] = "0"; // Reset the number of return times to 0
                    line = String.join(",", customerInfo);
                }
                writer.write(line);
                writer.newLine();
            }

            reader.close();
            writer.close();

            inputFile.delete();
            tempFile.renameTo(inputFile);

            System.out.println("Number of return times reset to 0 for customer ID: " + CID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerID='" + customerID + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", numOfRentals=" + numOfRentals +
                ", accountType='" + accountType + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", rentals=" + rentals +
                '}';
    }

    public void addTotalReturn(String CID) {
        try {
            File inputFile = new File("managementCus.txt");
            File tempFile = new File("tempManagementCus.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] customerInfo = line.split(",");
                if (customerInfo[0].equals(CID)) {
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

            System.out.println("Total return for customer " + CID + " has been incremented.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int getTotalRentalItems(String Cid) {
        int rentalItemCount = 0;
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("managementCus.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4 && data[0].equals(Cid)) {
                    rentalItemCount = Integer.parseInt(data[3]);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rentalItemCount;
    }
    public static void main(String[] args)
    {
        Regularaccount C = new Regularaccount("C008", "Hai", "090123456", null, 1, "Regular", "haha", "password", null );
        //int c = C.getnumReturn("C003");
        //C.resetTotalReturn("C001");
        //C.returned();
        //C.promoteCustomer("C005");
        //C.promoteManagement("C005");
        VIPaccount V = new VIPaccount("C009", "haha", "0901231312", null, 1, "Regular", "haha", "password", null );
        //V.addRewardPoint("C001");
        //C.rent();
        //Guestaccount G = new Guestaccount("C010", "Hai", "090123456", null, 1, "Regular", "haha", "password", null);
        //G.Grent();

    }
}
