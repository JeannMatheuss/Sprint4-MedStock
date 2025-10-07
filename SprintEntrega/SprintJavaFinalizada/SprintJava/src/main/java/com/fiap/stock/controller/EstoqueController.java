package com.fiap.stock.controller;

import com.fiap.stock.model.Material;
import com.fiap.stock.model.Usuario;
import com.fiap.stock.service.EstoqueService; // sua service antiga (se for manter)
import java.util.List;

public class EstoqueController {
    private final EstoqueService service;

    public EstoqueController(EstoqueService service) {
        this.service = service;
    }

    public List<Material> listarMateriais() throws Exception {
        return service.listarTodosMateriais();
    }

    public void registrarEntrada(Material m, int qtd, Usuario u) throws Exception {
        service.registrarEntrada(m, qtd, u);
    }

    public void registrarSaida(Material m, int qtd, Usuario u) throws Exception {
        service.registrarSaida(m, qtd, u);
    }

    // usado no LoginFrame
    public Usuario autenticar(String login, String senha) {
        try {
            return service.autenticar(login, senha);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // usado no MainFrame - CORRIGIDO: retorna o material criado
    public Material adicionarMaterial(String nome, double qtd, String unidade, double ponto) {
        try {
            return service.criarMaterial(nome, qtd, unidade, ponto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao criar material: " + e.getMessage(), e);
        }
    }

    // usado no MainFrame
    public void removerMaterial(Material m) {
        try {
            service.removerMaterial(m);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover material: " + e.getMessage(), e);
        }
    }

}
