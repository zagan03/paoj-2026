package com.pao.laboratory13;

import java.util.Scanner;

public class Main {

    // 1. Definim starile posibile ale sesiunii
    enum State {
        INIT, AUTH, OPEN, CLOSED
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;

        int q = scanner.nextInt();
        scanner.nextLine(); // Consumam restul liniei ramase dupa numarul Q

        // Initializam starea motorului de protocol
        State currentState = State.INIT;
        String currentUser = "";
        int historyCount = 0;

        // Procesam cele Q comenzi
        for (int i = 0; i < q; i++) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                i--; // Ignoram liniile goale fara sa consumam din numarul de comenzi
                continue;
            }

            // Spargem linia dupa spatii (unul sau mai multe)
            String[] tokens = line.split("\\s+");
            String command = tokens[0];

            // Verificam daca sesiunea este deja CLOSED - nicio comanda nu mai e permisa
            if (currentState == State.CLOSED) {
                System.out.println("ERR E_STATE CLOSED");
                continue;
            }

            // Logica determinista pentru fiecare comanda
            switch (command) {
                case "AUTH":
                    if (tokens.length < 2) {
                        System.out.println("ERR E_PARSE AUTH");
                    } else {
                        currentUser = tokens[1];
                        currentState = State.AUTH;
                        historyCount = 0; // Resetam istoricul la AUTH sau re-AUTH
                        System.out.println("OK AUTH user=" + currentUser);
                    }
                    break;

                case "OPEN":
                    if (tokens.length > 1) {
                        System.out.println("ERR E_PARSE OPEN");
                    } else if (currentState == State.OPEN) {
                        System.out.println("ERR E_STATE ALREADY_OPEN");
                    } else if (currentState == State.INIT) {
                        System.out.println("ERR E_STATE NOT_OPEN");
                    } else if (currentState == State.AUTH) {
                        currentState = State.OPEN;
                        System.out.println("OK OPEN");
                    }
                    break;

                case "SEND":
                    if (tokens.length < 2) {
                        System.out.println("ERR E_PARSE SEND");
                    } else if (currentState != State.OPEN) {
                        System.out.println("ERR E_STATE NOT_OPEN");
                    } else {
                        historyCount++;
                        System.out.println("OK OPEN sent");
                    }
                    break;

                case "BROADCAST":
                    if (tokens.length < 2) {
                        System.out.println("ERR E_PARSE BROADCAST");
                    } else if (currentState != State.OPEN) {
                        System.out.println("ERR E_STATE NOT_OPEN");
                    } else {
                        historyCount++;
                        System.out.println("OK OPEN broadcast");
                    }
                    break;

                case "HISTORY":
                    if (tokens.length > 1) {
                        System.out.println("ERR E_PARSE HISTORY");
                    } else if (currentState != State.OPEN) {
                        System.out.println("ERR E_STATE NOT_OPEN");
                    } else {
                        System.out.println("OK OPEN history=" + historyCount);
                    }
                    break;

                case "CLOSE":
                    if (tokens.length > 1) {
                        System.out.println("ERR E_PARSE CLOSE");
                    } else if (currentState != State.OPEN) {
                        System.out.println("ERR E_STATE NOT_OPEN");
                    } else {
                        currentState = State.CLOSED;
                        System.out.println("OK CLOSED");
                    }
                    break;

                default:
                    System.out.println("ERR E_PARSE UNKNOWN_COMMAND");
                    break;
            }
        }
        scanner.close();
    }
}