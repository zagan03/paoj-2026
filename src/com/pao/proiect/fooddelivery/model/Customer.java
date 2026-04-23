package fooddelivery.models;

public class Customer extends User
{
    private Address address;
    private boolean isPremium;
    public Customer(String name, String email, String phoneNumber,
                    String password, Address address, boolean isPremium) {
        super(name, email, phoneNumber, password);
        this.address = address;
        this.isPremium = isPremium;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public boolean isPremium() { // getter
        return isPremium;
    }
    public void setIsPremium(boolean isPremium) {
        this.isPremium = isPremium;
    }
    @Override
    public String toString() {
        return "Clientul cu " + super.toString() + address + " Status: " + (isPremium ? "Premium" : "Standard");
    }
}
