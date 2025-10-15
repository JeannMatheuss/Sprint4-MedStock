package com.fiap.stock.dao;

import com.fiap.stock.model.Usuario;
import com.fiap.stock.util.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioDAO {

    // Autenticação por login e senha
    public Optional<Usuario> findByLoginSenha(String login, String senha) throws SQLException {
        String sql = "SELECT ID, NOME, LOGIN, SENHA, PERFIL FROM USUARIO WHERE LOGIN=? AND SENHA=?";
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
                            rs.getString("SENHA"),
                            rs.getString("PERFIL")
                    ));
                }
            }
        }
        return Optional.empty();
    }

    // Listar todos usuários
    public List<Usuario> findAll() throws SQLException {
        List<Usuario> list = new ArrayList<>();
        String sql = "SELECT ID, NOME, LOGIN, SENHA, PERFIL FROM USUARIO";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Usuario(
                        rs.getLong("ID"),
                        rs.getString("NOME"),
                        rs.getString("LOGIN"),
                        rs.getString("SENHA"),
                        rs.getString("PERFIL")
                ));
            }
        }
        return list;
    }

    // Buscar por ID
    public Optional<Usuario> findById(Long id) throws SQLException {
        String sql = "SELECT ID, NOME, LOGIN, SENHA, PERFIL FROM USUARIO WHERE ID=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Usuario(
                            rs.getLong("ID"),
                            rs.getString("NOME"),
                            rs.getString("LOGIN"),
                            rs.getString("SENHA"),
                            rs.getString("PERFIL")
                    ));
                }
            }
        }
        return Optional.empty();
    }

    // Criar usuário
    public void save(Usuario u) throws SQLException {
        String sql = "INSERT INTO USUARIO (NOME, LOGIN, SENHA, PERFIL) VALUES (?,?,?,?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, u.getNome());
            ps.setString(2, u.getLogin());
            ps.setString(3, u.getSenha());
            ps.setString(4, u.getPerfil());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) u.setId(rs.getLong(1));
            }
        }
    }

    // Atualizar usuário
    public void update(Usuario u) throws SQLException {
        String sql = "UPDATE USUARIO SET NOME=?, LOGIN=?, SENHA=?, PERFIL=? WHERE ID=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getNome());
            ps.setString(2, u.getLogin());
            ps.setString(3, u.getSenha());
            ps.setString(4, u.getPerfil());
            ps.setLong(5, u.getId());
            ps.executeUpdate();
        }
    }

    // Deletar usuário
    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM USUARIO WHERE ID=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}
