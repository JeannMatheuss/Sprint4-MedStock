package com.fiap.stock.service;

import com.fiap.stock.dao.UsuarioDAO;
import com.fiap.stock.exception.ResourceNotFoundException;
import com.fiap.stock.model.Usuario;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UsuarioService {
    private final UsuarioDAO dao = new UsuarioDAO();

    public Usuario autenticar(String login, String senha) throws SQLException {
        return dao.findByLoginSenha(login, senha)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
}
