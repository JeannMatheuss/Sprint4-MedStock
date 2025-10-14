package com.fiap.stock.model;

public class Consumo {
    private Long id;
    private Long idMaterial;
    private Double quantidadeDiaria;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdMaterial() { return idMaterial; }
    public void setIdMaterial(Long idMaterial) { this.idMaterial = idMaterial; }
    public Double getQuantidadeDiaria() { return quantidadeDiaria; }
    public void setQuantidadeDiaria(Double quantidadeDiaria) { this.quantidadeDiaria = quantidadeDiaria; }
}
