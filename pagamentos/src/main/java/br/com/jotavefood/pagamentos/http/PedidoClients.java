package br.com.jotavefood.pagamentos.http;

import br.com.jotavefood.pagamentos.dto.ItemPedidoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("pedidos-ms")
public interface PedidoClients {

    @RequestMapping(method = RequestMethod.PUT, value = ("/pedidos/{id}/pago"))
    void atualizaPagamento(@PathVariable Long id);

    @RequestMapping(method = RequestMethod.GET, value = ("/pedidos/{id}"))
    List<ItemPedidoDto> buscarItensDoPedido(@PathVariable Long id);
}
