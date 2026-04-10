package com.pao.laboratory07.exercise3;

public final class ComandaRedusa extends Comanda {
    private double pret;
    private int discount;
    public ComandaRedusa(String nume, double pret, int discount, String client) {
        super(nume, client);
        this.pret = pret;
        this.discount = discount;
    }
    public int getDiscount() { return discount; }
    public double pretFinal() { return pret - ((pret * discount) / 100.0);}
    public String descriere() {
        return String.format("DISCOUNTED: %s, pret: %.2f lei (-%d%%) - client: %s", nume, pretFinal(), discount, client);
    }
}