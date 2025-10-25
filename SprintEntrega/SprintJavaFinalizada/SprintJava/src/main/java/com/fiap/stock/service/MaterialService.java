package com.fiap.stock.service;

import com.fiap.stock.dao.MaterialDAO;
import com.fiap.stock.exception.ResourceNotFoundException;
import com.fiap.stock.model.Material;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class MaterialService {

    private final MaterialDAO dao;

    public MaterialService(MaterialDAO dao) {
        this.dao = dao;
    }

    public List<Material> listarTodos() throws SQLException {
        return dao.findAll();
    }

    public Material buscarPorId(Long id) throws SQLException {
        return dao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material não encontrado"));
    }

    public Material criar(Material m) throws SQLException {
        if (m.getNome() == null || m.getNome().trim().isEmpty())
            throw new IllegalArgumentException("Nome obrigatório");
        dao.save(m);
        return m;
    }

    public Material atualizar(Material m) throws SQLException {
        if (m.getId() == null) throw new IllegalArgumentException("ID obrigatório");
        dao.update(m);
        return m;
    }

    public void deletar(Long id) throws SQLException {
        if (dao.findById(id).isEmpty())
            throw new ResourceNotFoundException("Material não encontrado");
        dao.delete(id);
    }
}
