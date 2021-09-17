package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.CozinhaNotFoundException;
import com.algaworks.algafood.domain.exception.EntityNotDeleteException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	private static final String MSG_ENTITY_NOT_DELETE = "Cozinha com o id %d não pode ser deletada!";

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Transactional
	public Cozinha save(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}

	// class de serviço nao pode ter conhecimento da implementação da API
	@Transactional
	public void delete(Long id) {
		try {
			cozinhaRepository.deleteById(id);
			cozinhaRepository.flush();
		} catch (EmptyResultDataAccessException ex) {
			throw new CozinhaNotFoundException(id);
		} catch (DataIntegrityViolationException ex) {
			throw new EntityNotDeleteException(String.format(MSG_ENTITY_NOT_DELETE, id));
		}
	}

	public Cozinha buscar(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new CozinhaNotFoundException(cozinhaId));
	}
}
