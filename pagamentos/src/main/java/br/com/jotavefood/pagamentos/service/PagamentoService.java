package br.com.jotavefood.pagamentos.service;

import br.com.jotavefood.pagamentos.dto.PagamentoDto;
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
import org.modelmapper.ModelMapper;


@Service
@Slf4j
public class PagamentoService {
    @Autowired
    private PagamentoRepository repository;


    public Page<PagamentoDto> obterTodos(Pageable paginacao){
        return repository.findAll(paginacao)
                .map(PagamentoDto::fromEntity);
    }

    public PagamentoDto obterPorId(Long id){
        return repository.findById(id).map(PagamentoDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException());
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

    public void excluirPagamento(Long id){
        repository.deleteById(id);
    }

}
