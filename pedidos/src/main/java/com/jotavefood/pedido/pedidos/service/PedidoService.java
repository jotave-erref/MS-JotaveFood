package com.jotavefood.pedido.pedidos.service;

import com.jotavefood.pedido.pedidos.dto.PedidoDto;
import com.jotavefood.pedido.pedidos.dto.StatusDto;
import com.jotavefood.pedido.pedidos.model.Pedido;
import com.jotavefood.pedido.pedidos.model.Status;
import com.jotavefood.pedido.pedidos.repository.PedidoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    @Autowired
    private PedidoRepository repository;


    public List<PedidoDto> obterTodos() {
        return repository.findAll().stream()
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
        pedido.getItens().forEach(item -> item.setPedido(pedido));

        Pedido salvo = repository.save(pedido);

        return new PedidoDto(
                salvo.getId(),
                salvo.getDataHora(),
                salvo.getStatus(),
                salvo.getItens()
        );

    }

    public PedidoDto atualizaStatus(Long id, StatusDto dto) {

        Pedido pedido = repository.porIdComItens(id);

        if (pedido == null) {
            throw new EntityNotFoundException();
        }

        pedido.setStatus(dto.status());
        repository.atualizaStatus(dto.status(), pedido);
        return new PedidoDto(pedido.getId(), pedido.getDataHora(), pedido.getStatus(), pedido.getItens());
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
