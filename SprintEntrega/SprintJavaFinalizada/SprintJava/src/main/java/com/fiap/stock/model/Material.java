package com.fiap.stock.model;

public class Material {
    private Long id;
    private String nome;
    private Double quantidade;
    private String unidade;
    private Double pontoReposicao;

    public Material() {}

    public Material(Long id, String nome, Double quantidade, String unidade, Double pontoReposicao) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.pontoReposicao = pontoReposicao;
    }

    public Material(String nome, Double quantidade, String unidade, Double pontoReposicao) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.pontoReposicao = pontoReposicao;
    }

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Double getQuantidade() { return quantidade; }
    public void setQuantidade(Double quantidade) { this.quantidade = quantidade; }
    public String getUnidade() { return unidade; }
    public void setUnidade(String unidade) { this.unidade = unidade; }
    public Double getPontoReposicao() { return pontoReposicao; }
    public void setPontoReposicao(Double pontoReposicao) { this.pontoReposicao = pontoReposicao; }
}
