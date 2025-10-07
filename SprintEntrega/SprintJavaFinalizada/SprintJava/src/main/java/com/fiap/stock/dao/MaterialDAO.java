package com.fiap.stock.dao;

import com.fiap.stock.model.Material;

import java.util.List;
import java.util.Optional;

public interface MaterialDAO {
    Material save(Material material) throws Exception;
    Optional<Material> findById(Long id) throws Exception;
    List<Material> findAll() throws Exception;
    void update(Material material) throws Exception;
    void delete(Long id) throws Exception;
}
