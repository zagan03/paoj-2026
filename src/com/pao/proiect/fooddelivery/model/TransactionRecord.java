package com.pao.proiect.fooddelivery.model;
import java.time.LocalDateTime;
public final class TransactionRecord {
    private static int count = 0;
    private final int id;
    private final int orderId;
    private final double amount;
    private final LocalDateTime paymentTime;

    public TransactionRecord(int orderId, double amount) {
        this.id = ++count;
        this.orderId = orderId;
        this.amount = amount;
        this.paymentTime = LocalDateTime.now();
    }

    // Getteri
    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    @Override
    public String toString() {
        return "Transaction ID: " + id + " | Order ID: " + orderId + " | Amount: " + amount + " | " + paymentTime;
    }
}
