package com.pao.laboratory08.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    // Calea către fișierul cu date — relativă la rădăcina proiectului
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        //
        // 1. Citește studenții din FILE_PATH cu BufferedReader
        // 2. Citește comanda din stdin: PRINT, SHALLOW <nume> sau DEEP <nume>
        // 3. Execută comanda:
        //    - PRINT → afișează toți studenții
        //    - SHALLOW <nume> → shallow clone + modifică orașul clonei la "MODIFICAT" + afișează
        //    - DEEP <nume> → deep clone + modifică orașul clonei la "MODIFICAT" + afișează
        List<Student> studenti = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linie;
            while((linie = br.readLine()) != null) {
                if (linie.trim().isEmpty()) continue;
                String[] date = linie.split(",");
                int varsta = Integer.parseInt(date[1].trim());
                Adresa adr = new Adresa(date[2].trim(), date[3].trim());
                Student st = new Student(date[0].trim(), varsta, adr);
                studenti.add(st);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextLine()) {
            String linieComanda = sc.nextLine();
            String[] parts = linieComanda.split(" ");
            String tipComanda = parts[0];
            if (tipComanda.equals("PRINT")) {
                for (Student s : studenti) {
                    System.out.println(s);
                }
            }
            else if (tipComanda.equals("SHALLOW") || tipComanda.equals("DEEP") ) {
                Student studentGasit = null;
                for (Student s : studenti) {
                    if (s.getNume().equals(parts[1])) {
                        studentGasit = s;
                        break;
                    }
                }
                if (studentGasit != null) {
                    if (tipComanda.equals("SHALLOW")) {
                        Student clona = new Student(studentGasit.getNume(), studentGasit.getVarsta(), studentGasit.getAdresa());
                        clona.getAdresa().setOras("MODIFICAT");

                        System.out.println("Original: " + studentGasit);
                        System.out.println("Clona: " + clona);
                    }
                    else {
                        Student clona = (Student) studentGasit.clone();
                        clona.getAdresa().setOras("MODIFICAT");
                        System.out.println("Original: " + studentGasit);
                        System.out.println("Clona: " + clona);
                    }
                }
            }
        }
    }
}
