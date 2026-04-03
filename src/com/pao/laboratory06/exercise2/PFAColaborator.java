package com.pao.laboratory06.exercise2;
import java.util.Scanner;

public class PFAColaborator extends Colaborator implements PersoanaFizica {
    private double cheltuieliLunare;
    private final double SALARIU_MINIM_2026 = 4050.0;

    @Override
    public void citeste(Scanner in) {
        this.nume = in.next();
        this.prenume = in.next();
        this.venitBrutLunar = in.nextDouble();
        this.cheltuieliLunare = in.nextDouble();
    }
    @Override
    public double calculeazaVenitNetAnual() {
        double venitNetBase = (venitBrutLunar - cheltuieliLunare) * 12;
        double SMA = 4050.0 * 12.0; // 48600.0

        // impozit 10%
        double impozit = 0.10 * venitNetBase;

        // CSAS
        double cass;
        if (venitNetBase < 6 * SMA) {
            cass = 0.10 * (6 * SMA);
        } else if (venitNetBase <= 72 * SMA) {
            cass = 0.10 * venitNetBase;
        } else {
            cass = 0.10 * (72 * SMA);
        }

        double cas;
        if (venitNetBase < 12 * SMA) {
            cas = 0;
        } else if (venitNetBase <= 24 * SMA) {
            cas = 0.25 * (12 * SMA);
        } else {
            cas = 0.25 * (24 * SMA);
        }

        return venitNetBase - impozit - cass - cas;
    }
    @Override
    public void afiseaza() {
        System.out.printf("PFA: %s %s, venit net anual: %.2f lei\n", nume, prenume, calculeazaVenitNetAnual());
    }

    @Override
    public String tipContract() { return TipColaborator.PFA.name(); }
}