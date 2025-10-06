package com.fiap.stock.dao;

import com.fiap.stock.model.Usuario;

import java.sql.*;

public class UsuarioDAO {

    private final Connection conn;

    public UsuarioDAO(Connection conn) {
        this.conn = conn;
    }

    // Salvar usuário
    public void save(Usuario u) throws SQLException {
        String sql = "INSERT INTO USUARIO(NOME, LOGIN, SENHA, PERFIL) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getLogin());
            ps.setString(3, u.getSenha());
            ps.setString(4, u.getPerfil());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    u.setId(rs.getInt(1));
                }
            }
        }
    }

    // Buscar por login e senha (autenticação)
    public Usuario findByLoginSenha(String login, String senha) {
        String sql = "SELECT * FROM USUARIO WHERE LOGIN = ? AND SENHA = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setString(2, senha);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapUsuario(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Buscar por login (verificação duplicidade)
    public Usuario findByLogin(String login) {
        String sql = "SELECT * FROM USUARIO WHERE LOGIN = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapUsuario(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Usuario mapUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("ID"));
        u.setNome(rs.getString("NOME"));
        u.setLogin(rs.getString("LOGIN"));
        u.setSenha(rs.getString("SENHA"));
        u.setPerfil(rs.getString("PERFIL"));
        return u;
    }
}
