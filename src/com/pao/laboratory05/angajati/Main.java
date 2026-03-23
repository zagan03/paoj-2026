package com.pao.laboratory05.angajati;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AngajatService service = AngajatService.getInstance();

        while (true) {
            System.out.println("\n===== Gestionare Angajați =====");
            System.out.println("1. Adaugă angajat");
            System.out.println("2. Listare dupa salariu"); // Sau ce logică de listare mai ai
            System.out.println("3. Cautare după departament");
            System.out.println("0. Ieșire");
            System.out.print("Opțiune: ");

            int optiune = scanner.nextInt();
            scanner.nextLine();

            if (optiune == 0) {
                System.out.println("La revedere!");
                break;
            }

            switch (optiune) {
                case 1:
                    System.out.print("Nume: ");
                    String nume = scanner.nextLine();

                    System.out.print("Nume departament: ");
                    String numeDep = scanner.nextLine();

                    System.out.print("Locație departament: ");
                    String locatieDep = scanner.nextLine();

                    System.out.print("Salariu: ");
                    double salariu = scanner.nextDouble();
                    scanner.nextLine(); // Din nou, curățăm buffer-ul

                    // Construim obiectele folosind Record-urile
                    Departament dep = new Departament(numeDep, locatieDep);
                    Angajat angajat = new Angajat(nume, salariu, dep);

                    service.addAngajat(angajat);
                    break;

                case 2:
                    service.listBySalary();
                    break;

                case 3:
                    System.out.print("Introdu numele departamentului pentru căutare: ");
                    String depCautat = scanner.nextLine();
                    service.findByDepartament(depCautat);
                    break;

                default:
                    System.out.println("Opțiune invalidă!");
            }
        }
        scanner.close();
    }
}