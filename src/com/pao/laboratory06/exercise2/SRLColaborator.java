package com.pao.laboratory06.exercise2;

import java.util.Scanner;
public class SRLColaborator extends Colaborator implements PersoanaJuridica {
    private double cheltuieliLunare;
    @Override
    public void citeste(Scanner in) {
        this.nume = in.next();
        this.prenume = in.next();
        this.venitBrutLunar = in.nextDouble();
        this.cheltuieliLunare = in.nextDouble();
    }
    @Override
    public double calculeazaVenitNetAnual() {
        double profitAnual = (venitBrutLunar - cheltuieliLunare) * 12;
        return profitAnual * 0.84;
    }
    @Override
    public void afiseaza() {
        System.out.printf("SRL: %s %s, venit net anual: %.2f lei\n", nume, prenume, calculeazaVenitNetAnual());
    }
    @Override
    public String tipContract() {
        return TipColaborator.SRL.name();
    }
}
