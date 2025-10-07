package com.fiap.stock.service;

import com.fiap.stock.dao.MaterialDAOImpl;
import com.fiap.stock.dao.MovimentacaoDAO;
import com.fiap.stock.dao.UsuarioDAO;
import com.fiap.stock.exception.ResourceNotFoundException;
import com.fiap.stock.model.Material;
import com.fiap.stock.model.Movimentacao;
import com.fiap.stock.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@Service
public class MaterialService {

    private final MaterialDAOImpl materialDAO = new MaterialDAOImpl();
    private Connection conn;
    private final MovimentacaoDAO movDAO = new MovimentacaoDAO(conn);
    private final UsuarioDAO usuarioDAO = new UsuarioDAO(conn);

    public List<Material> listarTodos() throws Exception {
        return materialDAO.findAll();
    }

    public Material criar(Material m) throws Exception {
        // validações
        if (m.getNome() == null || m.getNome().trim().isEmpty())
            throw new IllegalArgumentException("Nome é obrigatório");
        if (m.getQuantidade() == null || m.getQuantidade() < 0)
            throw new IllegalArgumentException("Quantidade inválida");
        if (m.getPontoReposicao() == null || m.getPontoReposicao() < 0)
            throw new IllegalArgumentException("Ponto de reposição inválido");
        return materialDAO.save(m);
    }

    public Material buscarPorId(Long id) throws Exception {
        Optional<Material> opt = materialDAO.findById(id);
        return opt.orElseThrow(() -> new ResourceNotFoundException("Material não encontrado"));
    }

    public void deletar(Long id) throws Exception {
        // verificação se existe
        Optional<Material> opt = materialDAO.findById(id);
        if (!opt.isPresent()) throw new ResourceNotFoundException("Material não encontrado");
        materialDAO.delete(id);
    }

    public void atualizar(Material m) throws Exception {
        if (m.getId() == null) throw new IllegalArgumentException("ID é obrigatório para atualização");
        materialDAO.update(m);
    }

    @Transactional
    public void movimentar(Long materialId, Integer usuarioId, String tipo, Integer quantidade) throws Exception {
        Material m = buscarPorId(materialId);
        Usuario u = usuarioDAO.findById(usuarioId);
        if (u == null) throw new ResourceNotFoundException("Usuário não encontrado");

        double atual = m.getQuantidade() != null ? m.getQuantidade() : 0.0;
        if ("ENTRADA".equalsIgnoreCase(tipo)) {
            m.setQuantidade(atual + quantidade);
        } else if ("SAIDA".equalsIgnoreCase(tipo)) {
            if (quantidade > atual) throw new IllegalArgumentException("Quantidade insuficiente");
            m.setQuantidade(atual - quantidade);
        } else {
            throw new IllegalArgumentException("Tipo inválido: use ENTRADA ou SAIDA");
        }
        materialDAO.update(m);

        Movimentacao mov = new Movimentacao();
        mov.setMaterialId(m.getId().intValue());
        mov.setUsuarioId(usuarioId);
        mov.setTipo(tipo.toUpperCase());
        mov.setQuantidade(quantidade);
        movDAO.registrarMovimentacao(mov);
    }
}
