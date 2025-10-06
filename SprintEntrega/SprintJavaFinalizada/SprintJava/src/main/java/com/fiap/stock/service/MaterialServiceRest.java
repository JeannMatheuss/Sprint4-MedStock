package com.fiap.stock.service;


import com.fiap.stock.dao.MaterialDAOImpl;
import com.fiap.stock.dao.MovimentacaoDAO;
import com.fiap.stock.dao.UsuarioDAO;
import com.fiap.stock.model.Material;
import com.fiap.stock.model.Movimentacao;
import com.fiap.stock.model.Usuario;
import com.fiap.stock.util.ConnectionFactory;
import com.fiap.stock.exception.ResourceNotFoundException;


import java.sql.Connection;
import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MaterialServiceRest {


    public List<Material> listarTodos() throws Exception {
        try (Connection conn = ConnectionFactory.getConnection()) {
            MaterialDAOImpl dao = new MaterialDAOImpl(conn);
            return dao.findAll();
        }
    }


    public Material criar(String nome, Double qtd, String unidade, Double ponto) throws Exception {
        if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome é obrigatório");
        if (qtd == null || qtd < 0) throw new IllegalArgumentException("Quantidade inválida");
        try (Connection conn = ConnectionFactory.getConnection()) {
            MaterialDAOImpl dao = new MaterialDAOImpl(conn);
            Material m = new Material(nome, qtd, unidade, ponto);
            return dao.save(m);
        }
    }


    public Material buscarPorId(Long id) throws Exception {
        try (Connection conn = ConnectionFactory.getConnection()) {
            MaterialDAOImpl dao = new MaterialDAOImpl(conn);
            return dao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Material não encontrado"));
        }
    }


    public void deletar(Long id) throws Exception {
        try (Connection conn = ConnectionFactory.getConnection()) {
            MaterialDAOImpl dao = new MaterialDAOImpl(conn);
            dao.delete(id);
        }
    }


    public void atualizar(Material material) throws Exception {
        try (Connection conn = ConnectionFactory.getConnection()) {
            MaterialDAOImpl dao = new MaterialDAOImpl(conn);
            dao.update(material);
        }
    }


    @Transactional
    public void movimentar(Long materialId, Integer usuarioId, String tipo, Integer quantidade) throws Exception {
        try (Connection conn = ConnectionFactory.getConnection()) {
            MaterialDAOImpl materialDAO = new MaterialDAOImpl(conn);
            MovimentacaoDAO movDAO = new MovimentacaoDAO(conn);
            UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
        }