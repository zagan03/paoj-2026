package com.pao.laboratory10.exercise2;

import com.pao.laboratory10.exercise1.Tranzactie;
import com.pao.laboratory10.exercise1.TipTranzactie;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data tip) — pot exista duplicate de id
        //    Stochează-le toate într-un ArrayList<Tranzactie> (cu duplicate, ordine inserare)
        //
        // 2. Procesează comenzile din stdin până la EOF:
        //
        //   UNIQUE_IDS      → LinkedHashSet<Integer> cu id-urile în ordinea primei apariții
        //                     afișează: "IDs unice (N): [1, 2, 3, ...]"
        //
        //   MONTHLY_REPORT  → TreeMap<String, ...> grupat pe yyyy-MM (substring 0-7 din data)
        //                     pentru fiecare lună, sumele CREDIT și DEBIT
        //                     format: "yyyy-MM: CREDIT X.XX RON, DEBIT Y.YY RON"
        //
        //   TOP n           → primele n tranzacții după suma descrescătoare (nu modifică lista)
        //                     afișează "Top n:" urmat de n linii
        //
        //   SORT_ASC        → Collections.sort cu suma crescătoare; afișează lista sortată
        //   SORT_DESC       → Collections.sort cu suma descrescătoare; afișează lista sortată
        //   REVERSE         → Collections.reverse; afișează lista
        //   MIN_MAX         → Collections.min/max după suma
        //                     "MIN: [id] data tip: suma RON"
        //                     "MAX: [id] data tip: suma RON"
        //
        //   CME_DEMO        → încearcă for(t : lista) lista.remove(t) în try-catch
        //                     afișează "ConcurrentModificationException prins: modificare in iteratie detectata."
        //
        // Format linie tranzacție: [id] data tip: suma RON
        //   Ex: [1] 2024-01-15 CREDIT: 1500.00 RON
        Scanner scanner = new Scanner(System.in);
        // Setam Locale.US pentru a procesa corect sumele cu punct
        scanner.useLocale(Locale.US);

        int n = scanner.nextInt();
        List<Tranzactie> lista = new ArrayList<>();

        // Citim cele N tranzactii
        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            double suma = scanner.nextDouble();
            String data = scanner.next();
            TipTranzactie tip = TipTranzactie.valueOf(scanner.next());

            lista.add(new Tranzactie(id, suma, data, tip));
        }

        // Citim comenzile pana la finalul fisierului
        while (scanner.hasNext()) {
            String comanda = scanner.next();

            switch (comanda) {
                case "UNIQUE_IDS": {
                    LinkedHashSet<Integer> ids = new LinkedHashSet<>();
                    for (Tranzactie t : lista) {
                        ids.add(t.getId());
                    }
                    System.out.println("IDs unice (" + ids.size() + "): " + ids);
                    break;
                }

                case "MONTHLY_REPORT": {
                    // Cheia e "yyyy-MM", valoarea e un array: [0] pt suma CREDIT, [1] pt suma DEBIT
                    TreeMap<String, double[]> report = new TreeMap<>();

                    for (Tranzactie t : lista) {
                        String luna = t.getData().substring(0, 7);
                        // Daca luna nu exista in map, o adaugam cu un array gol
                        report.putIfAbsent(luna, new double[2]);

                        if (t.getTip() == TipTranzactie.CREDIT) {
                            report.get(luna)[0] += t.getSuma();
                        } else {
                            report.get(luna)[1] += t.getSuma();
                        }
                    }

                    for (Map.Entry<String, double[]> entry : report.entrySet()) {
                        System.out.printf(Locale.US, "%s: CREDIT %.2f RON, DEBIT %.2f RON\n",
                                entry.getKey(), entry.getValue()[0], entry.getValue()[1]);
                    }
                    break;
                }

                case "TOP": {
                    int nTop = scanner.nextInt();
                    // Facem o copie ca sa nu modificam lista originala
                    List<Tranzactie> copie = new ArrayList<>(lista);
                    // Sortam copia descrescator dupa suma
                    copie.sort((t1, t2) -> Double.compare(t2.getSuma(), t1.getSuma()));

                    System.out.println("Top " + nTop + ":");
                    for (int i = 0; i < Math.min(nTop, copie.size()); i++) {
                        System.out.println(copie.get(i));
                    }
                    break;
                }

                case "SORT_ASC": {
                    lista.sort(Comparator.comparingDouble(Tranzactie::getSuma));
                    for (Tranzactie t : lista) {
                        System.out.println(t);
                    }
                    break;
                }

                case "SORT_DESC": {
                    lista.sort((t1, t2) -> Double.compare(t2.getSuma(), t1.getSuma()));
                    for (Tranzactie t : lista) {
                        System.out.println(t);
                    }
                    break;
                }

                case "REVERSE": {
                    Collections.reverse(lista);
                    for (Tranzactie t : lista) {
                        System.out.println(t);
                    }
                    break;
                }

                case "MIN_MAX": {
                    Comparator<Tranzactie> comp = Comparator.comparingDouble(Tranzactie::getSuma);
                    Tranzactie min = Collections.min(lista, comp);
                    Tranzactie max = Collections.max(lista, comp);

                    System.out.println("MIN: " + min);
                    System.out.println("MAX: " + max);
                    break;
                }

                case "CME_DEMO": {
                    try {
                        for (Tranzactie t : lista) {
                            lista.remove(t);
                        }
                    } catch (ConcurrentModificationException e) {
                        System.out.println("ConcurrentModificationException prins: modificare in iteratie detectata.");
                    }
                    break;
                }
            }
        }

        scanner.close();
    }
}
