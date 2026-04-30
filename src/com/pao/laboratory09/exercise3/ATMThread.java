package com.pao.laboratory09.exercise3;

import com.pao.laboratory09.exercise1.TipTranzactie;
import com.pao.laboratory09.exercise1.Tranzactie;

public class ATMThread extends Thread {
    private int idATM;
    private CoadaTranzactii coada;

    public ATMThread(int idATM, CoadaTranzactii coada) {
        this.idATM = idATM;
        this.coada = coada;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 4; i++) {
                int id = idATM * 100 + i;

                Tranzactie t = new Tranzactie(id, 150.0, "2024-05-20", "ATM-CONT", "DEST-CONT", TipTranzactie.DEBIT);

                System.out.println("[ATM-" + idATM + "] trimite: Tranzactie #" + t.getId() + " " + t.getSuma() + " RON");
                coada.adauga(t, idATM);

                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}