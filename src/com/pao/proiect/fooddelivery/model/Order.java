package com.pao.proiect.fooddelivery.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int count = 0;
    private int id;
    private Customer customer;
    private Restaurant restaurant;
    private List<MenuItem> items;
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


    public Order() {
        this.items = new ArrayList<>();
    }

    private double calculateTotalPrice() {
        double sum = 0;
        for (MenuItem item : this.items) {
            sum += item.getPrice();
        }
        return sum;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Restaurant getRestaurant() { return restaurant; }
    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }


    public List<MenuItem> getItems() { return items; }
    public void setItems(List<MenuItem> items) { this.items = items; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public Driver getAssignedDriver() { return assignedDriver; }
    public void setAssignedDriver(Driver driver) { this.assignedDriver = driver; }
}