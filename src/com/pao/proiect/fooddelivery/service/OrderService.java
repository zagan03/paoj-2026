package com.pao.proiect.fooddelivery.service;
import com.pao.proiect.fooddelivery.exception.EmptyCartException;
import com.pao.proiect.fooddelivery.exception.RestaurantClosedException;
import com.pao.proiect.fooddelivery.exception.NoDriverAvailableException;
import com.pao.proiect.fooddelivery.model.*;
import java.util.*;


public class OrderService {
    private final AuditService auditService = AuditService.getInstance();
    private static OrderService instance;
    private HashMap<Integer, List<Order>> orderHistory;

    private OrderService() {
        this.orderHistory = new HashMap<>();
    }

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderService();
        }
        return instance;
    }
    public Order searchOrderById(int orderId) {
        auditService.log("cauta_comanda_dupa_id");
        for (List<Order> clientOrders : orderHistory.values()) {
            for (Order order : clientOrders) {
                if (order.getId() == orderId) {
                    return order;
                }
            }
        }
        throw new RuntimeException("Comanda cu ID-ul #" + orderId + " nu a fost gasita in sistem!");
    }
    public void addOrderToClientHistory(Customer customer, Order order) {
        auditService.log("adauga_comanda_istoric_client");
        int clientId = customer.getId();
        if (!orderHistory.containsKey(customer.getId())) {
            List<Order> orders = new ArrayList<>();
            orders.add(order);
            orderHistory.put(clientId, orders);
        }
        else {
            orderHistory.get(clientId).add(order);
        }
    }

    public void placeOrder(Customer customer, Restaurant restaurant, PaymentMethod method) {
        auditService.log("plaseaza_comanda");
        if (!restaurant.getOpen()) {
            throw new RestaurantClosedException("Ne pare rau! Restaurantul " + restaurant.getName() + " este inchis!");
        }
        if (customer.isCartEmpty()) {
            throw new EmptyCartException("Cosul este gol! Trebuie sa adaugati produse in cos inainte de a plasa comanda!");
        }
        // cautare sofer
        Driver availableDriver = findAvailableDriver();
        if (availableDriver == null ) {
            throw new NoDriverAvailableException("Ne pare rau! NU avem soferi disponibili momentan!");
        }

        // creare comanda
        List<MenuItem> cartItems = new ArrayList<>(customer.getCartItems());
        Order order = new Order(customer, restaurant, cartItems, OrderStatus.PLACED, method);
        order.setAssignedDriver(availableDriver);
        availableDriver.setCurrentOrder(order);
        availableDriver.setAvailability(false);
        // generare chitanta
        PaymentService paymentService = PaymentService.getInstance();
        TransactionRecord receipt = new TransactionRecord(order.getId(), order.getTotalPrice());
        paymentService.addTransaction(receipt);
        System.out.println("Tranzactie confirmata! Chitanta #" + receipt.getId());
        addOrderToClientHistory(customer, order);
        customer.clearCart();
    }
    // actiune initiata de sofer aici
    public void deliverOrder(Driver driver) {
        auditService.log("livrare_comanda");
        driver.getCurrentOrder().setStatus(OrderStatus.DELIVERED);
        driver.completeOrder();
        System.out.println("Soferul " + driver.getName() + " a livrat comanda cu succes");
    }

    private Driver findAvailableDriver() {
        UserService userService = UserService.getInstance();
        for (Driver driver : userService.getAllDrivers()) {
            if (driver.isAvailable()) {
                return driver;
            }
        }
        return null;
    }

    public void printCustomerHistory(int clientId) {
        auditService.log("afisare_istoric_comenzi");
        System.out.println("Istoric comenzi pentru clientul ID: " + clientId);
        List<Order> history = orderHistory.get(clientId);

        if (history == null || history.isEmpty()) {
            System.out.println("Clientul nu are nicio comanda in istoric.");
            return;
        }

        for (Order o : history) {
            System.out.println("- Comanda #" + o.getId() + " | Total: " + o.getTotalPrice() + " RON | Status: " + o.getStatus());
        }
    }
}
