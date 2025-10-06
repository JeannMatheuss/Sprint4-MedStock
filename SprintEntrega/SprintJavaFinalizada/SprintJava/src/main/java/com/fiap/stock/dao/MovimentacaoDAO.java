package com.fiap.stock.dao;

import com.fiap.stock.model.Movimentacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class MovimentacaoDAO {

    private final Connection conn;

    public MovimentacaoDAO(Connection conn) {
        this.conn = conn;
    }

    // Registrar movimentação (entrada/saída)
    public void registrarMovimentacao(Movimentacao mov) throws SQLException {
        String sql = "INSERT INTO MOVIMENTACAO(MATERIAL_ID, USUARIO_ID, TIPO, QUANTIDADE, DATA_MOVIMENTACAO) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, mov.getMaterialId());
            ps.setInt(2, mov.getUsuarioId());
            ps.setString(3, mov.getTipo());
            ps.setInt(4, mov.getQuantidade());
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();
        }
    }
}
