package com.pao.laboratory09.exercise2;

import com.pao.laboratory09.exercise1.TipTranzactie;
import com.pao.laboratory09.exercise1.Tranzactie;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;
import java.nio.charset.StandardCharsets;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;

    public static void main(String[] args) throws Exception {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data tip)
        // 2. Scrie toate înregistrările în OUTPUT_FILE cu DataOutputStream (format binar, RECORD_SIZE=32 bytes/înreg.)
        //    - bytes 0-3:   id (int, little-endian via ByteBuffer)
        //    - bytes 4-11:  suma (double, little-endian via ByteBuffer)
        //    - bytes 12-21: data (String, 10 chars ASCII, paddat cu spații la dreapta)
        //    - byte 22:     tip (0=CREDIT, 1=DEBIT)
        //    - byte 23:     status (0=PENDING, 1=PROCESSED, 2=REJECTED)
        //    - bytes 24-31: padding (zerouri)
        // 3. Procesează comenzile din stdin până la EOF cu RandomAccessFile:
        //    - READ idx       → seek(idx * RECORD_SIZE), citește și afișează înregistrarea
        //    - UPDATE idx ST  → seek(idx * RECORD_SIZE + 23), scrie noul status (0/1/2)
        //                       afișează "Updated [idx]: STATUS"
        //    - PRINT_ALL      → citește și afișează toate înregistrările
        //
        // Format linie output:
        //   [idx] id=<id> data=<data> tip=<CREDIT|DEBIT> suma=<suma:.2f> RON status=<STATUS>

        // 1. Citim n si n tranzactii
        Scanner sc = new Scanner(System.in);
        File folder = new File("output");
        if (!folder.exists()) {
            folder.mkdir();
        }
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();

        // 2. Scriere inregistrari
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            for (int i = 0; i < n; i++) {
                int id = sc.nextInt();
                double suma = sc.nextDouble();
                String data = sc.next();
                TipTranzactie tip = TipTranzactie.valueOf(sc.next());

                // [Offset 0] ID (int, 4 bytes, little-endian)
                dos.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(id).array());

                // [Offset 4] Suma (double, 8 bytes, little-endian)
                dos.write(ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(suma).array());

                // [Offset 12] Data (10 bytes, ASCII, paddat cu spații)
                // %-10s adaugă spații la DREAPTA până la 10 caractere
                byte[] dataBytes = data.getBytes(StandardCharsets.US_ASCII);
                dos.write(dataBytes);
                int paddingNecesar = 10 - dataBytes.length;
                for (int j = 0; j < paddingNecesar; j++) {
                    dos.write(' ');
                }

                // [Offset 22] Tip (1 byte: 0 pentru CREDIT, 1 pentru DEBIT)
                dos.write(tip == TipTranzactie.CREDIT ? 0 : 1);

                // [Offset 23] Status (1 byte: 0 pentru PENDING)
                dos.write(0);

                // [Offset 24] Padding (8 bytes de zero)
                // Până aici am scris 4 + 8 + 10 + 1 + 1 = 24 bytes.
                // Pentru a ajunge la RECORD_SIZE (32), mai punem 8 bytes de zero.
                dos.write(new byte[8]);
            }
            // 3. procesare comenzi
            try (RandomAccessFile raf = new RandomAccessFile(OUTPUT_FILE, "rw")) {
                while (sc.hasNext()) {
                    String comanda = sc.next();
                    if (comanda.equals("READ")) {
                        int idx = sc.nextInt();
                        if (idx < n) {
                            raf.seek(idx * RECORD_SIZE);
                            byte[] buffer = new byte[RECORD_SIZE];
                            raf.read(buffer);
                            ByteBuffer bb = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN);
                            int id = bb.getInt();
                            double suma = bb.getDouble();
                            byte[] dataB = new byte[10];
                            bb.get(dataB);
                            String dataStr = new String(dataB, StandardCharsets.US_ASCII).trim();
                            int tipByte = bb.get();    // Offset 22
                            int statusByte = bb.get(); // Offset 23

                            String tipStr = (tipByte == 0) ? "CREDIT" : "DEBIT";
                            String statusStr = (statusByte == 0) ? "PENDING" : (statusByte == 1 ? "PROCESSED" : "REJECTED");
                            System.out.printf("[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s%n",
                                    idx, id, dataStr, tipStr, suma, statusStr);
                        }
                    } else if (comanda.equals("UPDATE")) {
                        int idx = sc.nextInt();
                        String noulStatusStr = sc.next();
                        int noulStatusByte = noulStatusStr.equals("PENDING") ? 0 : (noulStatusStr.equals("PROCESSED") ? 1 : 2);
                        raf.seek(idx * RECORD_SIZE + 23);
                        raf.write(noulStatusByte);
                        System.out.println("Updated [" + idx + "]: " + noulStatusStr);
                    } else if (comanda.equals("PRINT_ALL")) {
                        for (int i = 0; i < n; i++) {
                            raf.seek(i * RECORD_SIZE);
                            byte[] buffer = new byte[RECORD_SIZE];
                            raf.read(buffer);

                            // desfaecm pachetul
                            ByteBuffer bb = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN);
                            int id = bb.getInt();
                            double suma = bb.getDouble();
                            byte[] dataB = new byte[10];
                            bb.get(dataB);
                            String dataStr = new String(dataB, StandardCharsets.US_ASCII).trim();
                            int tipB = bb.get();
                            int statusB = bb.get();
                            // traducem bytes in text
                            String tipS = (tipB == 0) ? "CREDIT" : "DEBIT";
                            String statusS = (statusB == 0) ? "PENDING" : (statusB == 1 ? "PROCESSED" : "REJECTED");
                            System.out.printf("[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s%n",
                                    i, id, dataStr, tipS, suma, statusS);
                        }
                    }
                }
            }
        }
    }
}

