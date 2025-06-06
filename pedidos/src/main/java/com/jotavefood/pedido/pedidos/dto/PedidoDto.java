package com.jotavefood.pedido.pedidos.dto;

import com.jotavefood.pedido.pedidos.model.ItemPedido;
import com.jotavefood.pedido.pedidos.model.Pedido;
import com.jotavefood.pedido.pedidos.model.Status;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoDto(Long id, LocalDateTime dataHora, Status status, List<ItemPedido> itens) {

    public static PedidoDto fromEntity(Pedido pedido){
        return new PedidoDto(
                pedido.getId(),
                pedido.getDataHora(),
                pedido.getStatus(),
                pedido.getItens()
        );
    }
}
