package br.com.jotavefood.pagamentos.dto;

import java.util.List;

public record PedidoDto(List<ItemPedidoDto> itens) {
}
