package com.pao.proiect.fooddelivery.repository;

import com.pao.proiect.fooddelivery.model.User;
import com.pao.proiect.fooddelivery.model.Customer;
import com.pao.proiect.fooddelivery.model.Driver;
import com.pao.proiect.fooddelivery.model.Address;
import com.pao.proiect.fooddelivery.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements Repository<User, Integer> {
    private final Connection connection;

    public UserRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(User entity) {
        // Am adăugat câmpurile obligatorii (NOT NULL) din schema ta + AddressID
        String sql = "INSERT INTO Users (Name, Email, Role, PhoneNumber, Password, AddressID) VALUES (?, ?, ?, ?, ?, ?)";

        // Folosim RETURN_GENERATED_KEYS ca să îi dăm ID-ul înapoi obiectului!
        try (PreparedStatement stmt = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getEmail());

            // Folosim un mic truc de Java 21 (Pattern Matching) ca să extragem datele specifice din clasele copil
            if (entity instanceof Customer c) {
                stmt.setString(3, "CUSTOMER");
                stmt.setString(4, c.getPhoneNumber());    // Dacă metoda ta se numește getPhoneNumber(), modifică aici!
                stmt.setString(5, c.getPassword());

                if (c.getAddress() != null) {
                    stmt.setInt(6, c.getAddress().getId());
                } else {
                    stmt.setNull(6, java.sql.Types.INTEGER);
                }

            } else if (entity instanceof Driver d) {
                stmt.setString(3, "DRIVER");
                stmt.setString(4, d.getPhoneNumber());
                stmt.setString(5, d.getPassword());
                stmt.setNull(6, java.sql.Types.INTEGER);

            } else {
                stmt.setString(3, "USER");
                stmt.setString(4, "0000000000");
                stmt.setString(5, "default");
                stmt.setNull(6, java.sql.Types.INTEGER);
            }


            stmt.executeUpdate();


            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    entity.setId(rs.getInt(1));
                }
            }

            System.out.println("Utilizatorul " + entity.getName() + " a fost salvat cu succes in baza de date cu ID: " + entity.getId());

        } catch (SQLException e) {
            throw new RuntimeException("Eroare la salvarea utilizatorului: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<User> findById(Integer id) {
        String sql = "SELECT * FROM Users WHERE UserID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String role = rs.getString("Role");
                    User user = null;

                    if ("CUSTOMER".equalsIgnoreCase(role)) {
                        Address dummyAddress = new Address();
                        // Am adaugat dummyAddress si false (pentru isPremium)
                        user = new Customer(rs.getString("Name"), rs.getString("Email"), "0700000000", "dummyPass", dummyAddress, false);
                    } else if ("DRIVER".equalsIgnoreCase(role)) {
                        // Am adaugat "N/A" pentru vehicleNumber
                        user = new Driver(rs.getString("Name"), rs.getString("Email"), "0700000000", "dummyPass", "N/A");
                    }

                    if (user != null) {
                        user.setId(rs.getInt("UserID"));
                        return Optional.of(user);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la gasirea utilizatorului cu ID-ul: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String role = rs.getString("Role");
                User user = null;

                if ("CUSTOMER".equalsIgnoreCase(role)) {
                    Address dummyAddress = new Address();
                    // Am adaugat dummyAddress si false (pentru isPremium)
                    user = new Customer(rs.getString("Name"), rs.getString("Email"), "0700000000", "dummyPass", dummyAddress, false);
                } else if ("DRIVER".equalsIgnoreCase(role)) {
                    // Am adaugat "N/A" pentru vehicleNumber
                    user = new Driver(rs.getString("Name"), rs.getString("Email"), "0700000000", "dummyPass", "N/A");
                }

                if (user != null) {
                    user.setId(rs.getInt("UserID"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la preluarea utilizatorilor", e);
        }
        return users;
    }

    @Override
    public void update(User entity) {
        String sql = "UPDATE Users SET Name = ?, Email = ?, Role = ? WHERE UserID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getEmail());
            stmt.setString(3, entity.getRole());
            stmt.setInt(4, entity.getId());

            stmt.executeUpdate();
            System.out.println("Utilizator actualizat cu succes!");
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea utilizatorului", e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Users WHERE UserID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            stmt.executeUpdate();
            System.out.println("Utilizator sters cu succes!");
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea utilizatorului", e);
        }
    }
}