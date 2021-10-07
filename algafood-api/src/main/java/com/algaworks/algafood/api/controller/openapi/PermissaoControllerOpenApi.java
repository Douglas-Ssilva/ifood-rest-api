package com.algaworks.algafood.api.controller.openapi;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.model.PermissaoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Permissões")
public interface PermissaoControllerOpenApi {
	
	@ApiOperation("Lista todas permissões")
	CollectionModel<PermissaoDTO> findAll();

}
