package com.pao.proiect.fooddelivery.service;

import com.pao.proiect.fooddelivery.model.TransactionRecord;
import java.util.List;
import java.util.ArrayList;
public class PaymentService {
    private static PaymentService instance;
    private List<TransactionRecord> transactions;

    private PaymentService() {
        transactions = new ArrayList<>();
    }

    public static PaymentService getInstance() {
        if (instance == null) {
            instance = new PaymentService();
        }
        return instance;
    }
    public void addTransaction(TransactionRecord tr) {
        transactions.add(tr);
    }
    // todo: logica calculat profit total

    public TransactionRecord searchTransactionById(int id) {
        for (TransactionRecord t : transactions) {
            if (t.getId() == id) {
                return t;
            }
        }
        throw new RuntimeException("Tranzactia " + id + " nu a fost gasita!");
    }
}
