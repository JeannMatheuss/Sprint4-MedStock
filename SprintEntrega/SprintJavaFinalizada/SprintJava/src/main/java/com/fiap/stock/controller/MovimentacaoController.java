package com.fiap.stock.controller;

import com.fiap.stock.model.Movimentacao;
import com.fiap.stock.service.MovimentacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/movimentacoes")
public class MovimentacaoController {

    private final MovimentacaoService service;

    public MovimentacaoController(MovimentacaoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Movimentacao>> listar() throws SQLException {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<String> registrar(@RequestBody Movimentacao mov) throws SQLException {
        service.registrar(mov);
        return ResponseEntity.ok("Movimentação registrada com sucesso!");
    }
}
