package com.jotavefood.pedido.pedidos.dto;

import com.jotavefood.pedido.pedidos.model.Pedido;
import com.jotavefood.pedido.pedidos.model.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public record PedidoDto(Long id, LocalDateTime dataHora, Status status, List<ItemPedidoDto> itens) {

    public static PedidoDto fromEntity(Pedido pedido){
        List<ItemPedidoDto> itensDto = pedido.getItens()
                .stream().map(ItemPedidoDto::fromEntity).collect(Collectors.toList());

        return new PedidoDto(
                pedido.getId(),
                pedido.getDataHora(),
                pedido.getStatus(),
                itensDto
        );
    }
}
