package com.fiap.stock.test;

import com.fiap.stock.dao.MovimentacaoDAO;
import com.fiap.stock.model.Movimentacao;
import com.fiap.stock.service.MovimentacaoService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovimentacaoServiceTest {
    private final MovimentacaoDAO dao = mock(MovimentacaoDAO.class);
    private final MovimentacaoService service = new MovimentacaoService(dao);

    @Test
    void registrar_ok() throws Exception {
        Movimentacao mov = new Movimentacao(null,1L,1L,5.0,"ENTRADA",null);
        doNothing().when(dao).registrar(mov);
        service.registrar(mov);
        verify(dao).registrar(mov);
    }

    @Test
    void registrar_quantidadeInvalida() {
        Movimentacao mov = new Movimentacao(null,1L,1L,0.0,"ENTRADA",null);
        assertThrows(IllegalArgumentException.class, () -> service.registrar(mov));
    }
}
