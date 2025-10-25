package com.fiap.stock.test;

import com.fiap.stock.dao.UsuarioDAO;
import com.fiap.stock.exception.ResourceNotFoundException;
import com.fiap.stock.model.Usuario;
import com.fiap.stock.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    private final UsuarioDAO dao = mock(UsuarioDAO.class);
    private final UsuarioService service = new UsuarioService(dao);

    @Test
    void autenticar_sucesso() throws SQLException {
        Usuario u = new Usuario(1L,"Admin","admin","123","ADMIN");
        when(dao.findByLoginSenha("admin","123")).thenReturn(Optional.of(u));
        Usuario result = service.autenticar("admin","123");
        assertEquals("Admin", result.getNome());
        verify(dao, times(1)).findByLoginSenha("admin","123");
    }

    @Test
    void autenticar_naoEncontrado() throws SQLException {
        when(dao.findByLoginSenha("x","y")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.autenticar("x","y"));
    }
}
