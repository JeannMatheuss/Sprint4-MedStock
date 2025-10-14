package com.fiap.stock.dao;

import com.fiap.stock.model.Usuario;
import com.fiap.stock.util.ConnectionFactory;

import java.sql.*;
import java.util.Optional;

public class UsuarioDAO {

    public Optional<Usuario> findByLoginSenha(String login, String senha) throws SQLException {
        String sql = "SELECT ID, NOME, LOGIN, SENHA FROM USUARIO WHERE LOGIN=? AND SENHA=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setString(2, senha);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Usuario(
                            rs.getLong("ID"),
                            rs.getString("NOME"),
                            rs.getString("LOGIN"),
                            rs.getString("SENHA")
                    ));
                }
            }
        }
        return Optional.empty();
    }
}
