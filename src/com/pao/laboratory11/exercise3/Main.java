package com.pao.laboratory11.exercise3;

import java.util.*;
import java.util.stream.Collector;

public class Main {
    public static void main(String[] args) {
        // 1. Generam niste date hardcodate
        List<Transaction> txs = Arrays.asList(
                new Transaction(1, 1000.0, "RO", "WEB"),
                new Transaction(2, 500.0, "RU", "ATM"),
                new Transaction(3, 500.0, "RO", "WEB"), // TIE-BREAKER la suma cu ID 2
                new Transaction(4, 2500.0, "NG", "APP"),
                new Transaction(5, 100.0, "RO", "ATM")
        );

        // 2. Trecem datele pe banda rulanta si le colectam in Snapshot-ul nostru custom
        Snapshot snap = txs.stream().collect(toSnapshot(3));

        // 3. Demonstram cele 3 interogari
        System.out.println("=== 1. TOP 3 TRANZACTII ===");
        snap.getTopTransactions().forEach(System.out::println);

        System.out.println("\n=== 2. TOTAL TRANZACTII PE TARI ===");
        snap.getCountByCountry().forEach((tara, count) ->
                System.out.println(tara + ": " + count)
        );

        System.out.println("\n=== 3. TOTAL TRANZACTII PE CANALE ===");
        snap.getCountByChannel().forEach((canal, count) ->
                System.out.println(canal + ": " + count)
        );

        System.out.println("\n=== SUMA TOTALA PROCESATA: " + snap.getTotalAmount() + " ===");
    }

    // Metoda care construieste Colectorul Custom
    public static Collector<Transaction, ?, Snapshot> toSnapshot(int topN) {
        // Clasa locala MUNCITOR - asta e cutia mutabila in care strangem datele in timp ce ruleaza stream-ul
        class Aggregator {
            Map<String, Long> byCountry = new HashMap<>();
            Map<String, Long> byChannel = new HashMap<>();
            double total = 0.0;
            List<Transaction> all = new ArrayList<>();
        }

        return Collector.of(
                Aggregator::new, // 1. Supplier: Creeaza o noua cutie goala

                (agg, tx) -> {   // 2. Accumulator: Cum adaugam o tranzactie in cutie
                    agg.byCountry.put(tx.getCountry(), agg.byCountry.getOrDefault(tx.getCountry(), 0L) + 1);
                    agg.byChannel.put(tx.getChannel(), agg.byChannel.getOrDefault(tx.getChannel(), 0L) + 1);
                    agg.total += tx.getAmount();
                    agg.all.add(tx);
                },

                (agg1, agg2) -> agg1, // 3. Combiner: Folosit doar daca facem stream() in paralel (il lasam basic)

                agg -> {         // 4. Finisher: Cum transformam cutia de munca in Snapshot-ul final
                    // Sortam lista pentru top (descrescator dupa suma, crescator dupa id la egalitate)
                    agg.all.sort(Comparator.comparingDouble(Transaction::getAmount).reversed()
                            .thenComparingInt(Transaction::getId));

                    // Trunchiem lista ca sa pastram doar primele topN
                    List<Transaction> top = agg.all.subList(0, Math.min(topN, agg.all.size()));

                    // Returnam produsul final IMUTABIL
                    return new Snapshot(agg.byCountry, agg.byChannel, agg.total, top);
                }
        );
    }
}