package com.fiap.stock.dao;

import com.fiap.stock.model.Consumo;
import java.time.LocalDateTime;
import java.util.List;

public interface ConsumoDAO {
    Consumo save(Consumo consumo) throws Exception;
    List<Consumo> findByMaterialId(Long materialId) throws Exception;
    List<Consumo> findByPeriod(LocalDateTime from, LocalDateTime to) throws Exception;
}
