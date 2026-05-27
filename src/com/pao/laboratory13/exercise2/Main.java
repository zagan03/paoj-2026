package com.pao.laboratory13.exercise2;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    private static final int PORT = 9000;

    // CountDownLatch e perfect pentru shutdown controlat:
    // serverul asteapta pana cand acesti 2 clienti termina treaba.
    private static final CountDownLatch clientsLatch = new CountDownLatch(2);

    private static ServerSocket serverSocket;
    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws InterruptedException {
        // 1. Pornim Serverul pe un thread separat ca sa nu blocheze main-ul
        Thread serverThread = new Thread(() -> startServer());
        serverThread.start();

        // Mic delay pentru a ne asigura ca serverul asculta deja portul
        Thread.sleep(100);

        // 2. Datele hardcodate de test pentru cei doi clienti
        List<String> commandsClient1 = List.of("AUTH alice", "OPEN", "SEND hi", "CLOSE");
        List<String> commandsClient2 = List.of("AUTH bob", "OPEN", "BROADCAST x", "HISTORY", "CLOSE");

        // 3. Pornim clientii simultan prin ExecutorService
        threadPool.execute(new SimulatedClient("CLIENT-1", commandsClient1));
        threadPool.execute(new SimulatedClient("CLIENT-2", commandsClient2));

        // 4. Main-ul ramane blocat aici pana cand ambii clienti dau .countDown()
        clientsLatch.await();
        System.out.println("[SERVER] All clients done. Shutting down.");

        // 5. Oprim totul curat (inchidem mufa principala si distrugem thread-urile)
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        threadPool.shutdown();
    }

    // =========================================================================
    //                            LOGICA DE SERVER
    // =========================================================================
    private static void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("[SERVER] Listening on port " + PORT);

            while (!serverSocket.isClosed()) {
                // .accept() blocheaza pana cand un client intra pe portul 9000
                Socket clientSocket = serverSocket.accept();
                // Cum a intrat un client, delegam conexiunea catre un nou ClientHandler
                threadPool.execute(new ClientHandler(clientSocket));
            }
        } catch (SocketException e) {
            // Se arunca cand dam serverSocket.close() din afara, este shutdown-ul asteptat
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private ProtocolEngine engine;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            // IMPORTANT: Fiecare client primeste propria masinarie de stari izolata
            this.engine = new ProtocolEngine();
        }

        @Override
        public void run() {
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true) // autoFlush
            ) {
                String line;
                while ((line = in.readLine()) != null) {
                    // Procesam comanda prin logica din Ex 1
                    String response = engine.processCommand(line);
                    out.println(response); // Trimitem raspunsul inapoi clientului

                    if ("OK CLOSED".equals(response)) {
                        break; // Iesim din bucla daca s-a inchis conexiunea din protocol
                    }
                }
            } catch (IOException e) {
                System.out.println("Eroare comunicare client");
            } finally {
                try { socket.close(); } catch (IOException ignored) {}
            }
        }
    }

    // =========================================================================
    //                            LOGICA DE CLIENT
    // =========================================================================
    static class SimulatedClient implements Runnable {
        private String clientId;
        private List<String> commands;

        public SimulatedClient(String clientId, List<String> commands) {
            this.clientId = clientId;
            this.commands = commands;
        }

        @Override
        public void run() {
            try (
                    Socket socket = new Socket("localhost", PORT);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
            ) {
                System.out.println("[" + clientId + "] Connected");

                for (String cmd : commands) {
                    out.println(cmd);
                    String response = in.readLine();
                    System.out.println("[" + clientId + "] >> " + cmd + "  =>  " + response);
                    Thread.sleep(300); // 300ms delay ca sa se amestece frumos log-urile in consola
                }
                System.out.println("[" + clientId + "] Disconnected");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                clientsLatch.countDown(); // Indiferent ce se intampla, semnalizam serverului ca am terminat
            }
        }
    }

    // =========================================================================
    //            MOTORUL DE PROTOCOL (Codul refactorizat de la Ex 1)
    // =========================================================================
    static class ProtocolEngine {
        enum State { INIT, AUTH, OPEN, CLOSED }
        private State currentState = State.INIT;
        private String currentUser = "";
        private int historyCount = 0;

        public String processCommand(String line) {
            if (line == null || line.trim().isEmpty()) return "ERR E_PARSE UNKNOWN_COMMAND";
            String[] tokens = line.trim().split("\\s+");
            String command = tokens[0];

            if (currentState == State.CLOSED) return "ERR E_STATE CLOSED";

            switch (command) {
                case "AUTH":
                    if (tokens.length < 2) return "ERR E_PARSE AUTH";
                    currentUser = tokens[1];
                    currentState = State.AUTH;
                    historyCount = 0;
                    return "OK AUTH user=" + currentUser;
                case "OPEN":
                    if (tokens.length > 1) return "ERR E_PARSE OPEN";
                    if (currentState == State.OPEN) return "ERR E_STATE ALREADY_OPEN";
                    if (currentState == State.INIT) return "ERR E_STATE NOT_OPEN";
                    currentState = State.OPEN;
                    return "OK OPEN";
                case "SEND":
                    if (tokens.length < 2) return "ERR E_PARSE SEND";
                    if (currentState != State.OPEN) return "ERR E_STATE NOT_OPEN";
                    historyCount++;
                    return "OK OPEN sent";
                case "BROADCAST":
                    if (tokens.length < 2) return "ERR E_PARSE BROADCAST";
                    if (currentState != State.OPEN) return "ERR E_STATE NOT_OPEN";
                    historyCount++;
                    return "OK OPEN broadcast";
                case "HISTORY":
                    if (tokens.length > 1) return "ERR E_PARSE HISTORY";
                    if (currentState != State.OPEN) return "ERR E_STATE NOT_OPEN";
                    return "OK OPEN history=" + historyCount;
                case "CLOSE":
                    if (tokens.length > 1) return "ERR E_PARSE CLOSE";
                    if (currentState != State.OPEN) return "ERR E_STATE NOT_OPEN";
                    currentState = State.CLOSED;
                    return "OK CLOSED";
                default:
                    return "ERR E_PARSE UNKNOWN_COMMAND";
            }
        }
    }
}