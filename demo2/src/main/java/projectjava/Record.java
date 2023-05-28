package projectjava;

public class Record extends Item {
    private String genre;

    public Record(int ID, String title, String rentalType, int quantity, int rentalFee, boolean rentalStatus, String genre) {
        super("I" + ID, title, rentalType, quantity, rentalFee, rentalStatus);
        this.genre = genre;
    }

    public Record(String genre){
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