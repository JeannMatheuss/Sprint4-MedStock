package com.fiap.stock.controller;

import com.fiap.stock.model.Movimentacao;
import com.fiap.stock.service.MovimentacaoService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/movimentacao")
public class MovimentacaoController {

    private final MovimentacaoService service;

    public MovimentacaoController(MovimentacaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Movimentacao> listar() throws SQLException {
        return service.listar();
    }

    @PostMapping
    public void registrar(@RequestBody Movimentacao mov) throws SQLException {
        service.registrarMovimentacao(mov);
    }
}
