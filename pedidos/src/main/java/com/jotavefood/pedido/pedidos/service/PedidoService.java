package com.jotavefood.pedido.pedidos.service;

import com.jotavefood.pedido.pedidos.dto.ItemPedidoDto;
import com.jotavefood.pedido.pedidos.dto.PedidoDto;
import com.jotavefood.pedido.pedidos.dto.StatusDto;
import com.jotavefood.pedido.pedidos.model.ItemPedido;
import com.jotavefood.pedido.pedidos.model.Pedido;
import com.jotavefood.pedido.pedidos.model.Status;
import com.jotavefood.pedido.pedidos.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    @Autowired
    private PedidoRepository repository;


    public List<PedidoDto> obterTodos() {
        return repository.findAllComItens().stream()
                .map(PedidoDto::fromEntity).toList();
    }

    public PedidoDto obterPorId(Long id) {
        return repository.findById(id).map(PedidoDto::fromEntity)
                .orElseThrow(EntityNotFoundException::new);

    }

    public PedidoDto criarPedido(PedidoDto dto) {
        Pedido pedido = new Pedido();
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus(Status.REALIZADO);

        List<ItemPedido> itens = dto.itens().stream().map(itemDto -> {
            ItemPedido item = new ItemPedido();
            item.setQuantidade(itemDto.quantidade());
            item.setDescricao(itemDto.descricao());
            item.setPedido(pedido);
            return item;
                }).toList();
        pedido.setItens(itens);

        Pedido salvo = repository.save(pedido);
        return PedidoDto.fromEntity(salvo);

    }

    public PedidoDto atualizaStatus(Long id, StatusDto dto) {

        Pedido pedido = repository.porIdComItens(id);

        if (pedido == null) {
            throw new EntityNotFoundException();
        }

        pedido.setStatus(dto.status());
        repository.atualizaStatus(dto.status(), pedido);
        return new PedidoDto(pedido.getId(), pedido.getDataHora(), pedido.getStatus(),
                pedido.getItens().stream().map(ItemPedidoDto::fromEntity).collect(Collectors.toList()));
    }

    public void aprovaPagamentoPedido(Long id) {

        Pedido pedido = repository.porIdComItens(id);

        if (pedido == null) {
            throw new EntityNotFoundException();
        }

        pedido.setStatus(Status.PAGO);
        repository.atualizaStatus(Status.PAGO, pedido);
    }

}
