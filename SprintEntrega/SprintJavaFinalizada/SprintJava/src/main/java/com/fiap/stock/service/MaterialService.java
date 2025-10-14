package com.fiap.stock.service;

import com.fiap.stock.dao.MaterialDAOImpl;
import com.fiap.stock.exception.ResourceNotFoundException;
import com.fiap.stock.model.Material;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class MaterialService {

    private final MaterialDAOImpl dao;

    public MaterialService(MaterialDAOImpl dao) {
        this.dao = dao;
    }

    public List<Material> listarTodos() throws SQLException {
        return dao.findAll();
    }

    public Material criar(Material m) throws SQLException {
        if (m.getNome() == null || m.getNome().trim().isEmpty()) throw new IllegalArgumentException("Nome obrigatório");
        if (m.getQuantidade() == null || m.getQuantidade() < 0) throw new IllegalArgumentException("Quantidade inválida");
        return dao.save(m);
    }

    public Material buscarPorId(Long id) throws SQLException {
        return dao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Material não encontrado"));
    }

    public void deletar(Long id) throws SQLException {
        if (!dao.findById(id).isPresent()) throw new ResourceNotFoundException("Material não encontrado");
        dao.delete(id);
    }

    public void atualizar(Material m) throws SQLException {
        if (m.getId() == null) throw new IllegalArgumentException("ID obrigatório");
        dao.update(m);
    }
}
