package projectjava;

import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
        addItem();
    }

    public static void addItem(){
        System.out.println("Enter the name of the file: ");

        try (Scanner sc = new Scanner(System.in);
             PrintWriter pw = new PrintWriter(new FileWriter(sc.nextLine())))
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
                System.out.println("Enter the rental fee of the item : ");
                double rentalFee = sc.nextDouble();
                String genre = "";
                if (type.equalsIgnoreCase("Record") || (type.equalsIgnoreCase("DVD"))) {
                    System.out.println("Enter the genre of the item: ");
                    genre = sc.nextLine();
                }
                sc.nextLine(); // clear trailing newline from nextInt()
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


    public void updateItem(Item items){

    }

    public void deleteItem(Item items){

    }

    public void addCus(Customer cus){

    }

    public void updateCus(Customer cus){

    }

    public void promoteCus(Customer cus){

    }

    public void displayallItems(){

    }

    public void displayItemsnostock(){

    }

    public void displayallCus(){

    }

    public void displayCusbygroup(){

    }

    public void searchItem(Item items){

    }

    public void searchCus(Customer cus){

    }
}
