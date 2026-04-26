package com.pao.proiect.fooddelivery.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Restaurant {
    private static int count = 0;
    private int id;
    private String name;
    private List<MenuItem> menu;
    private Address address;
    private double rating = 0.0;
    private boolean isOpen;

    public Restaurant(String name, Address address) {
        this.id = ++count;
        this.name = name;
        this.address = address;
        this.menu = new ArrayList<>();
        this.isOpen = true;
    }
    public String getName() {
        return name;
    }
    public Address getAddress() {return address;}
    public void addItem(MenuItem item) {
        this.menu.add(item);
    }
    public List<MenuItem> getMenu(){
        return menu;
    }
    public void closeRestaurant() {
        isOpen = false;
    }
    public void openRestaurant() {
        isOpen = true;
    }
    public boolean getOpen() {return isOpen;}
    @Override
    public String toString(){
        return "[ID " + id + "] " + name + " | Locatie " + address +
                " | Numar Produse in meniu: " + menu.size();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Restaurant rest = (Restaurant) obj;
        return (Objects.equals(this.name, rest.name) &&
                Objects.equals(this.address, rest.address));
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

}
