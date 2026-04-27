package com.pao.laboratory08.exercise2;

import com.pao.laboratory08.exercise1.Student;
import com.pao.laboratory08.exercise1.Adresa;
import java.io.*;
import java.util.*;

public class Main {
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește studenții din FILE_PATH cu BufferedReader
        // 2. Citește pragul de vârstă din stdin cu Scanner
        // 3. Filtrează studenții cu varsta >= prag
        // 4. Scrie filtrații în "rezultate.txt" cu BufferedWriter
        // 5. Afișează sumarul la consolă

        List<Student> totiStudentii = new ArrayList<>();
        String fisierIntrare = "src/com/pao/laboratory08/tests/studenti.txt";
        String fisierIesire = "src/com/pao/laboratory08/tests/rezultate.txt";

        // 1. Citirea din fisier (Reutilizam logica de la Ex 1)
        try (BufferedReader br = new BufferedReader(new FileReader(fisierIntrare))) {
            String linie;
            while ((linie = br.readLine()) != null) {
                if (linie.trim().isEmpty()) continue;
                String[] date = linie.split(",");
                if (date.length == 4) {
                    Adresa adr = new Adresa(date[2].trim(), date[3].trim());
                    Student st = new Student(date[0].trim(), Integer.parseInt(date[1].trim()), adr);
                    totiStudentii.add(st);
                }
            }
        } catch (IOException e) {
            System.err.println("Eroare la citirea fisierului: " + e.getMessage());
        }

        // 2. Citirea pragului de varsta
        Scanner sc = new Scanner(System.in);
        int prag = sc.nextInt();

        // 3. Filtrarea si Scrierea in fisier
        List<Student> filtrati = new ArrayList<>();
        for (Student s : totiStudentii) {
            if (s.getVarsta() >= prag) {
                filtrati.add(s);
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fisierIesire))) {
            System.out.println("Filtru: varsta >= " + prag);
            System.out.println("Rezultate: " + filtrati.size() + " studenti");
            System.out.println();

            for (Student s : filtrati) {
                // Scriere in fisier
                bw.write(s.toString());
                bw.newLine();

                // Afisare la consola
                System.out.println(s);
            }

            System.out.println();
            System.out.println("Scris in: " + fisierIesire);

        } catch (IOException e) {
            System.err.println("Eroare la scrierea fisierului: " + e.getMessage());
        }
    }
    }


