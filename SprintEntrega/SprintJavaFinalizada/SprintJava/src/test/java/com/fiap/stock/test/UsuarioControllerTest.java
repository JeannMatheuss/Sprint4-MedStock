package com.fiap.stock.test;

import com.fiap.stock.controller.UsuarioController;
import com.fiap.stock.model.Usuario;
import com.fiap.stock.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UsuarioService service;

    @Test
    void login_sucesso() throws Exception {
        Usuario u = new Usuario(1L, "Admin", "admin", "123", "ADMIN");
        when(service.autenticar("admin", "123")).thenReturn(u);

        mvc.perform(post("/api/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\":\"admin\",\"senha\":\"123\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Bem-vindo")));
    }

    @Test
    void login_falha() throws Exception {
        when(service.autenticar("x", "y")).thenThrow(new RuntimeException("Erro"));
        mvc.perform(post("/api/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\":\"x\",\"senha\":\"y\"}"))
                .andExpect(status().isUnauthorized());
    }
}
