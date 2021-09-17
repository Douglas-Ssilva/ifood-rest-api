package com.algaworks.algafood.api.controller.openapi;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.GrupoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Usuários")
public interface UserGruposControllerOpenApi {
	
	@ApiOperation("Lista todos grupos do usuário")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class),
		@ApiResponse(code = 400, message = "ID do usuário com formato inválido", response = Problem.class)
	})
	List<GrupoDTO> findAll(@ApiParam(value = "Id do usuário", example = "1", required = true) Long usuarioId);
	
	@ApiOperation("Busca grupo por ID do usuário")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class),
		@ApiResponse(code = 400, message = "ID do usuário ou grupo com formato inválido", response = Problem.class)
	})
	GrupoDTO findAll(@ApiParam(value = "Id do usuário", example = "1", required = true) Long usuarioId, 
			@ApiParam(value = "Id do grupo", example = "1", required = true) Long grupoId);
	
	@ApiOperation("Associa grupo ao usuário")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Associação realizada com sucesso"),
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class),
		@ApiResponse(code = 400, message = "ID do usuário ou grupo com formato inválido", response = Problem.class)
	})
	void addGrupo(@ApiParam(value = "Id do usuário", example = "1", required = true) Long usuarioId, 
			@ApiParam(value = "Id do grupo", example = "1", required = true) Long grupoId);
	
	@ApiOperation("Desassocia grupo do usuário")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class),
		@ApiResponse(code = 400, message = "ID do usuário ou grupo com formato inválido", response = Problem.class)
	})
	void removeGrupo(@ApiParam(value = "Id do usuário", example = "1", required = true) Long usuarioId, 
			@ApiParam(value = "Id do grupo", example = "1", required = true) Long grupoId);

}
