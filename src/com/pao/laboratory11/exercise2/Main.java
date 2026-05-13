package com.pao.laboratory11.exercise2;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        if (!scanner.hasNextInt()) return;

        // Citim numarul de tranzactii
        int n = scanner.nextInt();
        List<Tx> txs = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            double amount = scanner.nextDouble();
            String date = scanner.next();
            String country = scanner.next();
            String channel = scanner.next();
            String account = scanner.next();

            txs.add(new Tx(id, amount, date, country, channel, account));
        }

        if (!scanner.hasNextInt()) return;

        // Citim numarul de comenzi
        int q = scanner.nextInt();

        for (int i = 0; i < q; i++) {
            String op = scanner.next();

            switch (op) {
                case "REPORT_MONTH": {
                    String month = scanner.next();

                    // Folosim Stream pentru a filtra dupa luna si a face suma
                    double total = txs.stream()
                            .filter(t -> t.date.startsWith(month))
                            .mapToDouble(t -> t.amount)
                            .sum();

                    // Folosim Stream pentru a numara tranzactiile din acea luna
                    long count = txs.stream()
                            .filter(t -> t.date.startsWith(month))
                            .count();

                    System.out.printf(Locale.US, "MONTH %s total=%.2f count=%d%n", month, total, count);
                    break;
                }

                case "REPORT_ACCOUNT": {
                    String account = scanner.next();

                    // La fel, filtram dupa cont si insumam sumele
                    double total = txs.stream()
                            .filter(t -> t.account.equals(account))
                            .mapToDouble(t -> t.amount)
                            .sum();

                    long count = txs.stream()
                            .filter(t -> t.account.equals(account))
                            .count();

                    System.out.printf(Locale.US, "ACCOUNT %s total=%.2f count=%d%n", account, total, count);
                    break;
                }

                case "TOP_CHANNELS": {
                    int k = scanner.nextInt();

                    if (txs.isEmpty()) {
                        System.out.println("NONE");
                        break;
                    }

                    // grupam dupa canal si numaram(ex: WEB -> 3, ATM -> 2)
                    Map<String, Long> counts = txs.stream()
                            .collect(Collectors.groupingBy(t -> t.channel, Collectors.counting()));

                    // sortam folosind comparator (descrescator dupa valoare, crescator alfabetic)
                    counts.entrySet().stream()
                            .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                                    .thenComparing(Map.Entry.comparingByKey()))
                            .limit(k)
                            .forEach(e -> System.out.println(e.getKey() + " " + e.getValue()));

                    break;
                }

                default:
                    // Ignoram comenzile necunoscute
                    break;
            }
        }

        scanner.close();
    }

    private static class Tx {
        int id;
        double amount;
        String date;
        String country;
        String channel;
        String account;

        Tx(int id, double amount, String date, String country, String channel, String account) {
            this.id = id;
            this.amount = amount;
            this.date = date;
            this.country = country;
            this.channel = channel;
            this.account = account;
        }
    }
}