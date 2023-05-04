package projectjava;

public class DVD extends Item{
    private String genre;

    public DVD(int ID, String title, String rentalType, int quantity, int rentalFee, boolean rentalStatus, String genre) {
        super("I" + ID, title, rentalType, quantity, rentalFee, rentalStatus);
        this.genre = genre;
    }

    public DVD(String genre){
        this.genre = "default";
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "Record{" +
                "genre='" + genre + '\'' +
                '}';
    }
}
