package fooddelivery.models;

public class Driver extends User{
    private String vehicleNumber; // numar inmatriculare vehicul
    private boolean isAvailable;

    public Driver(String name, String email, String phoneNumber,
                  String password, String vehicleNumber,
                  boolean isAvailable) {
        super(name, email, phoneNumber, password);
        this.vehicleNumber = vehicleNumber;
        this.isAvailable = isAvailable;
    }
    public String getVehicleNumber() {
        return vehicleNumber;
    }
    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }
    public boolean isAvailable(){ // getter
        return isAvailable;
    }
    public void setIsAvailable(boolean isAvailable){
        this.isAvailable = isAvailable;
    }
    @Override
    public String toString() {
        return "Soferul cu " + super.toString() + " Vehiculul: " + vehicleNumber +
                " Status: " + (isAvailable ? "Valabil" : "Ocupat");
    }
}
