package com.pao.proiect.fooddelivery.repository;

import com.pao.proiect.fooddelivery.model.Address;
import com.pao.proiect.fooddelivery.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddressRepository implements Repository<Address, Integer> {
    private final Connection connection;

    public AddressRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Address entity) {
        String sql = "INSERT INTO Addresses (City, Street, Number, Details) VALUES (?, ?, ?, ?)";


        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getCity());
            preparedStatement.setString(2, entity.getStreet());
            preparedStatement.setString(3, entity.getNumber());

            String details = entity.getDetails() != null ? entity.getDetails() : "-";
            preparedStatement.setString(4, details);

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    entity.setId(generatedId);
                }
            }

            System.out.println("Adresa a fost salvata cu succes in baza de date cu ID-ul: " + entity.getId());
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la salvarea adresei", e);
        }
    }

    @Override
    public Optional<Address> findById(Integer id) {
        String sql = "SELECT * FROM Addresses WHERE AddressID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Address address = new Address();
                    address.setId(rs.getInt("AddressID"));
                    address.setCity(rs.getString("City"));
                    address.setStreet(rs.getString("Street"));
                    address.setNumber(rs.getString("Number"));
                    address.setDetails(rs.getString("Details"));

                    return Optional.of(address);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la gasirea adresei cu ID-ul: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Address> findAll() {
        List<Address> addresses = new ArrayList<>();
        String sql = "SELECT * FROM Addresses";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Address address = new Address();
                address.setId(rs.getInt("AddressID"));
                address.setCity(rs.getString("City"));
                address.setStreet(rs.getString("Street"));
                address.setNumber(rs.getString("Number"));
                address.setDetails(rs.getString("Details"));

                addresses.add(address);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la aducerea adreselor", e);
        }
        return addresses;
    }

    @Override
    public void update(Address entity) {
        String sql = "UPDATE Addresses SET City = ?, Street = ?, Number = ?, Details = ? WHERE AddressID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getCity());
            preparedStatement.setString(2, entity.getStreet());
            preparedStatement.setString(3, entity.getNumber());
            preparedStatement.setString(4, entity.getDetails());
            preparedStatement.setInt(5, entity.getId());

            preparedStatement.executeUpdate();
            System.out.println("Adresa a fost actualizata cu succes!");
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea adresei", e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Addresses WHERE AddressID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            System.out.println("Adresa a fost stearsa cu succes!");
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea adresei", e);
        }
    }
}