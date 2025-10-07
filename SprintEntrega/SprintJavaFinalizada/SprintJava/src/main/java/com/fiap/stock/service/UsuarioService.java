package com.fiap.stock.service;

import com.fiap.stock.dao.UsuarioDAO;
import com.fiap.stock.model.Usuario;
import org.springframework.stereotype.Service;

import java.sql.Connection;

@Service
public class UsuarioService {
    private Connection conn;
    private final UsuarioDAO usuarioDAO = new UsuarioDAO(conn);

    public Usuario autenticar(String login, String senha) {
        // Em produção: verificar senha com hash (BCrypt)
        return usuarioDAO.findByLoginSenha(login, senha);
    }

    public Usuario buscarPorId(Integer id) {
        return usuarioDAO.findById(id);
    }
}
