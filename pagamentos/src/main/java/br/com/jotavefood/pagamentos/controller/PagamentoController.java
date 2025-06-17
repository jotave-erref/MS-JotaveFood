package br.com.jotavefood.pagamentos.controller;

import br.com.jotavefood.pagamentos.dto.PagamentoComItensDto;
import br.com.jotavefood.pagamentos.dto.PagamentoDto;
import br.com.jotavefood.pagamentos.service.PagamentoService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @GetMapping
    public Page<PagamentoDto> listar(@PageableDefault(size = 10) Pageable page){
        return service.obterTodos(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoComItensDto> detalhar(@PathVariable @NotNull Long id){
        PagamentoComItensDto dto = service.detalharPagamentoComItens(id);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PagamentoDto> cadastrar(@RequestBody @Valid PagamentoDto dto, UriComponentsBuilder uri){
        PagamentoDto pagamento = service.criarPagamento(dto);
        URI endereco = uri.path("/pagamentos/{id}").buildAndExpand(pagamento.id()).toUri();

        return ResponseEntity.created(endereco).body(pagamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDto> atualizar(@PathVariable @NotNull Long id, @RequestBody PagamentoDto dto){
        PagamentoDto atualizado = service.atualizarPagamento(id, dto);

        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{id}/confirmar")
    @CircuitBreaker(name = "atualizaPedido", fallbackMethod = "pagamentoAutorizadoComIntegracaopendente")
    public void confrimarPagamento(@PathVariable @NotNull Long id){
        service.confirmaPagamento(id);
    }

    public void pagamentoAutorizadoComIntegracaopendente(Long id, Exception e){
        service.alteraStatus(id);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<PagamentoDto> excluir(@PathVariable Long id){
        service.excluirPagamento(id);
        return ResponseEntity.noContent().build();
    }

}
