package com.pao.laboratory05.angajati;

public class Angajat implements Comparable<Angajat>{
    private String nume;
    private double salariu;
    private Departament departament;
    public Angajat(String nume, double salariu, Departament departament) {
        this.nume = nume;
        this.salariu = salariu;
        this.departament = departament;
    }
    public String getNume(){
        return nume;
    }
    public Departament getDepartament() {
        return departament;
    }
    public double getSalariu() {
        return salariu;
    }

    @Override
    public int compareTo(Angajat other) {
        return Double.compare(other.getSalariu(), this.salariu);
    }
    @Override
    public String toString() {
        return "Angajat {nume=" + nume + ", departament=Departament[nume=" + departament.nume() + ", locatie=" +
        departament.locatie() + "], salariu=" + salariu + "}";
    }
}
