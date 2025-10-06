package com.fiap.stock.model;

import java.time.LocalDateTime;

public class Consumo {
    private Long id;
    private Long materialId;
    private Double quantidade;
    private LocalDateTime dataApontamento;
    private String responsavel;

    public Consumo() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getMaterialId() { return materialId; }
    public void setMaterialId(Long materialId) { this.materialId = materialId; }
    public Double getQuantidade() { return quantidade; }
    public void setQuantidade(Double quantidade) { this.quantidade = quantidade; }
    public LocalDateTime getDataApontamento() { return dataApontamento; }
    public void setDataApontamento(LocalDateTime dataApontamento) { this.dataApontamento = dataApontamento; }
    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }
}
