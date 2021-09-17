package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntityNotDeleteException;
import com.algaworks.algafood.domain.exception.EstadoNotFoundException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	private static final String MSG_ENTITY_NOT_DELETE = "Estado de id: %d não pode ser deletado!";

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Transactional
	public Estado save(Estado estado) {
		return estadoRepository.save(estado);
	}

	@Transactional
	public void delete(Long id) {
		var estado = buscar(id);
		try {
			estadoRepository.delete(estado);
			estadoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntityNotDeleteException(String.format(MSG_ENTITY_NOT_DELETE, id));
		}
	}

	public Estado buscar(Long estadoId) {
		return estadoRepository.findById(estadoId).orElseThrow(() -> new EstadoNotFoundException(estadoId));
	}
	
}
