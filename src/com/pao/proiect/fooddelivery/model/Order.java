package com.pao.proiect.fooddelivery.model;
import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;
public class Order {
    private static int count = 0;
    private int id;
    private Customer customer;
    private Restaurant restaurant;
    private final List<MenuItem> items;
    private OrderStatus status;
    private double totalPrice;
    private PaymentMethod paymentMethod;
    private LocalDateTime orderDate;
    private Driver assignedDriver;
    public Order(Customer customer, Restaurant restaurant, List<MenuItem> items,
                 OrderStatus status, PaymentMethod paymentMethod) {
        this.id = ++count;
        this.customer = customer;
        this.restaurant = restaurant;
        this.items = new ArrayList<>(items);
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.orderDate = LocalDateTime.now();
        this.totalPrice = calculateTotalPrice();
    }
    private double calculateTotalPrice() {
        double sum = 0;
        for (MenuItem item : this.items) {
            sum += item.getPrice();
        }
        return sum;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public Customer getCustomer() {return customer;}
    public int getId() {return id;}
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public void setAssignedDriver(Driver driver) {
        this.assignedDriver = driver;
    }
}
