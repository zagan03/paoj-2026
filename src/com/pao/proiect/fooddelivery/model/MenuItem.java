package com.pao.proiect.fooddelivery.model;

import java.util.Objects;

public class MenuItem implements Comparable<MenuItem>{
    private static int count = 0;
    private final int id;
    private String name;
    private double price;
    private Restaurant restaurant;
    private String category;
    private int preparationTime;
    public MenuItem(String name, double price, Restaurant restaurant, String category, int preparationTime) {
        this.id = ++count;
        this.name = name;
        this.price = (price < 0 ? 0 : price);
        this.restaurant = restaurant;
        this.category = category;
        this.preparationTime = preparationTime;
    }
    public double getPrice() {
        return price;
    }
    public String getName() {return name;}
    public Restaurant getRestaurant() {return restaurant;}
    @Override
    public String toString() {
        return "[" + category + "] " + name + " - " + price + " RON (Prep: " + preparationTime + " min)";
    }
    @Override
    public int compareTo(MenuItem altProdus) {
        int categoryCompare = this.category.compareTo(altProdus.category);
        if (categoryCompare == 0) {
            return Double.compare(this.price, altProdus.price);
        }
        return categoryCompare;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        MenuItem item = (MenuItem) obj;
        return (item.id == this.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }
}
