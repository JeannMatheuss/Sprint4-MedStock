package com.fiap.stock.test;

import com.fiap.stock.dao.MaterialDAO;
import com.fiap.stock.exception.ResourceNotFoundException;
import com.fiap.stock.model.Material;
import com.fiap.stock.service.MaterialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MaterialServiceTest {

    private MaterialDAO dao;
    private MaterialService service;

    @BeforeEach
    void setup() {
        dao = mock(MaterialDAO.class);
        service = new MaterialService(dao);
    }

    @Test
    void criar_valido() throws SQLException {
        Material m = new Material(null, "Soro", 10.0, "ml", 5.0);
        doAnswer(inv -> { m.setId(1L); return null; }).when(dao).save(any(Material.class));

        Material result = service.criar(m);

        assertNotNull(result.getId());
        verify(dao, times(1)).save(any(Material.class));
    }

    @Test
    void buscarNaoEncontrado() throws SQLException {
        when(dao.findById(999L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.buscarPorId(999L));
    }
}
