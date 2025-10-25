package com.fiap.stock.dao;

import com.fiap.stock.model.Movimentacao;
import com.fiap.stock.util.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MovimentacaoDAO {

    public void registrar(Movimentacao mov) throws SQLException {
        String sql = "INSERT INTO MOVIMENTACAO (MATERIAL_ID, USUARIO_ID, QUANTIDADE, TIPO, DATA_MOVIMENTACAO) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, mov.getIdMaterial());
            ps.setLong(2, mov.getIdUsuario());
            ps.setDouble(3, mov.getQuantidade());
            ps.setString(4, mov.getTipo());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();
        }
    }

    public List<Movimentacao> listar() throws SQLException {
        List<Movimentacao> list = new ArrayList<>();
        String sql = "SELECT * FROM MOVIMENTACAO ORDER BY DATA_MOVIMENTACAO DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Movimentacao(
                        rs.getLong("ID"),
                        rs.getLong("MATERIAL_ID"),
                        rs.getLong("USUARIO_ID"),
                        rs.getDouble("QUANTIDADE"),
                        rs.getString("TIPO"),
                        rs.getTimestamp("DATA_MOVIMENTACAO").toLocalDateTime()
                ));
            }
        }
        return list;
    }
}
