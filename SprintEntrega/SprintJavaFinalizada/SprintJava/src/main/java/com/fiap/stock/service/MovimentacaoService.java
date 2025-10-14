package com.fiap.stock.service;

import com.fiap.stock.dao.MovimentacaoDAO;
import com.fiap.stock.model.Movimentacao;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class MovimentacaoService {
    private final MovimentacaoDAO dao = new MovimentacaoDAO();

    public void registrarMovimentacao(Movimentacao mov) throws SQLException {
        dao.registrar(mov);
    }

    public List<Movimentacao> listar() throws SQLException {
        return dao.listar();
    }

}
