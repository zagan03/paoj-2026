package com.pao.proiect.fooddelivery.service;

import com.pao.proiect.fooddelivery.model.*;
import java.util.*;
public class RestaurantService {
    private static RestaurantService instance;
    private List<Restaurant> restaurants;

    private RestaurantService() {
        restaurants = new ArrayList<>();
    }
    public static RestaurantService getInstance() {
        if (instance == null) {
            instance = new RestaurantService();
        }
        return instance;
    }
    public void addRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
        System.out.println("Restaurantul " + restaurant.getName() + " a fost adaugat!");
    }
    public void listAllRestaurants() {
        for (Restaurant r : restaurants) {
            System.out.println("Restaurant: " + r.getName() + " | Adresa: " + r.getAddress());
        }
    }
    public Restaurant searchRestaurantByName(String name) {
        for (Restaurant r : restaurants) {
            if (r.getName().equalsIgnoreCase(name)) {
                return r;
            }
        }
        throw new RuntimeException("Restaurantul " + name + " nu a fost gasit!");
    }
    public void deleteRestaurantByName(String name) {
        boolean removed = restaurants.removeIf(r -> r.getName().equalsIgnoreCase(name));
        if (removed) {
            System.out.println("Restaurantul " + name + " a fost scos din platforma.");
        }
        else {
            System.out.println("Nu s-a gasit niciun restaurant cu acest nume.");
        }
    }
    public List<Restaurant> searchByCity(String city) {
        List <Restaurant> found = new ArrayList<>();
        for (Restaurant r : restaurants) {
            if (r.getAddress().getCity().equalsIgnoreCase(city)) {
                found.add(r);
            }
        }
        return found;
    }



}
