package br.com.jotavefood.pagamentos.service;

import br.com.jotavefood.pagamentos.dto.ItemPedidoDto;
import br.com.jotavefood.pagamentos.dto.PagamentoComItensDto;
import br.com.jotavefood.pagamentos.dto.PagamentoDto;
import br.com.jotavefood.pagamentos.http.PedidoClients;
import br.com.jotavefood.pagamentos.model.Pagamento;
import br.com.jotavefood.pagamentos.model.Status;
import br.com.jotavefood.pagamentos.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class PagamentoService {
    @Autowired
    private PagamentoRepository repository;

    @Autowired
    private PedidoClients pedido;


    public Page<PagamentoDto> obterTodos(Pageable paginacao){
        return repository.findAll(paginacao)
                .map(PagamentoDto::fromEntity);
    }

    public PagamentoComItensDto detalharPagamentoComItens(Long id){
        Pagamento pagamento = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado"));

        List<ItemPedidoDto> itens = pedido.buscarItensDoPedido(pagamento.getId());
        return new PagamentoComItensDto(
                pagamento.getId(),
                pagamento.getValor(),
                pagamento.getNome(),
                pagamento.getNumero(),
                pagamento.getExpiracao(),
                pagamento.getCodigo(),
                pagamento.getStatus(),
                pagamento.getFormaDePagamentoId(),
                pagamento.getPedidoId(),
                itens);

    }

    public PagamentoDto criarPagamento(PagamentoDto dto) {
        log.info("Criando pagamento para DTO: {}", dto);

        var pagamento = new Pagamento();
        pagamento.setValor(dto.valor());
        pagamento.setNome(dto.nome());
        pagamento.setNumero(dto.numero());
        pagamento.setExpiracao(dto.expiracao());
        pagamento.setCodigo(dto.codigo());
        pagamento.setPedidoId(dto.pedidoId());
        pagamento.setFormaDePagamentoId(dto.formaDePagamentoId());
        pagamento.setStatus(Status.CRIADO);

        log.info("Salvando pagamento: {}", pagamento);

        pagamento = repository.save(pagamento);

        return new PagamentoDto(
                pagamento.getId(),
                pagamento.getValor(),
                pagamento.getNome(),
                pagamento.getNumero(),
                pagamento.getExpiracao(),
                pagamento.getCodigo(),
                pagamento.getStatus(),
                pagamento.getPedidoId(),
                pagamento.getFormaDePagamentoId()
        );
    }

    @Transactional
    public PagamentoDto atualizarPagamento(Long id, PagamentoDto dto){
        Pagamento pagamento = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());

        pagamento.setValor(dto.valor());
        pagamento.setNome(dto.nome());
        pagamento.setNumero(dto.numero());
        pagamento.setExpiracao(dto.expiracao());
        pagamento.setCodigo(dto.codigo());
        pagamento.setPedidoId(dto.pedidoId());
        pagamento.setFormaDePagamentoId(dto.formaDePagamentoId());

        pagamento = repository.save(pagamento);

        return PagamentoDto.fromEntity(pagamento);
    }

    public void confirmaPagamento(Long id){
        Optional<Pagamento> pagamento = repository.findById(id);

        if(!pagamento.isPresent()){
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(Status.APROVADO);
        repository.save(pagamento.get());
        pedido.atualizaPagamento(pagamento.get().getPedidoId());
    }

    public void alteraStatus(Long id){
        Optional<Pagamento> pagamento = repository.findById(id);

        if(!pagamento.isPresent()){
            throw new EntityNotFoundException();
        }

        pagamento.get().setStatus(Status.APROVADO_SEM_INTEGRACAO);
        repository.save(pagamento.get());
    }

    public void excluirPagamento(Long id){
        repository.deleteById(id);
    }

}
