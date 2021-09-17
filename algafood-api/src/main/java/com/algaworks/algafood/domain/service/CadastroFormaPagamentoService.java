package com.algaworks.algafood.domain.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntityNotDeleteException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNotFoundException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Situacao;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {
	
	private static final String MSG_NOT_DELETE = "Forma de pagamento de ID %s não pode ser deletada!";
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	public List<FormaPagamento> findAll() {
		return formaPagamentoRepository.findAll();
	}

	public FormaPagamento findById(Long id) {
		return formaPagamentoRepository.findById(id).orElseThrow(() -> new FormaPagamentoNotFoundException(id));
	}

	@Transactional
	public FormaPagamento merge(FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}

	@Transactional
	public void delete(Long id) {
		var formaPagamento = findById(id);
		try {
//			formaPagamentoRepository.delete(formaPagamento);
			formaPagamento.setSituacao(Situacao.EXCLUIDO);
			formaPagamentoRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntityNotDeleteException(String.format(MSG_NOT_DELETE, id));
		}
	}

	public Optional<OffsetDateTime> findLastUpdateDate() {
		return formaPagamentoRepository.findLastUpdateDate();
	}

	public List<FormaPagamento> findBySituacao() {
		return formaPagamentoRepository.findBySituacao(Situacao.ATIVO);
	}

	public Optional<OffsetDateTime> findLastUpdateDate(Long id) {
		return formaPagamentoRepository.findLastUpdateDate(id);
	}

}
