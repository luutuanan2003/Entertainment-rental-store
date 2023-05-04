package projectjava;

import java.util.ArrayList;

public abstract class Customer {
    private String ID;
    private String name;
    private int phone;
    private ArrayList<Item> items = new ArrayList<Item>();
    private String username;
    private String password;

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getPhone() {
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

    public Customer(String ID, String name, int phone, ArrayList<Item> items, String username, String password) {
        this.ID = ID;
        this.name = name;
        this.phone = phone;
        this.items = items;
        this.username = username;
        this.password = password;
    }

    public Customer(){
        this.ID = "default";
        this.name = "default";
        this.phone = 0;
        this.items = null;
        this.username = "default";
        this.password = "default";
    }

    public void rent(){

    }

    public void returned(){

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

    public abstract void rent(Item items);
}
