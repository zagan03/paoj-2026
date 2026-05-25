package com.pao.proiect.fooddelivery.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private static AuditService instance;

    private AuditService() {
    }

    public static AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public synchronized void log(String actionName) {
        try (FileWriter fw = new FileWriter("audit.csv", true);
             PrintWriter pw = new PrintWriter(fw)) {

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            pw.println(actionName + "," + now);

        } catch (IOException e) {
            throw new RuntimeException("Eroare la scrierea in fisierul de audit", e);
        }
    }
}
