package com.pao.laboratory14.exercise3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Bonus — Alocare Automata de Sali pentru Evenimente
 * <p>
 * Problema clasica de interviu: date N evenimente cu intervale [start, end],
 * gaseste numarul minim de sali necesare si atribuie fiecare eveniment la o sala.
 * <p>
 * Doua variante demonstrate:
 * Varianta 1 — greedy simplu O(N^2): prima sala disponibila
 * Varianta 2 — PriorityQueue O(N log N): min-heap de ore de final
 */
public class Main {

    // Folosim functionalitatea de record din Java pentru un model de date curat
    record Eveniment(String nume, int startMin, int endMin) {
    }

    /**
     * Converteste "HH:MM" in minute intregi de la miezul noptii.
     */
    private static int toMin(String hhmm) {
        String[] p = hhmm.split(":");
        return Integer.parseInt(p[0]) * 60 + Integer.parseInt(p[1]);
    }

    /**
     * Converteste minute intregi inapoi in "HH:MM".
     */
    private static String toHHMM(int min) {
        return String.format("%02d:%02d", min / 60, min % 60);
    }

    public static void main(String[] args) {
        // 1. Definim lista de 8 evenimente cu suprapuneri (ca sa fortam algoritmul)
        List<Eveniment> evenimente = new ArrayList<>(List.of(
                new Eveniment("Conferinta AI", toMin("09:00"), toMin("11:00")),
                new Eveniment("Workshop Java", toMin("09:30"), toMin("10:30")),
                new Eveniment("Sedinta HR", toMin("10:00"), toMin("12:00")),
                new Eveniment("Prezentare", toMin("10:45"), toMin("11:45")),
                new Eveniment("Interviu 1", toMin("11:00"), toMin("13:00")),
                new Eveniment("Interviu 2", toMin("11:30"), toMin("12:30")),
                new Eveniment("Pranz Echipa", toMin("12:00"), toMin("14:00")),
                new Eveniment("Hackathon", toMin("13:30"), toMin("16:00"))
        ));

        // 2. Sortam evenimentele dupa ora de start (pas obligatoriu pentru algoritmii pe intervale)
        evenimente.sort(Comparator.comparingInt(Eveniment::startMin));

        System.out.println("=== VARIANTA 1: Algoritm Greedy O(N^2) ===");
        // 'rooms' va tine minte ora de final (endMin) pentru fiecare sala alocata
        List<Integer> rooms = new ArrayList<>();

        for (Eveniment ev : evenimente) {
            boolean alocat = false;
            // Cautam prima sala care s-a eliberat inainte sau fix la ora noastra de start
            for (int i = 0; i < rooms.size(); i++) {
                if (rooms.get(i) <= ev.startMin()) {
                    rooms.set(i, ev.endMin()); // Reutilizam sala, ii actualizam ora de final
                    System.out.printf("%-15s (%s - %s) -> Sala #%d\n",
                            ev.nume(), toHHMM(ev.startMin()), toHHMM(ev.endMin()), (i + 1));
                    alocat = true;
                    break;
                }
            }
            // Daca nicio sala nu e libera, deschidem o sala noua
            if (!alocat) {
                rooms.add(ev.endMin());
                System.out.printf("%-15s (%s - %s) -> Sala #%d\n",
                        ev.nume(), toHHMM(ev.startMin()), toHHMM(ev.endMin()), rooms.size());
            }
        }
        System.out.println("Numar minim de sali utilizate (Varianta 1): " + rooms.size());

        System.out.println("\n=== VARIANTA 2: PriorityQueue O(N log N) ===");
        // Min-heap care va tine mereu deasupra (peek) sala care se elibereaza cel mai devreme
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (Eveniment ev : evenimente) {
            // Daca sala care se elibereaza prima e gata fix la startul evenimentului curent (sau mai devreme), o golim
            if (!pq.isEmpty() && pq.peek() <= ev.startMin()) {
                pq.poll();
            }
            // Adaugam ora de final a evenimentului curent in heap
            pq.offer(ev.endMin());
        }
        System.out.println("Numar minim de sali confirmate (Varianta 2): " + pq.size());
    }
}