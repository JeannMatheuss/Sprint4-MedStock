package com.fiap.stock.dto;


import javax.validation.constraints.*;


public class MaterialCreateDTO {
    @NotBlank
    private String nome;


    @NotNull
    @PositiveOrZero
    private Double quantidade;


    @NotBlank
    private String unidade;


    @NotNull
    @PositiveOrZero
    private Double pontoReposicao;


    // getters e setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Double getQuantidade() { return quantidade; }
    public void setQuantidade(Double quantidade) { this.quantidade = quantidade; }
    public String getUnidade() { return unidade; }
    public void setUnidade(String unidade) { this.unidade = unidade; }
    public Double getPontoReposicao() { return pontoReposicao; }
    public void setPontoReposicao(Double pontoReposicao) { this.pontoReposicao = pontoReposicao; }
}