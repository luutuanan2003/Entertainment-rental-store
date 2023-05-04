package projectjava;

public abstract class Item {
    private String ID;
    private String title;
    private String rentalType;
    private int quantity;
    private double rentalFee;
    private boolean rentalStatus;

    public String getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getRentalType() {
        return rentalType;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getRentalFee() {
        return rentalFee;
    }

    public boolean isRentalStatus() {
        return rentalStatus;
    }

    public Item(String ID, String title, String rentalType, int quantity, int rentalFee, boolean rentalStatus) {
        this.ID = ID;
        this.title = title;
        this.rentalType = rentalType;
        this.quantity = quantity;
        this.rentalFee = rentalFee;
        this.rentalStatus = rentalStatus;
    }

    public Item(){
        this.ID = "default";
        this.title = "default";
        this.rentalType = "default";
        this.quantity = 0;
        this.rentalFee = 0.00;
        this.rentalStatus = true;
    }

    @Override
    public String toString() {
        return "Item{" +
                "ID=" + ID +
                ", title='" + title + '\'' +
                ", rentalType='" + rentalType + '\'' +
                ", copiesnum=" + quantity +
                ", rentalFee=" + rentalFee +
                ", rentalStatus=" + rentalStatus +
                '}';
    }
}
