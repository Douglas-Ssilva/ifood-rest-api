package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PermissaoDTOAssembler;
import com.algaworks.algafood.api.controller.openapi.PermissaoControllerOpenApi;
import com.algaworks.algafood.api.model.PermissaoDTO;
import com.algaworks.algafood.domain.service.PermissaoService;

@RestController
@RequestMapping(path = "/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController  implements PermissaoControllerOpenApi {

	@Autowired
	private PermissaoService permissaoService;
	
	@Autowired
	private PermissaoDTOAssembler assembler;
	
	@Override
	@GetMapping
	public CollectionModel<PermissaoDTO> findAll() {
		var permissoes = permissaoService.findAll();
		return assembler.toCollectionModel(permissoes);
	}

}
