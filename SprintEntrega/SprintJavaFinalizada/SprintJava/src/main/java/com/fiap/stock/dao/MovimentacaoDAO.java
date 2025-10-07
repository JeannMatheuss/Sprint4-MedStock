package com.fiap.stock.dao;

import com.fiap.stock.model.Movimentacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

public class MovimentacaoDAO {

    private final Connection conn;

    // ✅ Mantém o construtor com Connection (usado pelo EstoqueService)
    public MovimentacaoDAO(Connection conn) {
        this.conn = conn;
    }

    // Registrar movimentação (entrada/saída) e retornar id gerado
    public Integer registrarMovimentacao(Movimentacao mov) throws Exception {
        String sql = "INSERT INTO MOVIMENTACAO (MATERIAL_ID, USUARIO_ID, TIPO, QUANTIDADE, DATA_MOVIMENTACAO) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, mov.getMaterialId());
            ps.setInt(2, mov.getUsuarioId());
            ps.setString(3, mov.getTipo());
            ps.setInt(4, mov.getQuantidade());
            ps.setTimestamp(5, Timestamp.valueOf(java.time.LocalDateTime.now()));
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return null;
    }
}
