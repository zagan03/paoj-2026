package com.pao.laboratory14.exercise2;

import com.pao.laboratory14.exercise2.model.Eveniment;
import com.pao.laboratory14.exercise2.repository.EvenimentRepository;
import com.pao.laboratory14.exercise1.TipBilet;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EvenimentRepository repo = new EvenimentRepository();
        repo.initSchema(); // Resetam baza de date la fiecare rulare, pentru teste deterministe

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String comanda = scanner.next();

            switch (comanda) {
                case "ADD":
                    String nume = scanner.next();
                    String data = scanner.next();
                    int cap = scanner.nextInt();
                    TipBilet tip = TipBilet.valueOf(scanner.next());

                    Eveniment ev = new Eveniment(0, nume, data, cap, tip);
                    repo.save(ev);
                    System.out.println("Adaugat: [" + ev.getId() + "] " + ev.getNume());
                    break;

                case "LIST":
                    List<Eveniment> evenimente = repo.findAll();
                    for (Eveniment e : evenimente) {
                        System.out.println(e.toString());
                    }
                    break;

                case "DELETE":
                    int idSters = scanner.nextInt();
                    int affectedRows = repo.deleteImpl(idSters);
                    if (affectedRows > 0) {
                        System.out.println("Sters: " + idSters);
                    } else {
                        System.out.println("Nu exista: " + idSters);
                    }
                    break;

                case "COUNT":
                    System.out.println("Total: " + repo.count());
                    break;
            }
        }
        scanner.close();
    }
}