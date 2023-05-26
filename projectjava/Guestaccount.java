package projectjava;

import java.io.*;
import java.util.ArrayList;

public class Guestaccount extends Customer{
    public Guestaccount(String ID, String name, String phone, ArrayList<Item> items, String username, String password) {
        super(ID, name, phone, items, username, password);
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
    @Override
    public String rent()
    {
        String customerID = super.rent();
        return customerID;
    }

}


