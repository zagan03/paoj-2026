package com.pao.proiect.fooddelivery.repository;
import com.pao.proiect.fooddelivery.model.OrderStatus;
import com.pao.proiect.fooddelivery.util.DatabaseConnection;

import java.sql.*;

public class ReportRepository{
    private final Connection connection;
    public ReportRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    public void printCustomerOrderHistory(int customerId) {
        String sql = "SELECT o.OrderID, o.TotalPrice, o.Status, c.Name, c.Email " +
                "FROM Orders o " +
                "JOIN Users c ON o.CustomerID = c.UserID " +
                "WHERE c.UserID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, customerId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("OrderID");
                double totalPrice = rs.getDouble("TotalPrice");
                String status = rs.getString("Status");
                String name = rs.getString("Name");
                String email = rs.getString("Email");

                System.out.println("Comanda #" + orderId + " | Client: " + name + " (" + email + ") | Total: " + totalPrice + " lei | Status: " + status);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException("Eroare la afisarea Istoricului Clientului", e);
        }
    }

    public void topProductsSold() {
        String sql = "select m.name, sum(quantity) as \"Total quantity ordered\" " +
                "from menuitems m " +
                "join orderitems o on o.itemid = m.itemid " +
                "group by m.itemid " +
                "order by sum(quantity) desc " +
                "limit 5";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String itemName = rs.getString("Name");
                int totalQuantity = rs.getInt("Total quantity ordered");
                System.out.println("Produs: " + itemName + " | Cantitate vanduta: " +  totalQuantity);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException("Eroare la afisare Top Produse", e);
        }
    }
    public void printRestaurantOrders(int restaurantId) {
        String sql = "SELECT o.OrderID, o.TotalPrice, r.Name AS RestaurantName " +
                "FROM Orders o " +
                "JOIN Restaurants r ON o.restaurantid = r.restaurantid " +
                "WHERE r.restaurantid = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, restaurantId);
            ResultSet rs = preparedStatement.executeQuery();

            boolean hasOrders = false;
            while (rs.next()) {
                hasOrders = true;
                int orderId = rs.getInt("OrderID");
                double totalPrice = rs.getDouble("TotalPrice");
                String restaurantName = rs.getString("RestaurantName");

                System.out.println("Restaurant: " + restaurantName + " | Comanda #" + orderId + " | Total: " + totalPrice + " lei");
            }

            if (!hasOrders) {
                System.out.println("Nicio comanda gasita pentru acest restaurant.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Eroare la afisarea comenzilor pentru restaurant", e);
        }
    }


}
