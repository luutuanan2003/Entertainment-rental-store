package projectjava;

import java.util.ArrayList;

public class Guestaccount extends Customer{
    public Guestaccount(String ID, String name, int phone, ArrayList<Item> items, String username, String password) {
        super(ID, name, phone, items, username, password);
    }
    @Override
    public void returned(){

    }
    @Override
    public void rent(Item items){

    }
}
