package com.pao.laboratory07.exercise3;

public final class ComandaStandard extends Comanda {
    private double pret;
    public ComandaStandard(String nume, double pret, String client) {
        super(nume, client);
        this.pret = pret;
    }
    public double pretFinal() { return pret; }
    public String descriere() {
        return String.format("STANDARD: %s, pret: %.2f lei - client: %s", nume, pretFinal(), client);
    }
}