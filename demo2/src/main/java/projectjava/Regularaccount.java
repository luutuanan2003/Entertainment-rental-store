package projectjava;

import java.io.*;
import java.util.List;


public class Regularaccount extends Customer{

    public Regularaccount(String customerID, String name, String address, String phone, int numOfRentals, String accountType, String username, String password, List<String> rentals) {
        super(customerID, name, address, phone, numOfRentals, accountType, username, password, rentals);
    }
    //promote trong customer.txt
    public void promoteCustomer(String CID) {
        try {
            File inputFile = new File("customers.txt");
            File tempFile = new File("tempCustomers.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] customerInfo = line.split(",");
                if (customerInfo[0].equals(CID)) {
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

            System.out.println("Customer " + CID + " has been promoted to VIP.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void promoteManagement(String CID) {
        try {
            File inputFile = new File("managementCus.txt");
            File tempFile = new File("tempManagementCus.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] customerInfo = line.split(",");
                if (customerInfo[0].equals(CID)) {
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

            System.out.println("Customer " + CID + " has been promoted to VIP in the management file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void RAreturned(){
        String CID = super.returned();
        if(getnumReturn(CID) > 5 )
        {
            System.out.println("Congratulation!! You have been promoted to VIP");
            promoteCustomer(CID);
            promoteManagement(CID);
            resetTotalReturn(CID);
        }
        else{
            addTotalReturn(CID);
            System.out.println("Total returns of " + CID + " is "+ getnumReturn(CID));
        }
    }
    @Override
    public String rent()
    {
        String customerID = super.rent();
        return customerID;
    }
}