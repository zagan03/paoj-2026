package com.pao.laboratory02.exercise3.service;

import com.pao.laboratory02.exercise3.model.Angajat;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Completează cele 3 metode.
 * Folosește ArrayList — nu mai e nevoie de redimensionare manuală.
 */
public class AngajatService {
    private final List<Angajat> angajati;

    public AngajatService() {

        this.angajati = new ArrayList<>();

    }

    /** TODO: angajati.add(a); println("Angajat adăugat: " + a.getName()); */
    public void addAngajat(Angajat a) {
        // TODO
        angajati.add(a);
        System.out.println("Angajat adaugat: " + a.getName());
    }

    /** TODO: dacă goală → mesaj; altfel parcurge cu index și afișează (i+1) + ". " + angajat */
    public void listAll() {
        // TODO
        if (angajati.isEmpty()) {
            System.out.println("Nu exista angajati in lista.");
        } else {
            for (int i = 0; i < angajati.size(); i++) {
                // print the individual Angajat at index i (calls its toString())
                System.out.println((i + 1) + ". " + angajati.get(i));
            }
        }
    }

    /** TODO: parcurge lista, sumează a.salariuTotal(), returnează totalul. */
    public double totalSalarii() {
        double total = 0.0;
        for (Angajat a : angajati) {
            total += a.salariuTotal(); // TODO: change to a.salariuTotal() for correct total
        }
        return total;
    }
}
