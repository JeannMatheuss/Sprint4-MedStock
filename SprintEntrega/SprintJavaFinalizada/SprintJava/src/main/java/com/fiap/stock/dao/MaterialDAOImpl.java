package com.fiap.stock.dao;

import com.fiap.stock.model.Material;
import com.fiap.stock.util.ConnectionFactory;
import com.fiap.stock.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MaterialDAOImpl {

    public List<Material> findAll() throws SQLException {
        List<Material> list = new ArrayList<>();
        String sql = "SELECT ID, NOME, QUANTIDADE, UNIDADE, PONTO_REPOSICAO FROM MATERIAL";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Material m = new Material(
                        rs.getLong("ID"),
                        rs.getString("NOME"),
                        rs.getDouble("QUANTIDADE"),
                        rs.getString("UNIDADE"),
                        rs.getDouble("PONTO_REPOSICAO")
                );
                list.add(m);
            }
        }
        return list;
    }

    public Material save(Material m) throws SQLException {
        String sql = "INSERT INTO MATERIAL(NOME, QUANTIDADE, UNIDADE, PONTO_REPOSICAO) VALUES(?,?,?,?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.getNome());
            ps.setDouble(2, m.getQuantidade());
            ps.setString(3, m.getUnidade());
            ps.setDouble(4, m.getPontoReposicao());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) m.setId(rs.getLong(1));
            }
        }
        return m;
    }

    public Optional<Material> findById(Long id) throws SQLException {
        String sql = "SELECT ID, NOME, QUANTIDADE, UNIDADE, PONTO_REPOSICAO FROM MATERIAL WHERE ID=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Material(
                            rs.getLong("ID"),
                            rs.getString("NOME"),
                            rs.getDouble("QUANTIDADE"),
                            rs.getString("UNIDADE"),
                            rs.getDouble("PONTO_REPOSICAO")
                    ));
                }
            }
        }
        return Optional.empty();
    }

    public void update(Material m) throws SQLException {
        String sql = "UPDATE MATERIAL SET NOME=?, QUANTIDADE=?, UNIDADE=?, PONTO_REPOSICAO=? WHERE ID=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getNome());
            ps.setDouble(2, m.getQuantidade());
            ps.setString(3, m.getUnidade());
            ps.setDouble(4, m.getPontoReposicao());
            ps.setLong(5, m.getId());
            ps.executeUpdate();
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM MATERIAL WHERE ID=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}
