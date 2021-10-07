package com.algaworks.algafood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.assembler.GrupoInputDTODisassembler;
import com.algaworks.algafood.api.controller.openapi.GruposControllerOpenApi;
import com.algaworks.algafood.api.model.GrupoDTO;
import com.algaworks.algafood.api.model.input.GrupoInputDTO;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GruposControllerOpenApi {
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService; 
	
	@Autowired
	private GrupoDTOAssembler grupoDTOAssembler;
	
	@Autowired
	private GrupoInputDTODisassembler grupoInputDTODisassembler; 
	
	@Override
	@GetMapping
	public CollectionModel<GrupoDTO> findAll(){
		return grupoDTOAssembler.toCollectionModel(cadastroGrupoService.findAll());
	}
	
	@Override
	@GetMapping("/{grupoId}")
	public GrupoDTO findById(@PathVariable Long grupoId){ 
		return grupoDTOAssembler.toModel(cadastroGrupoService.findById(grupoId));
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO add(@RequestBody @Valid GrupoInputDTO dto) {
		var grupo = grupoInputDTODisassembler.toDomainObject(dto);
		return grupoDTOAssembler.toModel(cadastroGrupoService.merge(grupo));
	}
	
	@Override
	@PutMapping("/{grupoId}")
	public GrupoDTO update(@RequestBody @Valid GrupoInputDTO dto, @PathVariable Long grupoId) {
		var grupo = cadastroGrupoService.findById(grupoId);
		grupoInputDTODisassembler.copyProperties(dto, grupo);
		return grupoDTOAssembler.toModel(cadastroGrupoService.merge(grupo));
	}
	
	@Override
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long grupoId) {
		cadastroGrupoService.delete(grupoId);
	}
	
	

}
