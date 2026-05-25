package com.pao.proiect.fooddelivery.model;

import java.util.Objects;

public class Address {
    private static int count = 0;
    private int id;
    private String city;
    private String street;
    private String number;
    private String details;

    public String getStreet() {
        return street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getNumber() {
        return number;
    }

    public String getDetails() {
        return details;
    }

    public Address() {
    }
    public Address(String city, String street, String number, String details) {
        this.id = ++count;
        this.city = city;
        this.street = street;
        this.number = number;
        this.details = details;
    }
    public int getId() { return id;}
    public String getCity() {return city;}
    public Address(String city, String street, String number) {
        this(city, street, number, ""); // constructor in cazul in care nu se dau detalii la adresa
        // apeleaza constructorul principal si ii da lui details string gol
    }

    @Override
    public String toString() {
        return "Adresa: Orasul: " + city + " , Strada: " + street +
                " , Numarul: " + number + (details.isEmpty() ? "" : ", " + details);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Address add = (Address) obj;
        return Objects.equals(this.city, add.city) &&
                Objects.equals(this.street, add.street) &&
                Objects.equals(this.number, add.number);
    }
    @Override
    public int hashCode() {
        return Objects.hash(city, street, number);
    }

    public void setId(int addressID) {
        id = addressID;
    }
}



