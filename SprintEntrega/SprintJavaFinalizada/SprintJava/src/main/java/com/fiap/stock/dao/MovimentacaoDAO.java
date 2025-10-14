package com.fiap.stock.dao;

import com.fiap.stock.model.Movimentacao;
import com.fiap.stock.util.ConnectionFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MovimentacaoDAO {

    public void registrar(Movimentacao mov) throws SQLException {
        String sql = "INSERT INTO MOVIMENTACAO(ID_MATERIAL, ID_USUARIO, QUANTIDADE, TIPO, DATA_HORA) VALUES(?,?,?,?,?)";
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
        String sql = "SELECT * FROM MOVIMENTACAO ORDER BY DATA_HORA DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Movimentacao m = new Movimentacao(
                        rs.getLong("ID"),
                        rs.getLong("ID_MATERIAL"),
                        rs.getLong("ID_USUARIO"),
                        rs.getDouble("QUANTIDADE"),
                        rs.getString("TIPO"),
                        rs.getTimestamp("DATA_HORA").toLocalDateTime()
                );
                list.add(m);
            }
        }
        return list;
    }
}
