package com.fiap.stock.dao;

import com.fiap.stock.model.Consumo;
import com.fiap.stock.util.ConnectionFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConsumoDAOImpl implements ConsumoDAO {

    @Override
    public Consumo save(Consumo consumo) throws Exception {
        String sql = "INSERT INTO CONSUMO (MATERIAL_ID, QUANTIDADE, DATA_APONTAMENTO, RESPONSAVEL) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[] {"ID"})) {
            ps.setLong(1, consumo.getMaterialId());
            ps.setDouble(2, consumo.getQuantidade());
            ps.setTimestamp(3, Timestamp.valueOf(consumo.getDataApontamento()));
            ps.setString(4, consumo.getResponsavel());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) consumo.setId(Long.valueOf(rs.getLong(1)));
            }
        }
        return consumo;
    }

    @Override
    public List<Consumo> findByMaterialId(Long materialId) throws Exception {
        String sql = "SELECT ID, MATERIAL_ID, QUANTIDADE, DATA_APONTAMENTO, RESPONSAVEL FROM CONSUMO WHERE MATERIAL_ID = ? ORDER BY DATA_APONTAMENTO DESC";
        List<Consumo> list = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, materialId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Consumo c = new Consumo();
                    c.setId(Long.valueOf(rs.getLong("ID")));
                    c.setMaterialId(Long.valueOf(rs.getLong("MATERIAL_ID")));
                    c.setQuantidade(Double.valueOf(rs.getDouble("QUANTIDADE")));
                    Timestamp ts = rs.getTimestamp("DATA_APONTAMENTO");
                    if (ts != null) c.setDataApontamento(ts.toLocalDateTime());
                    c.setResponsavel(rs.getString("RESPONSAVEL"));
                    list.add(c);
                }
            }
        }
        return list;
    }

    @Override
    public List<Consumo> findByPeriod(LocalDateTime from, LocalDateTime to) throws Exception {
        String sql = "SELECT ID, MATERIAL_ID, QUANTIDADE, DATA_APONTAMENTO, RESPONSAVEL FROM CONSUMO WHERE DATA_APONTAMENTO BETWEEN ? AND ? ORDER BY DATA_APONTAMENTO DESC";
        List<Consumo> list = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(from));
            ps.setTimestamp(2, Timestamp.valueOf(to));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Consumo c = new Consumo();
                    c.setId(Long.valueOf(rs.getLong("ID")));
                    c.setMaterialId(Long.valueOf(rs.getLong("MATERIAL_ID")));
                    c.setQuantidade(Double.valueOf(rs.getDouble("QUANTIDADE")));
                    Timestamp ts = rs.getTimestamp("DATA_APONTAMENTO");
                    if (ts != null) c.setDataApontamento(ts.toLocalDateTime());
                    c.setResponsavel(rs.getString("RESPONSAVEL"));
                    list.add(c);
                }
            }
        }
        return list;
    }
}
