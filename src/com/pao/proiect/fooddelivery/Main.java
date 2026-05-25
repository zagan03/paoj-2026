//package com.pao.proiect.fooddelivery;
//
//import com.pao.proiect.fooddelivery.model.*;
//import com.pao.proiect.fooddelivery.service.*;
//import com.pao.proiect.fooddelivery.exception.*;
//
//import java.util.Collections;
//import java.util.List;
//
//public class Main {
//    public static void main(String[] args) {
//        System.out.println("Start simulare platforma Food Delivery\n");
//
//        UserService userService = UserService.getInstance();
//        RestaurantService restaurantService = RestaurantService.getInstance();
//        OrderService orderService = OrderService.getInstance();
//
//        Address adresaStandard = new Address("Bucuresti", "Calea Victoriei", "15");
//        Address adresaMaria = new Address("Bucuresti", "Bulevardul Unirii", "22");
//        Address adresaIon = new Address("Bucuresti", "Strada Lunga", "10");
//
//        // 1. Inregistrare Clienti si Soferi
//        System.out.println("1. Inregistrare utilizatori:");
//
//        // Clienti
//        Customer c1 = userService.registerCustomer("Alexandru", "alex@mail.com", "0722111222", "pass1", adresaStandard, true);
//        Customer c2 = userService.registerCustomer("Maria", "maria@mail.com", "0733222333", "pass2", adresaMaria, false);
//        Customer c3 = userService.registerCustomer("Ion", "ion@mail.com", "0744333444", "pass3", adresaIon, false);
//
//        // Soferi
//        Driver d1 = userService.registerDriver("Andrei", "andrei@mail.com", "0744444444", "pass5", "B-11-GLV");
//        Driver d2 = userService.registerDriver("Bogdan", "bogdan@mail.com", "0755555555", "pass6", "CJ-22-TAZ");
//
//        System.out.println();
//
//        // 2. Creare restaurante si produse
//        System.out.println("2. Adaugare restaurante si meniu:");
//
//        // Restaurant 1: Burger Kingdom
//        Restaurant r1 = new Restaurant("Burger Kingdom", adresaStandard);
//        MenuItem burger = new MenuItem("Cheeseburger", 35.0, r1, "Food", 15);
//        MenuItem nuggets = new MenuItem("Chicken Nuggets", 22.0, r1, "Food", 10);
//        MenuItem cartofi = new MenuItem("French Fries", 12.0, r1, "Sides", 5);
//        MenuItem onionRings = new MenuItem("Onion Rings", 15.0, r1, "Sides", 7);
//        MenuItem cola = new MenuItem("Cola Zero", 8.5, r1, "Bauturi", 2);
//        MenuItem milkshake = new MenuItem("Milkshake Chocolate", 18.0, r1, "Bauturi", 5);
//
//        r1.addItem(burger);
//        r1.addItem(nuggets);
//        r1.addItem(cartofi);
//        r1.addItem(onionRings);
//        r1.addItem(cola);
//        r1.addItem(milkshake);
//        restaurantService.addRestaurant(r1);
//
//        // Restaurant 2: Pizza Magic
//        Restaurant r2 = new Restaurant("Pizza Magic", adresaStandard);
//        MenuItem pizza = new MenuItem("Diavola", 40.0, r2, "Pizza", 20);
//        MenuItem quattro = new MenuItem("Quattro Formaggi", 45.0, r2, "Pizza", 20);
//        MenuItem sosUsturoi = new MenuItem("Garlic Sauce", 3.5, r2, "Sides", 1);
//        MenuItem profiterol = new MenuItem("Profiterol", 28.0, r2, "Desert", 8);
//        MenuItem fanta = new MenuItem("Fanta", 8.5, r2, "Bauturi", 2);
//
//        r2.addItem(pizza);
//        r2.addItem(quattro);
//        r2.addItem(sosUsturoi);
//        r2.addItem(profiterol);
//        r2.addItem(fanta);
//        restaurantService.addRestaurant(r2);
//        System.out.println();
//        restaurantService.listAllRestaurants();
//        System.out.println();
//
//        // 3. Testare validari cos (exceptii)
//        System.out.println("3. Testare validari cos:");
//        try {
//            c1.addToCart(burger);
//            c1.addToCart(pizza);
//        } catch (ItemsFromDifferentRestaurantsException e) {
//            System.out.println("Exceptie prinsa corect: " + e.getMessage());
//            c1.clearCart(); // Curatam cosul dupa greseala
//        }
//        System.out.println();
//
//        // 4. Flux de business: Comenzi multiple
//        System.out.println("4. Plasare si livrare comenzi:");
//
//        // Punem soferii pe tura
//        d1.setAvailability(true);
//        d2.setAvailability(true);
//
//        // Comanda 1: Alexandru
//        System.out.println("--- Comanda 1 (Alexandru) ---");
//        c1.addToCart(burger);
//        c1.addToCart(cartofi);
//        c1.addToCart(milkshake);
//        orderService.placeOrder(c1, r1, PaymentMethod.CARD);
//
//        // Comanda 2: Maria
//        System.out.println("\n--- Comanda 2 (Maria) ---");
//        c2.addToCart(quattro);
//        c2.addToCart(profiterol);
//        orderService.placeOrder(c2, r2, PaymentMethod.CASH);
//
//        orderService.deliverOrder(d1);
//
//        // Comanda 3: Ion
//        System.out.println("\n--- Comanda 3 (Ion) ---");
//        c3.addToCart(pizza);
//        c3.addToCart(sosUsturoi);
//        c3.addToCart(fanta);
//        orderService.placeOrder(c3, r2, PaymentMethod.CARD);
//
//        System.out.println("\nLivram restul comenzilor de pe traseu...");
//        orderService.deliverOrder(d2); // Bogdan livreaza Mariei
//        orderService.deliverOrder(d1); // Andrei livreaza lui Ion
//
//        // Comanda 4: Alexandru comanda din nou
//        System.out.println("\n--- Comanda 4 (Alexandru - a doua comanda) ---");
//        c1.addToCart(onionRings);
//        c1.addToCart(cola);
//        orderService.placeOrder(c1, r1, PaymentMethod.ONLINE_WALLET);
//        orderService.deliverOrder(d1); // O preia primul disponibil
//        System.out.println();
//        // 5. Demonstratie Sortare Meniu
//        System.out.println("Meniu Burger Kingdom sortat corect (Categorie -> Pret):");
//        List<MenuItem> meniuR1 = r1.getMenu();
//        Collections.sort(meniuR1);
//        for (MenuItem item : meniuR1) {
//            System.out.println(item);
//        }
//        System.out.println();
//        System.out.println("Meniu Pizza Magic sortat corect (Categorie -> Pret):");
//        List<MenuItem> meniuR2 = r2.getMenu();
//        Collections.sort(meniuR2);
//        for (MenuItem item : meniuR2) {
//            System.out.println(item);
//        }
//        System.out.println();
//
//        // 6. Afisari finale si Istoric
//        System.out.println("6. Afisari sistem si istoric:");
//
//        System.out.println("Clienti in sistem: " + userService.getAllCustomers().size());
//        System.out.println("Soferi in sistem: " + userService.getAllDrivers().size());
//
//        System.out.println("\nIstoric comenzi Alexandru (ar trebui sa aiba 2 comenzi):");
//        orderService.printCustomerHistory(c1.getId());
//
//        System.out.println("\nIstoric comenzi Maria:");
//        orderService.printCustomerHistory(c2.getId());
//
//        System.out.println("\nIstoric comenzi Ion:");
//        orderService.printCustomerHistory(c3.getId());
//
//        System.out.println("\nSimulare finalizata cu succes.");
//    }
//}




// MAI SUS E MAIN-UL DE LA ETAPA 1


package com.pao.proiect.fooddelivery;

import com.pao.proiect.fooddelivery.model.Address;
import com.pao.proiect.fooddelivery.model.Customer;
import com.pao.proiect.fooddelivery.repository.ReportRepository;
import com.pao.proiect.fooddelivery.service.UserService;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Incepere Test Etapa 2: Validare Sistem ===");

        // ---------------------------------------------------------
        // 1. TESTARE AUDIT SI SERVICII (Scrierea in fisierul .csv)
        // ---------------------------------------------------------
        System.out.println("\n--- 1. Testare UserService & AuditService ---");
        UserService userService = UserService.getInstance();

        try {
            // Cream o adresa de test
            Address mockAddress = new Address("Bucuresti", "Str. Victoriei", "10A");

            // Aceasta actiune trebuie sa declanseze log-ul "inregistreaza_client" in audit.csv
            Customer testCustomer = userService.registerCustomer("Andrei Popescu", "andrei@email.com", "0712345678", "parola123", mockAddress, true);

            // Aceasta actiune ar trebui sa declanseze log-ul "afisare_clienti" (daca ai pus apelul in metoda)
            userService.getAllCustomers();

            System.out.println("[INFO] Operatiunile pe useri s-au executat. Verifica fisierul 'audit.csv' din folderul proiectului.");

        } catch (Exception e) {
            System.err.println("[EROARE] La testarea UserService: " + e.getMessage());
        }

        // ---------------------------------------------------------
        // 2. TESTARE BAZA DE DATE SI INTEROGARI COMPLEXE (JOIN-uri)
        // ---------------------------------------------------------
        System.out.println("\n--- 2. Testare Baza de Date & Rapoarte ---");

        try {
            // Initializam repository-ul (Aici se face automat si DatabaseConnection.getInstance().getConnection())
            ReportRepository reportRepository = new ReportRepository();

            System.out.println("\n[Raport A] Istoricul clientului cu ID 1 (Testare JOIN Orders - Users):");
            // Punem ID-ul 1 ca exemplu. Daca nu ai un user cu ID 1 in baza de date, schimba numarul.
            reportRepository.printCustomerOrderHistory(1);

            System.out.println("\n[Raport B] Top 5 produse vandute (Testare GROUP BY si ORDER BY):");
            reportRepository.topProductsSold();

            System.out.println("\n[Raport C] Comenzile restaurantului cu ID 1 (Testare JOIN Orders - Restaurants):");
            // La fel, pune un ID valid de restaurant din tabelul tau
            reportRepository.printRestaurantOrders(1);

        } catch (Exception e) {
            System.err.println("[EROARE] La testarea bazei de date (JDBC): " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n=== Testare Finalizata! ===");
    }
}