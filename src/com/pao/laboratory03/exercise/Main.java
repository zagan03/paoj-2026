package com.pao.laboratory03.exercise;

import com.pao.laboratory03.exercise.model.Subject;
import com.pao.laboratory03.exercise.service.StudentService;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // TODO: obține instanța StudentService (Singleton)
        StudentService service = StudentService.getInstance();

        System.out.println("=== Sistem Gestiune Studenți ===");

        boolean running = true;
        while (running) {
            System.out.println("\n--- Meniu ---");
            System.out.println("1. Adaugă student");
            System.out.println("2. Adaugă notă");
            System.out.println("3. Afișează toți studenții");
            System.out.println("4. Top studenți (după medie)");
            System.out.println("5. Media pe materie");
            System.out.println("0. Ieșire");
            System.out.print("Opțiune: ");

            String option = scanner.nextLine().trim();

            try {
                switch (option) {
                    case "1":
                        System.out.print("Nume: ");
                        String name = scanner.nextLine().trim();
                        System.out.print("Vârsta: ");
                        int age = Integer.parseInt(scanner.nextLine().trim());
                        // TODO: apelează service.addStudent(name, age)
                        service.addStudent(name, age);
                        System.out.println("Student adăugat cu succes!");
                        break;

                    case "2":
                        System.out.print("Nume student: ");
                        String studentName = scanner.nextLine().trim();
                        // TODO: afișează Subject.values()
                        System.out.print("Materie (" + Arrays.toString(Subject.values()) + "): ");
                        String subjectStr = scanner.nextLine().trim().toUpperCase();
                        System.out.print("Nota (1-10): ");
                        double grade = Double.parseDouble(scanner.nextLine().trim());

                        // TODO: convertește subjectStr în Subject cu valueOf()
                        Subject subject = Subject.valueOf(subjectStr);

                        // TODO: apelează service.addGrade(studentName, subject, grade)
                        service.addGrade(studentName, subject, grade);
                        System.out.println("Notă adăugată!");
                        break;

                    case "3":
                        // TODO: apelează service.printAllStudents()
                        service.printAllStudents();
                        break;

                    case "4":
                        // TODO: apelează service.printTopStudents()
                        service.printTopStudents();
                        break;

                    case "5":
                        // TODO: apelează service.getAveragePerSubject() și afișează
                        System.out.println("=== Media pe materie ===");
                        Map<Subject, Double> avgs = service.getAveragePerSubject();
                        if (avgs.isEmpty()) {
                            System.out.println("Nu există note introduse.");
                        } else {
                            for (Map.Entry<Subject, Double> entry : avgs.entrySet()) {
                                System.out.printf("%s: %.2f%n", entry.getKey().name(), entry.getValue());
                            }
                        }
                        break;

                    case "0":
                        running = false;
                        System.out.println("La revedere!");
                        break;

                    default:
                        System.out.println("Opțiune invalidă.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Eroare: Introdu un număr valid.");
            } catch (IllegalArgumentException e) {
                // Aceasta prinde și erorile de la Subject.valueOf() dacă scrii o materie greșită
                System.out.println("Eroare: Materie invalidă sau argument greșit.");
            } catch (RuntimeException e) {
                // Aceasta prinde InvalidStudentException, InvalidGradeException, StudentNotFoundException etc.
                System.out.println("Eroare: " + e.getMessage());
            }
        }

        scanner.close();
    }
}