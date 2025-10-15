package com.fiap.stock.service;

import com.fiap.stock.dao.UsuarioDAO;
import com.fiap.stock.exception.ResourceNotFoundException;
import com.fiap.stock.model.Usuario;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioDAO dao;

    public UsuarioService(UsuarioDAO dao) {
        this.dao = dao;
    }

    // Autenticar usuário
    public Usuario autenticar(String login, String senha) throws SQLException {
        return dao.findByLoginSenha(login, senha)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    // Listar todos
    public List<Usuario> listarTodos() throws SQLException {
        return dao.findAll();
    }

    // Buscar por ID
    public Usuario buscarPorId(Long id) throws SQLException {
        return dao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    // Criar usuário
    public void criar(Usuario u) throws SQLException {
        if (u.getNome() == null || u.getNome().trim().isEmpty()) throw new IllegalArgumentException("Nome obrigatório");
        if (u.getLogin() == null || u.getLogin().trim().isEmpty()) throw new IllegalArgumentException("Login obrigatório");
        if (u.getSenha() == null || u.getSenha().trim().isEmpty()) throw new IllegalArgumentException("Senha obrigatória");
        if (u.getPerfil() == null || u.getPerfil().trim().isEmpty()) throw new IllegalArgumentException("Perfil obrigatório");
        dao.save(u);
    }

    // Atualizar usuário
    public void atualizar(Usuario u) throws SQLException {
        if (u.getId() == null) throw new IllegalArgumentException("ID obrigatório");
        dao.update(u);
    }

    // Deletar usuário
    public void deletar(Long id) throws SQLException {
        dao.delete(id);
    }
}
