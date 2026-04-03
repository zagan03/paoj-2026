package com.pao.laboratory06.exercise3;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 1. Demonstratie Enum
        System.out.println("TVA curent: " + ConstanteFinanciare.TVA.getValoare());

        // 2. Sortare Ingineri
        List<Inginer> ingineri = new ArrayList<>();
        ingineri.add(new Inginer("Popescu", "Ion", "0722", 8000, 6000));
        ingineri.add(new Inginer("Ionescu", "Ana", "0733", 9500, 5000));
        ingineri.add(new Inginer("Ababei", "Dan", "0744", 7000, 3500));

        Collections.sort(ingineri); // Sortare naturala (nume)
        System.out.println("Dupa nume: " + ingineri);

        ingineri.sort(new ComparatorInginerSalariu());
        System.out.println("Dupa salariu DESC: " + ingineri);

        // 3. Referinta prin interfata
        PlataOnline plata = new Inginer("Vasile", "Gigel", "0711", 5000, 4800);
        plata.autentificare("user", "pass");
        // plata.salariu; // ERROR: Referinta PlataOnline nu vede campul salariu!

        // 4. Persoana Juridica + SMS
        PersoanaJuridica firma = new PersoanaJuridica("SRL Tech", "Admin", "0788");
        firma.trimiteSMS("Confirmare plata 100 RON");
        System.out.println("SMS-uri inregistrate: " + firma.getSmsTrimise());

        // Edge case: Trimitere SMS fara telefon
        PersoanaJuridica firmaFaraTel = new PersoanaJuridica("NoPhone SRL", "Admin", null);
        boolean ok = firmaFaraTel.trimiteSMS("Test");
        System.out.println("SMS trimis fara telefon? " + ok); // returneaza false

        // ---Testare exceptii---
        System.out.println("\n--- Testare erori (Edge Cases) ---");

        // Testam login null
        try {
            ingineri.get(0).autentificare(null, "");
        } catch (IllegalArgumentException e) {
            System.out.println("Am prins eroarea de login: " + e.getMessage());
        }

        // Testam SMS pe Inginer (folosind referinta 'plata' declarata mai sus)
        try {
            plata.trimiteSMS("Salut!");
        } catch (UnsupportedOperationException e) {
            System.out.println("Am prins eroarea de capabilitate: " + e.getMessage());
        }
        if (!firmaFaraTel.trimiteSMS("Test 2")) {
            System.out.println("Corect: Firma fara numar returneaza false.");
        }
    }
}