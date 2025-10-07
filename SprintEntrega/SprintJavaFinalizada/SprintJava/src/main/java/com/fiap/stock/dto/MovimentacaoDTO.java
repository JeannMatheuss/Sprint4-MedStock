package com.fiap.stock.dto;

import javax.validation.constraints.*;

public class MovimentacaoDTO {
    @NotNull
    private Long materialId;

    @NotNull
    private Integer usuarioId;

    @NotBlank
    @Pattern(regexp = "ENTRADA|SAIDA", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String tipo;

    @NotNull @Positive
    private Integer quantidade;

    // getters/setters
    public Long getMaterialId() { return materialId; }
    public void setMaterialId(Long materialId) { this.materialId = materialId; }
    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
}
