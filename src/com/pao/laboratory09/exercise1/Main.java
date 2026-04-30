package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex1.ser";

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data contSursa contDestinatie tip)
        // 2. Setează câmpul note = "procesat" pe fiecare tranzacție înainte de serializare
        // 3. Serializează lista de tranzacții în OUTPUT_FILE cu ObjectOutputStream (try-with-resources)
        // 4. Deserializează lista din OUTPUT_FILE cu ObjectInputStream (try-with-resources)
        // 5. Procesează comenzile din stdin până la EOF:
        //    - LIST          → afișează toate tranzacțiile, câte una pe linie
        //    - FILTER yyyy-MM → afișează tranzacțiile cu data care începe cu yyyy-MM
        //                       sau "Niciun rezultat." dacă nu există
        //    - NOTE id        → afișează "NOTE[id]: <valoarea câmpului note>"
        //                       sau "NOTE[id]: not found" dacă id-ul nu există
        //
        // Format linie tranzacție:
        //   [id] data tip: suma RON | contSursa -> contDestinatie
        //   Ex: [1] 2024-01-15 CREDIT: 1500.00 RON | RO01SRC1 -> RO01DST1

        Scanner sc = new Scanner(System.in);
        File folder = new File("output");
        if (!folder.exists()) {
            folder.mkdir();
        }
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        List<Tranzactie> listaTranzactii = new ArrayList<>();

        // 1. Citire Tranzactii
        for (int i = 0; i < n; i++) {
            int id = sc.nextInt();
            double suma = sc.nextDouble();
            String data = sc.next();
            String sursa = sc.next();
            String destinatie = sc.next();
            TipTranzactie tip = TipTranzactie.valueOf(sc.next());
            Tranzactie t = new Tranzactie(id, suma, data, sursa, destinatie, tip);
            // 2. Setare note la "procesat"
            t.setNote("procesat");
            listaTranzactii.add(t);
        }
        // 3. Serializare
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            oos.writeObject(listaTranzactii);
        } catch (IOException e) {
            System.err.println("Eroare: Nu am putut salva tranzactiile pe disc.");
        }
        // 4. Deserializare
        List<Tranzactie> tranzactiiRecuperate = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(OUTPUT_FILE))) {
            tranzactiiRecuperate = (List<Tranzactie>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Eroare: Nu am putut citi tranzactiile de pe disc.");
        }
        // 5. Procesare comenzi pana la EOF
        while (sc.hasNext()) {
            String comanda = sc.next();

            if (comanda.equals("LIST")) {
                for (Tranzactie t : tranzactiiRecuperate) {
                    System.out.println(t);
                }
            } else if (comanda.equals("FILTER")) {
                String prefix = sc.next();
                boolean gasit = false;
                for (Tranzactie t : tranzactiiRecuperate) {
                    if (t.getData().startsWith(prefix)) {
                        System.out.println(t);
                        gasit = true;
                    }
                }
                if (!gasit) {
                    System.out.println("Niciun rezultat.");
                }
            } else if (comanda.equals("NOTE")) {
                int cautatId = sc.nextInt();
                boolean gasit = false;
                for (Tranzactie t : tranzactiiRecuperate) {
                    if (t.getId() == cautatId) {
                        System.out.println("NOTE[" + cautatId + "]: " + t.getNote());
                        gasit = true;
                        break;
                    }
                }
                if (!gasit) {
                    System.out.println("NOTE[" + cautatId + "]: not found");
                }
            }
        }
    }
}