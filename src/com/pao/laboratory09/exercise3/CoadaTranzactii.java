package com.pao.laboratory09.exercise3;

import com.pao.laboratory09.exercise1.Tranzactie;
import java.util.LinkedList;
import java.util.Queue;

public class CoadaTranzactii {
    private Queue<Tranzactie> banda = new LinkedList<>();
    private final int CAPACITATE = 5;

    public synchronized void adauga(Tranzactie t, int idATM) throws InterruptedException {
        while (banda.size() == CAPACITATE) {
            System.out.println("[ATM-" + idATM + "] astept loc...");
            wait();
        }
        banda.add(t);
        notifyAll();
    }

    public synchronized Tranzactie extrage() throws InterruptedException {
        while (banda.isEmpty()) {
            wait();
            // Daca a fost trezit de Main la final si banda tot e goala, oprim bucla.
            if (banda.isEmpty()) {
                return null;
            }
        }
        Tranzactie t = banda.poll();
        notifyAll();
        return t;
    }

    public synchronized boolean esteGoala() {
        return banda.isEmpty();
    }
}