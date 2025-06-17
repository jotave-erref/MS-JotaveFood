package br.com.jotavefood.pagamentos.dto;

import br.com.jotavefood.pagamentos.model.Status;

import java.math.BigDecimal;
import java.util.List;

public record PagamentoComItensDto(
        Long id,
        BigDecimal valor,
        String nome, String numero,
        String expiracao,
        String codigo,
        Status status,
        Long formaDePagamentoId,
        Long pedidoId,
        List<ItemPedidoDto> itens) {
}
