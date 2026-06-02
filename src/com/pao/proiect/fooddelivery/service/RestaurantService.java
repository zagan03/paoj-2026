//package com.pao.proiect.fooddelivery.service;
//
//import com.pao.proiect.fooddelivery.model.*;
//import java.util.*;
//public class RestaurantService {
//    private final AuditService auditService = AuditService.getInstance();
//    private static RestaurantService instance;
//    private List<Restaurant> restaurants;
//
//    private RestaurantService() {
//        restaurants = new ArrayList<>();
//    }
//    public static RestaurantService getInstance() {
//        if (instance == null) {
//            instance = new RestaurantService();
//        }
//        return instance;
//    }
//    public void addRestaurant(Restaurant restaurant) {
//        auditService.log("adauga_restaurant");
//        restaurants.add(restaurant);
//        System.out.println("Restaurantul " + restaurant.getName() + " a fost adaugat!");
//    }
//    public void listAllRestaurants() {
//        auditService.log("afiseaza_restaurante");
//        for (Restaurant r : restaurants) {
//            System.out.println("Restaurant: " + r.getName() + " | Adresa: " + r.getAddress());
//        }
//    }
//    public Restaurant searchRestaurantByName(String name) {
//        auditService.log("cauta_restaurant_dupa_nume");
//        for (Restaurant r : restaurants) {
//            if (r.getName().equalsIgnoreCase(name)) {
//                return r;
//            }
//        }
//        throw new RuntimeException("Restaurantul " + name + " nu a fost gasit!");
//    }
//    public void deleteRestaurantByName(String name) {
//        auditService.log("sterge_restaurant_dupa_nume");
//        boolean removed = restaurants.removeIf(r -> r.getName().equalsIgnoreCase(name));
//        if (removed) {
//            System.out.println("Restaurantul " + name + " a fost scos din platforma.");
//        }
//        else {
//            System.out.println("Nu s-a gasit niciun restaurant cu acest nume.");
//        }
//    }
//    public List<Restaurant> searchByCity(String city) {
//        auditService.log("cauta_restaurant_dupa_oras");
//        List <Restaurant> found = new ArrayList<>();
//        for (Restaurant r : restaurants) {
//            if (r.getAddress().getCity().equalsIgnoreCase(city)) {
//                found.add(r);
//            }
//        }
//        return found;
//    }
//
//
//
//}

package com.pao.proiect.fooddelivery.service;

import com.pao.proiect.fooddelivery.model.*;
import com.pao.proiect.fooddelivery.repository.RestaurantRepository; // IMPORTĂM REPOSITORY-UL
import java.util.*;

public class RestaurantService {
    private final AuditService auditService = AuditService.getInstance();

    // AICI E SCHIMBAREA: Înlocuim lista din memorie cu Repository-ul conectat la DB
    private final RestaurantRepository restaurantRepository = new RestaurantRepository();

    private static RestaurantService instance;

    private RestaurantService() {
        // Gata cu lista locala, citim direct din baza de date!
    }

    public static RestaurantService getInstance() {
        if (instance == null) {
            instance = new RestaurantService();
        }
        return instance;
    }

    public void addRestaurant(Restaurant restaurant) {
        auditService.log("adauga_restaurant");

        // SALVARE ÎN DB VIA REPOSITORY
        restaurantRepository.save(restaurant);

        System.out.println("Restaurantul " + restaurant.getName() + " a fost adaugat!");
    }

    public void listAllRestaurants() {
        auditService.log("afiseaza_restaurante");

        // Luăm toate restaurantele din DB prin repository
        List<Restaurant> restaurants = restaurantRepository.findAll();
        for (Restaurant r : restaurants) {
            System.out.println("Restaurant: " + r.getName() + " | Adresa: " + r.getAddress());
        }
    }

    public Restaurant searchRestaurantByName(String name) {
        auditService.log("cauta_restaurant_dupa_nume");

        // Căutăm direct în lista adusă din baza de date
        List<Restaurant> restaurants = restaurantRepository.findAll();
        for (Restaurant r : restaurants) {
            if (r.getName().equalsIgnoreCase(name)) {
                return r;
            }
        }
        throw new RuntimeException("Restaurantul " + name + " nu a fost gasit!");
    }

    public void deleteRestaurantByName(String name) {
        auditService.log("sterge_restaurant_dupa_nume");

        List<Restaurant> restaurants = restaurantRepository.findAll();
        Restaurant toDelete = null;

        for (Restaurant r : restaurants) {
            if (r.getName().equalsIgnoreCase(name)) {
                toDelete = r;
                break;
            }
        }

        if (toDelete != null) {
            // Apelăm metoda standard 'delete' din repository folosind ID-ul restaurantului găsit
            restaurantRepository.delete(toDelete.getId());
            System.out.println("Restaurantul " + name + " a fost scos din platforma.");
        } else {
            System.out.println("Nu s-a gasit niciun restaurant cu acest nume.");
        }
    }

    public List<Restaurant> searchByCity(String city) {
        auditService.log("cauta_restaurant_dupa_oras");
        List<Restaurant> found = new ArrayList<>();

        // Filtram pe baza orasului din adresa, citind direct din DB
        List<Restaurant> restaurants = restaurantRepository.findAll();
        for (Restaurant r : restaurants) {
            if (r.getAddress() != null && r.getAddress().getCity() != null &&
                    r.getAddress().getCity().equalsIgnoreCase(city)) {
                found.add(r);
            }
        }
        return found;
    }
}
