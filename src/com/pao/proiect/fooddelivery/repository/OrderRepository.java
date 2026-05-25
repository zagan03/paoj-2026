package com.pao.proiect.fooddelivery.repository;

import com.pao.proiect.fooddelivery.model.Order;
import com.pao.proiect.fooddelivery.model.MenuItem;
import com.pao.proiect.fooddelivery.model.OrderStatus; // Presupunand ca ai acest enum
import com.pao.proiect.fooddelivery.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository implements Repository<Order, Integer> {
    private final Connection connection;

    public OrderRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Order entity) {
        String sqlOrder = "INSERT INTO Orders (CustomerID, RestaurantID, TotalPrice, Status) VALUES (?, ?, ?, ?)";
        String sqlItem = "INSERT INTO OrderItems (OrderID, MenuItemID, Quantity) VALUES (?, ?, ?)";

        try {
            // Incepem tranzactia explicit
            connection.setAutoCommit(false);

            try (PreparedStatement stmtOrder = connection.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS)) {

                stmtOrder.setInt(1, entity.getCustomer().getId());
                stmtOrder.setInt(2, entity.getRestaurant().getId());
                stmtOrder.setDouble(3, entity.getTotalPrice());
                stmtOrder.setString(4, entity.getStatus().name()); // Salvam statusul ca text
                stmtOrder.executeUpdate();

                int orderId;
                try (ResultSet generatedKeys = stmtOrder.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orderId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Eroare la obtinerea ID-ului comenzii.");
                    }
                }

                try (PreparedStatement stmtItem = connection.prepareStatement(sqlItem)) {
                    for (MenuItem item : entity.getItems()) {
                        stmtItem.setInt(1, orderId);
                        stmtItem.setInt(2, item.getId());
                        stmtItem.setInt(3, 1);
                        stmtItem.addBatch();
                    }
                    stmtItem.executeBatch();
                }
            }

            connection.commit();
            System.out.println("Comanda a fost salvata cu succes (Tranzactie reusita)!");

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Eroare la rollback!", rollbackEx);
            }
            throw new RuntimeException("Tranzactia a picat! S-a efectuat rollback.", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Eroare la resetarea auto-commit-ului.", e);
            }
        }
    }

    @Override
    public Optional<Order> findById(Integer id) {
        String sql = "SELECT * FROM Orders WHERE OrderID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("OrderID"));
                    order.setTotalPrice(rs.getDouble("TotalPrice"));

                    return Optional.of(order);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la gasirea comenzii", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("OrderID"));
                order.setTotalPrice(rs.getDouble("TotalPrice"));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la preluarea comenzilor", e);
        }
        return orders;
    }

    @Override
    public void update(Order entity) {
        String sql = "UPDATE Orders SET Status = ?, TotalPrice = ? WHERE OrderID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getStatus().name());
            stmt.setDouble(2, entity.getTotalPrice());
            stmt.setInt(3, entity.getId());
            stmt.executeUpdate();
            System.out.println("Comanda actualizata cu succes!");
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea comenzii", e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sqlDeleteItems = "DELETE FROM OrderItems WHERE OrderID = ?";
        String sqlDeleteOrder = "DELETE FROM Orders WHERE OrderID = ?";

        try {
            connection.setAutoCommit(false);

            // Pasul 1: Stergem copiii (produsele) mai intai!
            try (PreparedStatement stmtItems = connection.prepareStatement(sqlDeleteItems)) {
                stmtItems.setInt(1, id);
                stmtItems.executeUpdate();
            }

            // Pasul 2: Stergem parintele (comanda) la final
            try (PreparedStatement stmtOrder = connection.prepareStatement(sqlDeleteOrder)) {
                stmtOrder.setInt(1, id);
                stmtOrder.executeUpdate();
            }
            connection.commit();
            System.out.println("Comanda si produsele aferente au fost sterse cu succes!");

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Eroare la rollback stergere!", rollbackEx);
            }
            throw new RuntimeException("Eroare la stergerea comenzii, s-a dat rollback", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Eroare la resetarea auto-commit", e);
            }
        }
    }
}