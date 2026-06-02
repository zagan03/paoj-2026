package com.pao.proiect.fooddelivery;

import com.pao.proiect.fooddelivery.model.Address;
import com.pao.proiect.fooddelivery.model.Customer;
import com.pao.proiect.fooddelivery.model.Restaurant;
import com.pao.proiect.fooddelivery.repository.AddressRepository;
import com.pao.proiect.fooddelivery.repository.ReportRepository;
import com.pao.proiect.fooddelivery.service.UserService;
import com.pao.proiect.fooddelivery.service.RestaurantService;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Incepere Test Etapa 2: Validare Sistem ===");

        // Initializam Repository-ul de adrese si Service-urile
        AddressRepository addressRepository = new AddressRepository();
        UserService userService = UserService.getInstance();
        RestaurantService restaurantService = RestaurantService.getInstance();

        // 1. TESTARE USER SERVICE & PERSISTENTA REPOSITORY
        System.out.println("\n--- 1. Testare UserService, Repository si Audit ---");

        try {
            // PASUL 1: Cream si salvam adresa ca sa primim un ID valid de la MySQL
            Address userAddress = new Address("Bucuresti", "Str. Libertatii", "22");
            System.out.println("[TEST] Salvare adresa client in DB via AddressRepository...");
            addressRepository.save(userAddress);

            // PASUL 2: Acum ca adresa are ID, putem salva clientul fara erori
            System.out.println("[TEST] Inregistrare client nou in baza de date via UserRepository...");
            // Generam un email unic la fiecare rulare ca sa nu pice la validarea de "email deja existent"
            String unicEmail = "client_" + System.currentTimeMillis() + "@email.com";

            Customer testCustomer = userService.registerCustomer("Alexandru Test", unicEmail, "0711223344", "parolaSigura", userAddress, true);

            System.out.println("[TEST] Numar total de clienti cititi direct din DB: " + userService.getAllCustomers().size());

        } catch (Exception e) {
            System.err.println("[EROARE] La testarea UserService: " + e.getMessage());
            e.printStackTrace();
        }

        // 2. TESTARE RESTAURANT SERVICE
        System.out.println("\n--- 2. Testare RestaurantService & Persistenta SQL ---");

        try {
            // PASUL 1: Salvam adresa restaurantului
            Address restAddress = new Address("Bucuresti", "Bd. Unirii", "10");
            System.out.println("[TEST] Salvare adresa restaurant in DB via AddressRepository...");
            addressRepository.save(restAddress);

            // PASUL 2: Salvam restaurantul legat de adresa proaspat creata
            Restaurant restaurantNou = new Restaurant("Burger Test", restAddress);
            restaurantNou.setRating(4.9);
            restaurantNou.openRestaurant();

            System.out.println("[TEST] Adaugare restaurant nou in tabela Restaurants...");
            restaurantService.addRestaurant(restaurantNou);

            System.out.println("\n[TEST] Lista restaurante active (citite din DB):");
            restaurantService.listAllRestaurants();

        } catch (Exception e) {
            System.err.println("[EROARE] La testarea RestaurantService: " + e.getMessage());

        }


        // 3. TESTARE INTEROGARI COMPLEXE SI JOIN-uri
        System.out.println("\n--- 3. Testare Interogari Complexe (JOIN-uri & Group By) ---");

        try {
            ReportRepository reportRepository = new ReportRepository();

            System.out.println("\n[Raport A] Istoricul clientului cu ID 1 (Testare JOIN Orders - Users):");
            reportRepository.printCustomerOrderHistory(1);

            System.out.println("\n[Raport B] Top 5 produse vandute (Testare GROUP BY si ORDER BY):");
            reportRepository.topProductsSold();

            System.out.println("\n[Raport C] Comenzile restaurantului cu ID 1 (Testare JOIN Orders - Restaurants):");
            reportRepository.printRestaurantOrders(1);

        } catch (Exception e) {
            System.err.println("[EROARE] La testarea rapoartelor: " + e.getMessage());

        }

        System.out.println("\n=== Testare Finalizata! Verifica si fisierul 'audit.csv' ===");
    }
}