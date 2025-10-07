package com.fiap.stock.controller;

import com.fiap.stock.dto.MaterialCreateDTO;
import com.fiap.stock.model.Material;
import com.fiap.stock.service.MaterialService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/materials")
@Validated
public class MaterialController {

    private final MaterialService service;

    public MaterialController(MaterialService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Material>> listar() throws Exception {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Material> buscar(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Material> criar(@RequestBody @Valid MaterialCreateDTO dto) throws Exception {
        Material m = new Material(dto.getNome(), dto.getQuantidade(), dto.getUnidade(), dto.getPontoReposicao());
        Material criado = service.criar(m);
        return ResponseEntity.created(URI.create("/api/materials/" + criado.getId())).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody @Valid MaterialCreateDTO dto) throws Exception {
        Material m = new Material(id, dto.getNome(), dto.getQuantidade(), dto.getUnidade(), dto.getPontoReposicao());
        service.atualizar(m);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) throws Exception {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
