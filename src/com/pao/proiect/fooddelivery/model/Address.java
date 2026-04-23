package fooddelivery.models;

public class Address {
    private String city;
    private String street;
    private String number;
    private String details;

    public Address(String city, String street, String number, String details) {
        this.city = city;
        this.street = street;
        this.number = number;
        this.details = details;
    }
    public Address(String city, String street, String number) {
        this(city, street, number, ""); // constructor in cazul in care nu se dau detalii la adresa
        // apeleaza constructorul principal si ii da lui details string gol
    }

    @Override
    public String toString() {
        return "Adresa: Orasul: " + city + " , Strada: " + street +
                " , Numarul: " + number + (details.isEmpty() ? "" : ", " + details);
    }

}



