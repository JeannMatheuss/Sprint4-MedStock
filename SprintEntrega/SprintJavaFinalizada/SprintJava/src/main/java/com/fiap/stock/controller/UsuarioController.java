package com.fiap.stock.controller;

import com.fiap.stock.model.Usuario;
import com.fiap.stock.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public Usuario autenticar(@RequestBody Usuario usuario) throws SQLException {
        return service.autenticar(usuario.getLogin(), usuario.getSenha());
    }
}
