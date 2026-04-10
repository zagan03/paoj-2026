package com.pao.laboratory07.exercise3;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        if (!sc.hasNextInt()) return;
        int n = Integer.parseInt(sc.nextLine().trim());
        List<Comanda> comenzi = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] t = line.split(" ");

            if (t[0].equals("STANDARD")) {
                comenzi.add(new ComandaStandard(t[1], Double.parseDouble(t[2]), t[3]));
            } else if (t[0].equals("DISCOUNTED")) {
                comenzi.add(new ComandaRedusa(t[1], Double.parseDouble(t[2]), Integer.parseInt(t[3]), t[4]));
            } else if (t[0].equals("GIFT")) {
                comenzi.add(new ComandaGratuita(t[1], t[2]));
            }
        }

        for (Comanda c : comenzi) {
            String desc = c.descriere();
            System.out.println(desc.replace(" - client:", " [PLACED] - client:"));
        }

        while (sc.hasNext()) {
            String cmd = sc.next();
            if (cmd.equals("QUIT")) break;

            switch (cmd) {
                case "STATS" -> {
                    System.out.println("--- STATS ---");
                    double medieStd = comenzi.stream()
                            .filter(c -> c instanceof ComandaStandard)
                            .mapToDouble(Comanda::pretFinal).average().orElse(0.0);
                    double medieRed = comenzi.stream()
                            .filter(c -> c instanceof ComandaRedusa)
                            .mapToDouble(Comanda::pretFinal).average().orElse(0.0);
                    double medieGift = comenzi.stream()
                            .filter(c -> c instanceof ComandaGratuita)
                            .mapToDouble(Comanda::pretFinal).average().orElse(0.0);

                    System.out.printf("STANDARD: medie = %.2f lei\n", medieStd);
                    System.out.printf("DISCOUNTED: medie = %.2f lei\n", medieRed);
                    System.out.printf("GIFT: medie = %.2f lei\n", medieGift);
                }
                case "FILTER" -> {
                    double threshold = sc.nextDouble();
                    System.out.printf("--- FILTER (>= %.2f) ---\n", threshold);
                    comenzi.stream()
                            .filter(c -> c.pretFinal() >= threshold)
                            .forEach(c -> System.out.println(c.descriere()));
                }
                case "SORT" -> {
                    System.out.println("--- SORT (by client, then by pret) ---");
                    comenzi.stream()
                            .sorted(Comparator.comparing(Comanda::getClient)
                                    .thenComparing(Comanda::pretFinal))
                            .forEach(c -> System.out.println(c.descriere()));
                }
                case "SPECIAL" -> {
                    System.out.println("--- SPECIAL (discount > 15%) ---");
                    comenzi.stream()
                            .filter(c -> c instanceof ComandaRedusa r && r.getDiscount() > 15)
                            .forEach(c -> System.out.println(c.descriere()));
                }
            }
        }
    }
}