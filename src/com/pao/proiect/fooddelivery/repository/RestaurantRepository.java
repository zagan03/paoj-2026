package com.pao.proiect.fooddelivery.repository;
import com.pao.proiect.fooddelivery.model.Restaurant;
import com.pao.proiect.fooddelivery.model.Address;
import com.pao.proiect.fooddelivery.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RestaurantRepository implements Repository<Restaurant, Integer> {
    private final Connection connection;
    public RestaurantRepository() {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        this.connection = databaseConnection.getConnection();
    }
    @Override
    public void save(Restaurant entity) {
        String sql = "INSERT INTO Restaurants (Name, Rating, IsOpen, AddressID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDouble(2, entity.getRating());
            preparedStatement.setBoolean(3, entity.getOpen());
            preparedStatement.setInt(4, entity.getAddress().getId());
            preparedStatement.executeUpdate();
            System.out.println("Restaurantul a fost salvat cu succes in baza de date!");
        }
        catch (SQLException e) {
            throw new RuntimeException("Eroare la salvarea restaurantului", e);
        }

    }

    @Override
    public Optional<Restaurant> findById(Integer id) {
        String sql = "SELECT * FROM Restaurants WHERE RestaurantID = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Address dummyAddress = new Address();
                    dummyAddress.setId(rs.getInt("AddressID"));
                    Restaurant rest = new Restaurant(rs.getString("Name"), dummyAddress);
                    rest.setId(rs.getInt("RestaurantID"));
                    rest.setRating(rs.getDouble("Rating"));

                    if (rs.getBoolean("IsOpen")) rest.openRestaurant();
                    else rest.closeRestaurant();

                    return Optional.of(rest);
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException("Eroare la gasirea restaurantului cu ID-ul: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Restaurant> findAll() {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM Restaurants";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            // Cat timp mai avem randuri de citit din tabelul din baza de date
            while (rs.next()) {
                Address dummyAddress = new Address();
                dummyAddress.setId(rs.getInt("AddressID"));

                Restaurant rest = new Restaurant(rs.getString("Name"), dummyAddress);

                rest.setId(rs.getInt("RestaurantID"));
                rest.setRating(rs.getDouble("Rating"));

                if (rs.getBoolean("IsOpen")) {
                    rest.openRestaurant();
                } else {
                    rest.closeRestaurant();
                }
                restaurants.add(rest);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Eroare la aducerea restaurantelor", e);
        }
        return restaurants;
        }

    @Override
    public void update(Restaurant entity) {
        String sql = "UPDATE Restaurants SET Name = ?, Rating = ?, IsOpen = ?, AddressID = ? WHERE RestaurantID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Setam noile valori (primele 4 semne de intrebare)
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setDouble(2, entity.getRating());
            preparedStatement.setBoolean(3, entity.getOpen());
            preparedStatement.setInt(4, entity.getAddress().getId());

            // setam ID-ul restaurantului pe care vrem sa il modificam (al 5-lea semn)
            preparedStatement.setInt(5, entity.getId());

            preparedStatement.executeUpdate();
            System.out.println("Restaurantul a fost actualizat cu succes!");

        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea restaurantului", e);
        }
    }



    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM Restaurants WHERE RestaurantID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Punem ID-ul in locul semnului de intrebare
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            System.out.println("Restaurantul a fost sters cu succes!");

        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea restaurantului", e);
        }
    }
}
