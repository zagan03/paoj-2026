package com.pao.laboratory06.exercise3;

public class Inginer extends Angajat implements PlataOnline, Comparable<Inginer> {
    private double sold;

    public Inginer(String nume, String prenume, String telefon, double salariu, double soldInitial) {
        super(nume, prenume, telefon, salariu);
        this.sold = soldInitial;
    }

    @Override
    public void autentificare(String user, String parola) {
        if (user == null || user.isEmpty() || parola == null || parola.isEmpty()) {
            throw new IllegalArgumentException("User sau parola invalide!");
        }
        System.out.println("Inginer " + nume + " s-a autentificat.");
    }

    @Override
    public double consultareSold() { return sold; }

    @Override
    public boolean efectuarePlata(double suma) {
        if (suma <= sold) {
            sold -= suma;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Inginer o) {
        return this.nume.compareTo(o.nume); // sortare alfabetica
    }

    @Override
    public String toString() {
        return String.format("Inginer: %s %s, Salariu: %.2f", nume, prenume, salariu);
    }
}