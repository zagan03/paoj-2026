package com.pao.laboratory11.exercise3;


public final class Transaction {
    private final int id;
    private final double amount;
    private final String country;
    private final String channel;

    public Transaction(int id, double amount, String country, String channel) {
        this.id = id;
        this.amount = amount;
        this.country = country;
        this.channel = channel;
    }

    public int getId() { return id; }
    public double getAmount() { return amount; }
    public String getCountry() { return country; }
    public String getChannel() { return channel; }

    @Override
    public String toString() {
        return String.format("[ID: %d] %.2f RON | Tara: %s | Canal: %s", id, amount, country, channel);
    }
}