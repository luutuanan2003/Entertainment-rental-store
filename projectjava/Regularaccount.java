package projectjava;

import java.util.ArrayList;

public class Regularaccount extends Customer{

    public Regularaccount(String ID, String name, String address ,String phone, int total_rentals, String customerType, String username, String password) {
        super( ID,  name,  address , phone,  total_rentals, customerType,  username, password);
    }

    public void Raturned(){
        String cID = super.returned();
    }
    @Override
    public String rent()
    {
        String customerID = super.rent();
        return customerID;
    }
}
