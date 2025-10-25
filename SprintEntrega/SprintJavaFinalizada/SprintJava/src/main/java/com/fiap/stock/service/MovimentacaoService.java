package com.fiap.stock.service;

import com.fiap.stock.dao.MovimentacaoDAO;
import com.fiap.stock.model.Movimentacao;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class MovimentacaoService {

    private final MovimentacaoDAO dao;

    public MovimentacaoService(MovimentacaoDAO dao) {
        this.dao = dao;
    }

    public void registrar(Movimentacao mov) throws SQLException {
        if (mov.getQuantidade() == null || mov.getQuantidade() <= 0)
            throw new IllegalArgumentException("Quantidade inválida");
        if (mov.getTipo() == null || mov.getTipo().isBlank())
            throw new IllegalArgumentException("Tipo obrigatório");
        dao.registrar(mov);
    }

    public List<Movimentacao> listar() throws SQLException {
        return dao.listar();
    }
}
