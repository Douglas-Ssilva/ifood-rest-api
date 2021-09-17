package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.CidadeNotFoundException;
import com.algaworks.algafood.domain.exception.EntityNotDeleteException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

	private static final String MSG_ENTITY_NOT_DELETE = "Cidade de id: %d não pode ser deletada.";

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	@Transactional
	public Cidade save(Cidade cidade) throws EntityNotFoundException {
		var estado = buscarEstado(cidade);
		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}

	private Estado buscarEstado(Cidade cidade) {
		return cadastroEstadoService.buscar(cidade.getEstado().getId());
	}

	public Cidade buscar(Long id) {
		return cidadeRepository.findById(id).orElseThrow(() -> new CidadeNotFoundException(id));
	}

	@Transactional
	public void delete(Long id) {
		try {
			cidadeRepository.deleteById(id);
			cidadeRepository.flush();//devido ao @Transactional que faz com que a operação de delete seja executado depois da última linha desse método
									//aí lança a exception porém fora do try catch
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntityNotDeleteException(String.format(MSG_ENTITY_NOT_DELETE, id));
		}
	}

}
