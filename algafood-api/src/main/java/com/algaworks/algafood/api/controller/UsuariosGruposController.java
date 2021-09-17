package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.controller.openapi.UserGruposControllerOpenApi;
import com.algaworks.algafood.api.model.GrupoDTO;
import com.algaworks.algafood.domain.exception.GrupoNotFoundException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;


@RestController
@RequestMapping(path = "/usuarios/{usuarioId}/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuariosGruposController implements UserGruposControllerOpenApi {
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private GrupoDTOAssembler grupoDTOAssembler;
	
	@Override
	@GetMapping
	public List<GrupoDTO> findAll(@PathVariable Long usuarioId) {
		var usuario = cadastroUsuarioService.findById(usuarioId);
		return grupoDTOAssembler.toCollectionDTO(usuario.getGrupos());
	}
	 
	@Override
	@GetMapping("/{grupoId}")
	public GrupoDTO findAll(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		var usuario = cadastroUsuarioService.findById(usuarioId);
		Optional<Grupo> grupoOP = usuario.getGrupos().stream().filter(g -> g.getId().equals(grupoId)).findAny();
		if (grupoOP.isEmpty()) {
			throw new GrupoNotFoundException(grupoId);
		}
		return grupoDTOAssembler.toDTO(grupoOP.get());
	}
	
	@Override
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void addGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuarioService.addGrupo(usuarioId, grupoId);
	}
	
	@Override
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuarioService.removeGrupo(usuarioId, grupoId);
	}

}
