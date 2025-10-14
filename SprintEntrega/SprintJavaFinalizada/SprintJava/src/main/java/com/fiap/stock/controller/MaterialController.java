package com.fiap.stock.controller;

import com.fiap.stock.model.Material;
import com.fiap.stock.service.MaterialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/materiais")
public class MaterialController {

    private final MaterialService service;

    public MaterialController(MaterialService service) {
        this.service = service;
    }

    @GetMapping
    public List<Material> listarTodos() throws SQLException {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Material> buscar(@PathVariable Long id) throws SQLException {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public Material criar(@RequestBody Material m) throws SQLException {
        return service.criar(m);
    }

    @PutMapping("/{id}")
    public Material atualizar(@PathVariable Long id, @RequestBody Material m) throws SQLException {
        m.setId(id);
        service.atualizar(m);
        return m;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws SQLException {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
