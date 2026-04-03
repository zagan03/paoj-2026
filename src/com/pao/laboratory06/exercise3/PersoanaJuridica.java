package com.pao.laboratory06.exercise3;
import java.util.*;

public class PersoanaJuridica extends Persoana implements PlataOnlineSMS {
    private List<String> smsTrimise = new ArrayList<>();

    public PersoanaJuridica(String nume, String prenume, String telefon) {
        super(nume, prenume, telefon);
    }

    @Override
    public boolean trimiteSMS(String mesaj) {
        if (mesaj == null || mesaj.isEmpty() || telefon == null || telefon.isEmpty()) {
            return false;
        }
        smsTrimise.add(mesaj);
        return true;
    }

    // Metodele din PlataOnline trebuie si ele implementate
    @Override public void autentificare(String u, String p) {}
    @Override public double consultareSold() { return 100000.0; }
    @Override public boolean efectuarePlata(double s) { return true; }

    public List<String> getSmsTrimise() { return smsTrimise; }
}