package com.pao.laboratory09.exercise3;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        CoadaTranzactii coada = new CoadaTranzactii();

        ATMThread atm1 = new ATMThread(1, coada);
        ATMThread atm2 = new ATMThread(2, coada);
        ATMThread atm3 = new ATMThread(3, coada);

        ProcessorThread processor = new ProcessorThread(coada);
        Thread processorThread = new Thread(processor);

        // 1. Pornim tot
        atm1.start();
        atm2.start();
        atm3.start();
        processorThread.start();

        // 2. Asteptam sa termine bancomatele de pus pe banda
        atm1.join();
        atm2.join();
        atm3.join();

        // 3. Oprim procesorul si ii dam notify sa se trezeasca
        processor.activ = false;
        synchronized (coada) {
            coada.notifyAll();
        }

        // 4. Asteptam sa isi incheie treaba
        processorThread.join();
    }
}