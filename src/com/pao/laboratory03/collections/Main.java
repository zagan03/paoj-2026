package com.pao.laboratory03.collections;

import java.util.*;

/**
 * Exercițiul 1 — Colecții: HashMap și TreeMap
 *
 * Creează în acest main:
 *
 * PARTEA A — HashMap (frecvența cuvintelor)
 * 1. Declară un array de String-uri:
 *    String[] words = {"java", "python", "java", "c++", "python", "java", "rust", "c++", "go"};
 * 2. Creează un HashMap<String, Integer> care contorizează de câte ori apare fiecare cuvânt.
 *    - Parcurge array-ul și folosește put() + getOrDefault() pentru a incrementa contorul.
 * 3. Afișează map-ul.
 * 4. Verifică dacă există cheia "rust" cu containsKey().
 * 5. Afișează DOAR cheile (keySet()), apoi DOAR valorile (values()).
 * 6. Parcurge map-ul cu entrySet() și afișează "cheia -> valoarea" pentru fiecare intrare.
 *
 * PARTEA B — TreeMap (sortare automată)
 * 7. Creează un TreeMap<String, Integer> din același HashMap (constructor cu argument).
 * 8. Afișează TreeMap-ul — observă ordinea alfabetică a cheilor.
 * 9. Folosește firstKey() și lastKey() pentru a afișa prima și ultima cheie.
 *
 * PARTEA C — Map cu obiecte
 * 10. Creează un HashMap<String, List<String>> care asociază materii cu liste de studenți.
 *     Exemplu: "PAOJ" -> ["Ana", "Mihai", "Ion"], "BD" -> ["Ana", "Elena"]
 * 11. Afișează toți studenții de la materia "PAOJ".
 * 12. Adaugă un student nou la "BD" și afișează lista actualizată.
 */
public class Main {
    public static void main(String[] args) {
        // === PARTEA A: HashMap — frecventa cuvintelor ===
        System.out.println("=== PARTEA A: HashMap — frecventa cuvintelor ===");
        String[] words = {"java", "python", "java", "c++", "python", "java", "rust", "c++", "go"};

        Map<String, Integer> freq = new HashMap<>();
        for (String word : words) {
            freq.put(word, freq.getOrDefault(word, 0) + 1);
        }

        System.out.println("Frecventa: " + freq);
        System.out.println("Contine 'rust'? " + freq.containsKey("rust"));
        System.out.println("Chei: " + freq.keySet());
        System.out.println("Valori: " + freq.values());

        for (Map.Entry<String, Integer> entry : freq.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        // === PARTEA B: TreeMap — sortare automată ===
        System.out.println("\n=== PARTEA B: TreeMap — sortare automata ===");
        TreeMap<String, Integer> sorted = new TreeMap<>(freq);
        System.out.println("Sortat: " + sorted);
        System.out.println("Prima cheie: " + sorted.firstKey());
        System.out.println("Ultima cheie: " + sorted.lastKey());

        // === PARTEA C: Map cu obiecte ===
        System.out.println("\n=== PARTEA C: Map cu obiecte ===");
        Map<String, List<String>> materii = new HashMap<>();
        materii.put("PAOJ", new ArrayList<>(Arrays.asList("Ana", "Mihai", "Ion")));
        materii.put("BD", new ArrayList<>(Arrays.asList("Ana", "Elena")));

        System.out.println("Studenti la PAOJ: " + materii.get("PAOJ"));

        materii.get("BD").add("George");
        System.out.println("Studenti la BD (actualizat): " + materii.get("BD"));
    }
}
