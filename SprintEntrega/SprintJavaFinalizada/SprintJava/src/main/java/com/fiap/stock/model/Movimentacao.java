package com.fiap.stock.model;

import java.time.LocalDateTime;

public class Movimentacao {
    private Long id;
    private Long idMaterial;
    private Long idUsuario;
    private Double quantidade;
    private String tipo; // ENTRADA ou SAIDA
    private LocalDateTime dataHora;

    public Movimentacao() {}

    public Movimentacao(Long id, Long idMaterial, Long idUsuario, Double quantidade, String tipo, LocalDateTime dataHora) {
        this.id = id;
        this.idMaterial = idMaterial;
        this.idUsuario = idUsuario;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.dataHora = dataHora;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdMaterial() { return idMaterial; }
    public void setIdMaterial(Long idMaterial) { this.idMaterial = idMaterial; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Double getQuantidade() { return quantidade; }
    public void setQuantidade(Double quantidade) { this.quantidade = quantidade; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}
