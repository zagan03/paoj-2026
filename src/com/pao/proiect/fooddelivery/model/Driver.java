package com.pao.proiect.fooddelivery.model;

public class Driver extends User{
    private String vehicleNumber; // numar inmatriculare vehicul
    private boolean isAvailable;
    private Order currentOrder;

    public Driver(String name, String email, String phoneNumber,
                  String password, String vehicleNumber
                  ) {
        super(name, email, phoneNumber, password);
        this.vehicleNumber = vehicleNumber;
        this.isAvailable = false;
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
    public void setAvailability(boolean isAvailable){
        this.isAvailable = isAvailable;
    }
    public void setCurrentOrder(Order order) {
        this.currentOrder = order;
    }
    public Order getCurrentOrder() {
        return currentOrder;
    }
    public void completeOrder() {
        this.currentOrder = null;
        this.isAvailable = true;
    }

    @Override
    public String getRole() {
        return "DRIVER";
    }

    @Override
    public String toString() {
        return "Soferul cu " + super.toString() + " Vehiculul: " + vehicleNumber +
                " Status: " + (isAvailable ? "Valabil" : "Ocupat");
    }
}
