package com.pao.laboratory10.exercise3;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Hardcodam 10 tranzactii pe 3 luni diferite (Ianuarie, Februarie, Martie 2024)
        List<Tranzactie> lista = Arrays.asList(
                new Tranzactie(1, 1500.00, "2024-01-10", TipTranzactie.CREDIT, "RO12INGB1"),
                new Tranzactie(2, 200.50, "2024-01-15", TipTranzactie.DEBIT, "RO12INGB1"),
                new Tranzactie(3, 50.00, "2024-01-20", TipTranzactie.DEBIT, "RO99BT2"),
                new Tranzactie(4, 3000.00, "2024-02-05", TipTranzactie.CREDIT, "RO12INGB1"),
                new Tranzactie(5, 120.00, "2024-02-14", TipTranzactie.DEBIT, "RO55BCR3"),
                new Tranzactie(6, 450.00, "2024-02-28", TipTranzactie.DEBIT, "RO12INGB1"),
                new Tranzactie(7, 2100.00, "2024-03-01", TipTranzactie.CREDIT, "RO99BT2"),
                new Tranzactie(8, 80.00, "2024-03-10", TipTranzactie.DEBIT, "RO12INGB1"),
                new Tranzactie(9, 600.00, "2024-03-15", TipTranzactie.DEBIT, "RO55BCR3"),
                new Tranzactie(10, 100.00, "2024-03-25", TipTranzactie.CREDIT, "RO99BT2")
        );

        // 1. filter(tip == CREDIT)
        System.out.println("=== 1. Toate tranzactiile CREDIT ===");
        lista.stream()
                .filter(t -> t.getTip() == TipTranzactie.CREDIT)
                .forEach(System.out::println);

        // 2. mapToDouble(suma).sum()
        System.out.println("\n=== 2. Total procesat ===");
        double total = lista.stream()
                .mapToDouble(Tranzactie::getSuma)
                .sum();
        System.out.printf(Locale.US, "Total procesat: %.2f RON\n", total);

        // 3. Collectors.groupingBy(luna, summingDouble(suma))
        System.out.println("\n=== 3. Suma totala per luna ===");
        Map<String, Double> sumaPerLuna = lista.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getData().substring(0, 7),
                        TreeMap::new, // Folosim TreeMap ca sa afiseze lunile in ordine cronologica
                        Collectors.summingDouble(Tranzactie::getSuma)
                ));

        sumaPerLuna.forEach((luna, suma) ->
                System.out.printf(Locale.US, "Per luna: %s: %.2f RON\n", luna, suma)
        );

        // 4. sorted(comparingDouble.reversed()).limit(3)
        System.out.println("\n=== 4. Top 3 tranzactii (dupa suma) ===");
        lista.stream()
                .sorted(Comparator.comparingDouble(Tranzactie::getSuma).reversed())
                .limit(3)
                .forEach(System.out::println);

        // 5. map(contSursa).distinct().collect(toList())
        System.out.println("\n=== 5. Conturi sursa unice ===");
        List<String> conturi = lista.stream()
                .map(Tranzactie::getContSursa)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Conturi sursa unice: " + conturi);

        // 6. mapToDouble(suma).average()
        System.out.println("\n=== 6. Suma medie a tranzactiilor ===");
        double medie = lista.stream()
                .mapToDouble(Tranzactie::getSuma)
                .average()
                .orElse(0.0);
        System.out.printf(Locale.US, "Suma medie: %.2f RON\n", medie);

        // 7. Collectors.groupingBy(luna) cu format extras
        System.out.println("\n=== 7. EXTRAS DE CONT lunar ===");
        Map<String, List<Tranzactie>> tranzactiiPerLuna = lista.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getData().substring(0, 7),
                        TreeMap::new,
                        Collectors.toList()
                ));

        tranzactiiPerLuna.forEach((luna, tranzactiiLuna) -> {
            // Facem un stream mic pentru a calcula totalul pe luna respectiva
            double sumaLuna = tranzactiiLuna.stream().mapToDouble(Tranzactie::getSuma).sum();
            System.out.printf(Locale.US, "EXTRAS DE CONT - %s: %d tranzactii, total: %.2f RON\n",
                    luna, tranzactiiLuna.size(), sumaLuna);
        });
    }
}