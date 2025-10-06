package com.fiap.stock.dao;

import com.fiap.stock.model.Material;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MaterialDAOImpl implements MaterialDAO {

    private final Connection conn;

    public MaterialDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Material save(Material material) throws Exception {
        String sql = "INSERT INTO MATERIAL (NOME, QUANTIDADE, UNIDADE, PONTO_REPOSICAO) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID"})) {
            stmt.setString(1, material.getNome());
            stmt.setDouble(2, material.getQuantidade());       // <- número
            stmt.setString(3, material.getUnidade());
            stmt.setDouble(4, material.getPontoReposicao());   // <- número
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    material.setId(rs.getLong(1));
                }
            }
        }
        return material;
    }

    @Override
    public Optional<Material> findById(Long id) throws Exception {
        String sql = "SELECT * FROM MATERIAL WHERE ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Material m = new Material(
                            rs.getLong("ID"),
                            rs.getString("NOME"),
                            rs.getDouble("QUANTIDADE"),
                            rs.getString("UNIDADE"),
                            rs.getDouble("PONTO_REPOSICAO")
                    );
                    return Optional.of(m);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Material> findAll() throws Exception {
        List<Material> list = new ArrayList<>();
        String sql = "SELECT * FROM MATERIAL ORDER BY ID";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

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

    @Override
    public void update(Material material) throws Exception {
        String sql = "UPDATE MATERIAL SET NOME = ?, QUANTIDADE = ?, UNIDADE = ?, PONTO_REPOSICAO = ? WHERE ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, material.getNome());
            stmt.setDouble(2, material.getQuantidade());
            stmt.setString(3, material.getUnidade());
            stmt.setDouble(4, material.getPontoReposicao());
            stmt.setLong(5, material.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        String sql = "DELETE FROM MATERIAL WHERE ID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
