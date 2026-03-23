package com.pao.laboratory05.biblioteca;

import java.util.Arrays;
import java.util.Comparator;

public class BibliotecaService {
    private Carte[] carti;

    // Pasul 1: Constructor privat
    private BibliotecaService() {
        this.carti = new Carte[0];
    }

    // Pasul 2: Holder-ul intern (Lazy Initialization)
    private static class Holder {
        private static final BibliotecaService INSTANCE = new BibliotecaService();
    }

    // Pasul 3: Metoda de acces
    public static BibliotecaService getInstance() {
        return Holder.INSTANCE;
    }

    // --- Aici vin metodele de logica ---
    public void addCarte(Carte carte) {
        Carte[] newArray = new Carte[carti.length + 1];
        System.arraycopy(carti, 0, newArray, 0, carti.length);
        newArray[carti.length] = carte;
        this.carti = newArray;
        System.out.println("Adăugată: " + carte.getTitlu());
    }
    // Sortare naturala (dupa Rating descrescator)
    public void listSortedByRating() {
        Carte[] copy = carti.clone();
        Arrays.sort(copy); // Foloseste compareTo din clasa Carte
        printArray(copy);
    }

    // Sortare flexibila (dupa orice comparator trimitem: An, Autor etc.)
    public void listSortedBy(Comparator<Carte> comparator) {
        Carte[] copy = carti.clone();
        Arrays.sort(copy, comparator);
        printArray(copy);
    }
    
    private void printArray(Carte[] array) {
        for (Carte c : array) {
            System.out.println(c);
        }
    }
}