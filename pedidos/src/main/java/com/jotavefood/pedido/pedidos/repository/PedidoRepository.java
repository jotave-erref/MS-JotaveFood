package com.jotavefood.pedido.pedidos.repository;

import com.jotavefood.pedido.pedidos.model.Pedido;
import com.jotavefood.pedido.pedidos.model.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Pedido p set p.status = :status where p = :pedido")
    void atualizaStatus(Status status, Pedido pedido);

    @Query(value = "SELECT p from Pedido p LEFT JOIN FETCH p.itens where p.id = :id")
    Pedido porIdComItens(Long id);

    @Query("SELECT DISTINCT p FROM Pedido p LEFT JOIN FETCH p.itens")
    List<Pedido> findAllComItens();
}
