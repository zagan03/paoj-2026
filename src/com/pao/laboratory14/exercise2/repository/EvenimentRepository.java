package com.pao.laboratory14.exercise2.repository;

import com.pao.laboratory14.exercise2.model.Eveniment;
import com.pao.laboratory14.exercise2.util.DatabaseConnection;
import com.pao.laboratory14.exercise1.TipBilet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvenimentRepository implements Repository<Eveniment, Integer> {
    private final Connection connection;

    public EvenimentRepository() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void initSchema() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS evenimente");
            stmt.execute("CREATE TABLE IF NOT EXISTS evenimente (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nume TEXT NOT NULL, " +
                    "data TEXT NOT NULL, " +
                    "capacitate INTEGER, " +
                    "tip TEXT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Eveniment ev) {
        String sql = "INSERT INTO evenimente (nume, data, capacitate, tip) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, ev.getNume());
            ps.setString(2, ev.getData());
            ps.setInt(3, ev.getCapacitate());
            ps.setString(4, ev.getTip().name()); // Salvam enum-ul ca String
            ps.executeUpdate();

            // Luam ID-ul generat automat de SQLite si il setam in obiect
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    ev.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Eveniment> findAll() {
        List<Eveniment> lista = new ArrayList<>();
        String sql = "SELECT * FROM evenimente ORDER BY id";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Eveniment ev = new Eveniment(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getString("data"),
                        rs.getInt("capacitate"),
                        TipBilet.valueOf(rs.getString("tip")) // Convertim String-ul inapoi in Enum
                );
                lista.add(ev);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public int deleteImpl(int id) {
        String sql = "DELETE FROM evenimente WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate(); // Returneaza cate randuri a sters
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM evenimente";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Metode din interfata ramase neimplementate pentru acest scenariu
    @Override public Eveniment findById(Integer id) { return null; }
    @Override public void update(Eveniment entity) {}
    @Override public void delete(Integer id) {}
}