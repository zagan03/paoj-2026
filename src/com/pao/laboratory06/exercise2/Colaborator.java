package com.pao.laboratory06.exercise2;

public abstract class Colaborator implements IOperatiiCitireScriere, Comparable<Colaborator> {
    protected String nume;
    protected String prenume;
    protected double venitBrutLunar;

    public abstract double calculeazaVenitNetAnual();
    public TipColaborator getTip() {
        return TipColaborator.valueOf(tipContract());
    }

    @Override
    public int compareTo(Colaborator other) {
        // sortare descrescatoare dupa venit net anual
        return Double.compare(other.calculeazaVenitNetAnual(), this.calculeazaVenitNetAnual());
    }
}