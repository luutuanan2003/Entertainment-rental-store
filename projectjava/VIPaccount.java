import java.io.*;


public class VIPaccount extends Customer{


    public VIPaccount(String ID, String name, String address ,String phone, int total_rentals, String customerType, String username, String password) {
        super(ID, name, address,phone, total_rentals, customerType, username, password);
    }

    public void addRewardPoint(String CID) {
        try {
            File inputFile = new File("managementCus.txt");
            File tempFile = new File("tempManagementCus.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

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

            reader.close();
            writer.close();

            inputFile.delete();
            tempFile.renameTo(inputFile);

            System.out.println("Reward points for customer " + CID + " have been updated in the management file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String rent() {
        String customerID = super.rent();

        return customerID;
    }

    public void Vreturned(){
        String CID = super.returned();
        addRewardPoint(CID);
    }
}
