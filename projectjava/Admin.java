package projectjava;

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

    public void addItem(Item items){

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
