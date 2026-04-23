package fooddelivery.models;

import java.util.ArrayList;
import java.util.List;
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
    @Override
    public String toString(){
        return "[ID " + id + "] " + name + " | Locatie " + address +
                " | Numar Produse in meniu: " + menu.size();
    }
}
