package com.fiap.stock.service;

import com.fiap.stock.dao.MaterialDAOImpl;
import com.fiap.stock.dao.MovimentacaoDAO;
import com.fiap.stock.dao.UsuarioDAO;
import com.fiap.stock.model.Material;
import com.fiap.stock.model.Movimentacao;
import com.fiap.stock.model.Usuario;

import java.sql.Connection;
import java.util.List;

public class EstoqueService {

    private final MaterialDAOImpl materialDAO;
    private final MovimentacaoDAO movimentacaoDAO;
    private final UsuarioDAO usuarioDAO;

    public EstoqueService(Connection conn) {
        this.materialDAO = new MaterialDAOImpl();
        this.movimentacaoDAO = new MovimentacaoDAO(conn);
        this.usuarioDAO = new UsuarioDAO(conn);
    }

    public List<Material> listarTodosMateriais() throws Exception {
        return materialDAO.findAll();
    }

    public Material criarMaterial(String nome, double quantidade, String unidade, double pontoReposicao) throws Exception {
        Material m = new Material(nome, quantidade, unidade, pontoReposicao);
        return materialDAO.save(m);
    }

    public void registrarEntrada(Material m, int quantidade, Usuario u) throws Exception {
        double atual = m.getQuantidade() != null ? m.getQuantidade() : 0;
        m.setQuantidade(atual + quantidade);
        materialDAO.update(m);

        Movimentacao mov = new Movimentacao();
        mov.setMaterialId(m.getId().intValue());
        mov.setUsuarioId(u.getId());
        mov.setTipo("ENTRADA");
        mov.setQuantidade(quantidade);
        movimentacaoDAO.registrarMovimentacao(mov);
    }

    public void registrarSaida(Material m, int quantidade, Usuario u) throws Exception {
        double atual = m.getQuantidade() != null ? m.getQuantidade() : 0;
        if (quantidade > atual) throw new IllegalArgumentException("Quantidade insuficiente!");
        m.setQuantidade(atual - quantidade);
        materialDAO.update(m);

        Movimentacao mov = new Movimentacao();
        mov.setMaterialId(m.getId().intValue());
        mov.setUsuarioId(u.getId());
        mov.setTipo("SAIDA");
        mov.setQuantidade(quantidade);
        movimentacaoDAO.registrarMovimentacao(mov);
    }

    public void removerMaterial(Material m) throws Exception {
        if (m.getId() == null) {
            throw new IllegalArgumentException("Material não possui ID válido.");
        }
        materialDAO.delete(m.getId());
    }


    public Usuario autenticar(String login, String senha) {
        return usuarioDAO.findByLoginSenha(login, senha);
    }
}
