package com.algaworks.algafood.api.controller.openapi;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.PermissaoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

	
	@ApiOperation("Lista todas permissões de um grupo")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class),
		@ApiResponse(code = 400, message = "ID do grupo incompatível", response = Problem.class),
	})
	CollectionModel<PermissaoDTO> findAll(@ApiParam(example = "1", required = true, value = "Id do Grupo") Long grupoId);
	
	@ApiOperation("Associa permissão a um grupo")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Associação realizada com sucesso"),
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class),
		@ApiResponse(code = 400, message = "ID do grupo ou permissão incompatível", response = Problem.class),
	})
	ResponseEntity<Void> addPermissao(@ApiParam(example = "1", required = true, value = "Id do Grupo") Long grupoId, 
			@ApiParam(example = "1", required = true, value = "Id da permissão") Long permissaoId);
	
	@ApiOperation("Desassocia permissão de um grupo")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class),
		@ApiResponse(code = 400, message = "ID do grupo ou permissão incompatível", response = Problem.class),
	})
	ResponseEntity<Void> removePermissao(@ApiParam(example = "1", required = true, value = "Id do Grupo") Long grupoId, 
			@ApiParam(example = "1", required = true, value = "Id da permissão") Long permissaoId);

}
