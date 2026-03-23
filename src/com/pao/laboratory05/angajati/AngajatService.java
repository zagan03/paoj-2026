package com.pao.laboratory05.angajati;
import java.util.Arrays;
import java.util.Comparator;
public class AngajatService {
    private Angajat[] angajati;

    private AngajatService() {
        this.angajati = new Angajat[0];
    }
    private static class Holder {
        private static final AngajatService INSTANCE = new AngajatService();
    }
    public static AngajatService getInstance(){
        return Holder.INSTANCE;
    }

    public void addAngajat(Angajat a) {
        Angajat[] copie = new Angajat[angajati.length + 1];
        System.arraycopy(angajati, 0, copie, 0, angajati.length);
        copie[angajati.length] = a;
        this.angajati = copie;
        System.out.println("Am adaugat angajatul " + a.getNume());
    }
    public void printAll() {
        for (Angajat a : angajati) {
            System.out.println(a);
        }
    }
    public void listBySalary() {
        Angajat[] clona = angajati.clone();
        Arrays.sort(clona);
        for(Angajat a : clona) {
            System.out.println(a);
        }
    }
    public void findByDepartament(String numeDept) {
        boolean found = false;
        for (Angajat a : angajati) {
            if(a.getDepartament().nume().equalsIgnoreCase(numeDept)) {
                System.out.println(a);
                found = true;
            }
        }
        if (!found) System.out.println("Niciun angajat gasit in departamentul: "+ numeDept);
    }
}
