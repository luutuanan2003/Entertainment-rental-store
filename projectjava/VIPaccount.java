package projectjava;

import java.util.ArrayList;

public class VIPaccount extends Customer{
    private int rewardpoints;

    public VIPaccount(String ID, String name, String address ,String phone, int total_rentals, String customerType, String username, String password, int rewardpoints) {
        super(ID,  name,  address , phone,  total_rentals, customerType,  username, password);
        this.rewardpoints = rewardpoints;
    }

    public int getRewardpoints() {
        return rewardpoints;
    }

    @Override
    public String rent() {
        String customerID = super.rent();

        return customerID;
    }

    public void Vreturned(){
        String cID = super.returned();
    }
}
