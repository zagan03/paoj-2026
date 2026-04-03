package com.pao.laboratory06.exercise2;
import java.util.Scanner;
public class CIMColaborator extends Colaborator implements PersoanaFizica {
    private boolean bonus;
    @Override
    public void citeste(Scanner in) {
        this.nume = in.next();
        this.prenume = in.next();
        this.venitBrutLunar = in.nextDouble();
        String bonusInput = in.next();
        this.bonus = bonusInput.equalsIgnoreCase("DA");
    }
    @Override
    public double calculeazaVenitNetAnual() {
        double venitNetAnual = venitBrutLunar * 12 * 0.55;
        if (bonus) {
            venitNetAnual *= 1.10;
        }

        return venitNetAnual;
    }
    public void afiseaza() {
        System.out.printf("CIM: %s %s, venit net anual: %.2f lei\n", nume, prenume, calculeazaVenitNetAnual());
    }

    @Override
    public String tipContract() {
        return TipColaborator.CIM.name();
    }

    @Override
    public boolean areBonus() {
        return bonus;
    }
}


