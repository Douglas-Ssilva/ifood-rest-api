package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.assembler.PermissaoDTOAssembler;
import com.algaworks.algafood.api.controller.openapi.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafood.api.model.PermissaoDTO;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(path = "/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissoesController implements GrupoPermissaoControllerOpenApi {
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	@Autowired
	private PermissaoDTOAssembler permissaoDTOAssembler;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Override
	@GetMapping 
	public CollectionModel<PermissaoDTO> findAll(@PathVariable Long grupoId) {
		var grupo = cadastroGrupoService.findById(grupoId);
		var dtos = permissaoDTOAssembler.toCollectionModelGrupoPermissoes(grupo.getPermissoes(), grupoId);
		dtos.getContent().forEach(p -> p.add(algaLinks.linkToDesassociarPermissaoGrupo(grupoId, p.getId(), "desassociar")));
		return dtos;
	}
	
	@Override
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> addPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupoService.addPermissao(grupoId, permissaoId);
		return ResponseEntity.noContent().build();
		
	}
	
	@Override
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> removePermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupoService.removePermissao(grupoId, permissaoId);
		return ResponseEntity.noContent().build();
	}

}
