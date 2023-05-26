import java.util.ArrayList;

public class Guestaccount extends Customer{
    public Guestaccount(String ID, String name, String phone, ArrayList<Item> items, String username, String password) {
        super(ID, name, phone, items, username, password);
    }
    @Override
    public void returned(){

    }
    @Override
    public String rent()
    {
        String customerID = super.rent();
        return customerID;
    }
}
