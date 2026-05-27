package com.pao.laboratory14.exercise1;

import java.util.*;
import java.util.stream.Collector;

// 2. Clasa Bilet
class Bilet {
    int id;
    String eveniment;
    TipBilet tip;
    double pret;

    public Bilet(int id, String eveniment, TipBilet tip, double pret) {
        this.id = id;
        this.eveniment = eveniment;
        this.tip = tip;
        this.pret = pret;
    }
}

// 3. Clasa RaportVanzari - imutabila, cum a cerut proful
class RaportVanzari {
    final Map<TipBilet, Long> numarPerTip;
    final Map<TipBilet, Double> incasariPerTip;
    final double totalGlobal;
    final double medieGlobala;
    final TipBilet tipCelMaiPopular;

    public RaportVanzari(Map<TipBilet, Long> numarPerTip, Map<TipBilet, Double> incasariPerTip,
                         double totalGlobal, double medieGlobala, TipBilet tipCelMaiPopular) {
        // Folosim unmodifiableMap pentru a bloca orice modificare dupa creare
        this.numarPerTip = Collections.unmodifiableMap(numarPerTip);
        this.incasariPerTip = Collections.unmodifiableMap(incasariPerTip);
        this.totalGlobal = totalGlobal;
        this.medieGlobala = medieGlobala;
        this.tipCelMaiPopular = tipCelMaiPopular;
    }

    public void afiseazaRaportSimplu() {
        // TipBilet.values() garanteaza ca iteram alfabetic (BACKSTAGE -> STANDARD -> VIP)
        for (TipBilet tip : TipBilet.values()) {
            if (numarPerTip.containsKey(tip)) {
                // Locale.US asigura punctul la zecimale, nu virgula
                System.out.printf(Locale.US, "%s: count=%d incasari=%.2f RON\n",
                        tip.name(), numarPerTip.get(tip), incasariPerTip.get(tip));
            }
        }
    }

    public void afiseazaRaportComplet() {
        afiseazaRaportSimplu();
        System.out.println("---");
        System.out.printf(Locale.US, "Total: %.2f RON\n", totalGlobal);
        System.out.printf(Locale.US, "Medie: %.2f RON\n", medieGlobala);
        System.out.println("Cel mai popular: " + tipCelMaiPopular.name());
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;

        int n = scanner.nextInt();
        List<Bilet> bilete = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            String eveniment = scanner.next();
            TipBilet tip = TipBilet.valueOf(scanner.next());
            double pret = scanner.nextDouble();
            bilete.add(new Bilet(id, eveniment, tip, pret));
        }

        String comanda = scanner.next();
        scanner.close();

        // Aplicam magia: procesam toate biletele prin colectorul nostru custom
        RaportVanzari raport = bilete.stream().collect(creeazaColectorRaport());

        if ("RAPORT_SIMPLU".equals(comanda)) {
            raport.afiseazaRaportSimplu();
        } else if ("RAPORT_COMPLET".equals(comanda)) {
            raport.afiseazaRaportComplet();
        }
    }

    // 4. Implementarea propriu-zisa a Colectorului Custom
    private static Collector<Bilet, Map<TipBilet, double[]>, RaportVanzari> creeazaColectorRaport() {
        return Collector.of(
                // A. Supplier: creeaza pe loc un map gol care va pastra {TipBilet -> [count, suma_bani]}
                HashMap::new,

                // B. Accumulator: ia fiecare bilet si il baga in map
                (map, bilet) -> {
                    double[] statistici = map.computeIfAbsent(bilet.tip, k -> new double[2]);
                    statistici[0]++;           // crestem numarul de bilete vandute
                    statistici[1] += bilet.pret; // adunam banii pe bilet
                },

                // C. Combiner: imbina munca daca sunt mai multe thread-uri (streams paralele)
                (map1, map2) -> {
                    map2.forEach((tip, stat2) -> {
                        double[] stat1 = map1.computeIfAbsent(tip, k -> new double[2]);
                        stat1[0] += stat2[0];
                        stat1[1] += stat2[1];
                    });
                    return map1;
                },

                // D. Finisher: transforma fisierul temporar (Map-ul) in obiectul final curat
                mapTimpReal -> {
                    Map<TipBilet, Long> numarBilete = new HashMap<>();
                    Map<TipBilet, Double> incasariBilete = new HashMap<>();
                    double totalSuma = 0;
                    long totalCount = 0;

                    long maxCount = -1;
                    TipBilet tipPopular = null;

                    // Parcurgem alfabetic. Daca doua au acelasi numar,
                    // semnul strict > asigura ca pastram prima varianta!
                    for (TipBilet tip : TipBilet.values()) {
                        if (mapTimpReal.containsKey(tip)) {
                            long count = (long) mapTimpReal.get(tip)[0];
                            double suma = mapTimpReal.get(tip)[1];

                            numarBilete.put(tip, count);
                            incasariBilete.put(tip, suma);

                            totalCount += count;
                            totalSuma += suma;

                            if (count > maxCount) {
                                maxCount = count;
                                tipPopular = tip;
                            }
                        }
                    }

                    double medieSuma = (totalCount > 0) ? (totalSuma / totalCount) : 0;

                    // Returnam obiectul final, imutabil
                    return new RaportVanzari(numarBilete, incasariBilete, totalSuma, medieSuma, tipPopular);
                }
        );
    }
}