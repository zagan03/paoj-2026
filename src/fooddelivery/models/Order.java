package fooddelivery.models;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
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
    public Order(Customer customer, Restaurant restaurant, List<MenuItem> items,
                 OrderStatus status, PaymentMethod paymentMethod) {
        this.id = ++count;
        this.customer = customer;
        this.restaurant = restaurant;
        this.items = items;
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
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
