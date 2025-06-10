package com.jotavefood.pedido.pedidos.dto;

import com.jotavefood.pedido.pedidos.model.ItemPedido;

public record ItemPedidoDto(Long id, Integer quantidade, String descricao) {

    public static ItemPedidoDto fromEntity(ItemPedido item) {
        return new ItemPedidoDto(
                item.getId(),
                item.getQuantidade(),
                item.getDescricao()
        );
    }
}
