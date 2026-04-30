package com.pao.laboratory09.exercise3;

import com.pao.laboratory09.exercise1.Tranzactie;

public class ProcessorThread implements Runnable {
    private CoadaTranzactii coada;
    public volatile boolean activ = true;

    public ProcessorThread(CoadaTranzactii coada) {
        this.coada = coada;
    }

    @Override
    public void run() {
        int total = 0;
        try {
            while (activ || !coada.esteGoala()) {
                Tranzactie t = coada.extrage();

                if (t != null) { // Daca t nu e null, am extras cu succes
                    System.out.println("[Processor] Factura #" + t.getId() + " - " + t.getSuma() + " RON | " + t.getData());
                    total++;
                    Thread.sleep(80);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Toate tranzactiile procesate. Total: " + total);
    }
}