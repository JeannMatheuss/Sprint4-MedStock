package com.fiap.stock.controller;

import com.fiap.stock.exception.ResourceNotFoundException;
import com.fiap.stock.model.Usuario;
import com.fiap.stock.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<String> autenticar(@RequestBody Usuario usuario) {
        try {
            Usuario u = service.autenticar(usuario.getLogin(), usuario.getSenha());
            // Login válido → retorna mensagem de sucesso
            return ResponseEntity.ok("Login realizado com sucesso. Bem-vindo, " + u.getNome() + "!");
        } catch (ResourceNotFoundException e) {
            // Login inválido → retorna mensagem de erro
            return ResponseEntity.status(401).body("Login inválido: usuário ou senha incorretos.");
        } catch (Exception e) {
            // Erro genérico
            return ResponseEntity.status(500).body("Erro interno. Tente novamente mais tarde.");
        }
    }
}
