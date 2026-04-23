package com.pao.proiect.fooddelivery;
import com.pao.proiect.fooddelivery.model.Address;
import com.pao.proiect.fooddelivery.model.MenuItem;
import com.pao.proiect.fooddelivery.model.Restaurant;

public class Main {
    public static void main(String[] args) {
        // 1. Setup Adrese
        Address clientAddr = new Address("Bucuresti", "Libertatii", "10");
        Address restAddr = new Address("Bucuresti", "Victoriei", "100");

        // 2. Creare Restaurant
        Restaurant rest = new Restaurant("Burgaria Noastra", restAddr);

        // 3. Creare Produse (Aici folosim variabila 'rest' pe care am creat-o mai sus)
        MenuItem m1 = new MenuItem("Cheeseburger", 25.0, rest, "Main", 15);
        MenuItem m2 = new MenuItem("Cartofi Prajiti", 10.0, rest, "Sides", 8);
        MenuItem m3 = new MenuItem("Cola", 7.5, rest, "Drinks", 2);

        // 4. Adaugam produsele in meniul restaurantului
        rest.addItem(m1);
        rest.addItem(m2);
        rest.addItem(m3);

        // 5. Verificam rezultatul
        System.out.println(rest); // Aici se apeleaza toString() de la Restaurant

        System.out.println("\n--- Detalii Meniu ---");
        for (MenuItem item : rest.getMenu()) {
            System.out.println(item);
        }
    }
}