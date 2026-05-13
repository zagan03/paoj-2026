package com.pao.laboratory11.exercise3;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Snapshot {
    private final Map<String, Long> countByCountry;
    private final Map<String, Long> countByChannel;
    private final double totalAmount;
    private final List<Transaction> topTransactions;

    public Snapshot(Map<String, Long> byCountry, Map<String, Long> byChannel, double totalAmount, List<Transaction> topTransactions) {
        // Le invelim in colectii Unmodifiable (read-only) ca nimeni sa nu le mai poata strica dupa creare
        this.countByCountry = Collections.unmodifiableMap(new HashMap<>(byCountry));
        this.countByChannel = Collections.unmodifiableMap(new HashMap<>(byChannel));
        this.totalAmount = totalAmount;
        this.topTransactions = List.copyOf(topTransactions);
    }

    public Map<String, Long> getCountByCountry() { return countByCountry; }
    public Map<String, Long> getCountByChannel() { return countByChannel; }
    public double getTotalAmount() { return totalAmount; }
    public List<Transaction> getTopTransactions() { return topTransactions; }
}