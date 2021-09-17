package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntityNotDeleteException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNotFoundException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {
	
	private static final String MSG_NOT_DELETE = "Usuário de id: %d não pode ser deletado.";
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	public Usuario findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new UsuarioNotFoundException(id));
	}
	
	public List<Usuario> findAll(){ 
		return repository.findAll();
	}
	
	@Transactional
	public Usuario merge(Usuario user) {
//		repository.jpaStopManagedThisEntity(user);
//		Optional<Usuario> userOP = repository.findByEmail(user.getEmail());
//		if (userOP.isPresent() && !user.equals(userOP.get())) {
//			throw new NegocioException(String.format("Já existe um usuário com esse email: %s cadastrado.", user.getEmail()));
//		}
		if (repository.existsByEmailAndIdNot(user.getEmail(), user.getId())) {
			throw new NegocioException(String.format("Já existe um usuário com esse email: %s cadastrado.", user.getEmail()));
		}
		
		return repository.save(user);
	}
	
	@Transactional
	public void update() {}
	
	@Transactional
	public void delete(Long id) {
		var usuario = findById(id);
		try {
			repository.delete(usuario);
			repository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntityNotDeleteException(String.format(MSG_NOT_DELETE, id));
		}
	}

	@Transactional
	public void updateSenha(Long id, String senhaAtual, String senhaNova) {
		var usuario = findById(id);
		if (!usuario.senhaCoincideCom(senhaAtual)) {
			throw new NegocioException("A senha atual não coincide com a senha do usuário!");
		}
		usuario.setSenha(senhaNova);
	}
	
	@Transactional
	public void addGrupo(Long usuarioId, Long grupoId) {
		var usuario = findById(usuarioId);
		var grupo = cadastroGrupoService.findById(grupoId);
		usuario.addGrupo(grupo);
	}

	@Transactional
	public void removeGrupo(Long usuarioId, Long grupoId) {
		var usuario = findById(usuarioId);
		var grupo = cadastroGrupoService.findById(grupoId);
		usuario.removeGrupo(grupo);
	}
	
	

}
