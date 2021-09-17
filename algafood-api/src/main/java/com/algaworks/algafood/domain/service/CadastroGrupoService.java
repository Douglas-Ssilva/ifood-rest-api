package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntityNotDeleteException;
import com.algaworks.algafood.domain.exception.GrupoNotFoundException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {
	
	private static final String MSG_GRUPO_NOT_DELETE = "Grupo de id: %d está em uso.";
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroPermissaoService cadastroPermissaoService;
	
	public List<Grupo> findAll() {
		return grupoRepository.findAll();
	}

	public Grupo findById(Long id) {
		return grupoRepository.findById(id).orElseThrow(() -> new GrupoNotFoundException(id));
	}

	@Transactional
	public Grupo merge(Grupo grupo) {
		return grupoRepository.save(grupo);
	}

	@Transactional
	public void delete(Long id) {
		var grupo = findById(id);
		try {
			grupoRepository.delete(grupo);
			grupoRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntityNotDeleteException(String.format(MSG_GRUPO_NOT_DELETE, id));
		}
	}

	@Transactional
	public void addPermissao(Long grupoId, Long permissaoId) {
		var grupo = findById(grupoId);
		var permissao = cadastroPermissaoService.findById(permissaoId);
		grupo.addPermissao(permissao);
	}

	@Transactional
	public void removePermissao(Long grupoId, Long permissaoId) {
		var grupo = findById(grupoId);
		var permissao = cadastroPermissaoService.findById(permissaoId);
		grupo.removePermissao(permissao);
		
	}

}
