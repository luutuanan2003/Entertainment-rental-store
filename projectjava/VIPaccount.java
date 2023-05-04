package projectjava;

import java.util.ArrayList;

public class VIPaccount extends Customer{
    private int rewardpoints;

    public VIPaccount(String ID, String name, int phone, ArrayList<Item> items, String username, String password, int rewardpoints) {
        super(ID, name, phone, items, username, password);
        this.rewardpoints = rewardpoints;
    }

    public int getRewardpoints() {
        return rewardpoints;
    }

    @Override
    public void rent(Item items) {

    }
    @Override
    public void returned(){

    }
}
