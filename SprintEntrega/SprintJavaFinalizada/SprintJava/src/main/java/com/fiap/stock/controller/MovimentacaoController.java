package com.fiap.stock.controller;

import com.fiap.stock.dto.MovimentacaoDTO;
import com.fiap.stock.service.MaterialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/movimentacoes")
public class MovimentacaoController {

    private final MaterialService service;

    public MovimentacaoController(MaterialService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> movimentar(@RequestBody @Valid MovimentacaoDTO dto) throws Exception {
        service.movimentar(dto.getMaterialId(), dto.getUsuarioId(), dto.getTipo(), dto.getQuantidade());
        return ResponseEntity.ok().build();
    }
}
