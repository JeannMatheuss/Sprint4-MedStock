package com.fiap.stock.controller;

import com.fiap.stock.model.Usuario;
import com.fiap.stock.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<String> autenticar(@RequestBody Usuario usuario) {
        try {
            Usuario u = service.autenticar(usuario.getLogin(), usuario.getSenha());
            return ResponseEntity.ok("Login realizado com sucesso. Bem-vindo, " + u.getNome() + "!");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Login inválido: usuário ou senha incorretos.");
        }
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        try {
            List<Usuario> usuarios = service.listarTodos();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        try {
            Usuario u = service.buscarPorId(id);
            return ResponseEntity.ok(u);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }
    }

    // Criar usuário
    @PostMapping
    public ResponseEntity<String> criar(@RequestBody Usuario usuario) {
        try {
            service.criar(usuario);
            return ResponseEntity.status(201).body("Usuário criado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno ao criar usuário.");
        }
    }

    // Atualizar usuário
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            usuario.setId(id);
            service.atualizar(usuario);
            return ResponseEntity.ok("Usuário atualizado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }
    }

    // Deletar usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        try {
            service.deletar(id);
            return ResponseEntity.ok("Usuário excluído com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }
    }
}
