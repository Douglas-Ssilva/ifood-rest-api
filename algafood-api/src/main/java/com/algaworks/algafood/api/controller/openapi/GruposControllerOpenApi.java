package com.algaworks.algafood.api.controller.openapi;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.GrupoDTO;
import com.algaworks.algafood.api.model.input.GrupoInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GruposControllerOpenApi {
	
	@ApiOperation("Lista todos grupos")
	List<GrupoDTO> findAll();
	
	@ApiResponses({
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class),
		@ApiResponse(code = 400, message = "ID com formato inválido", response = Problem.class)
	})
	@ApiOperation("Busca grupo por ID")
	GrupoDTO findById(@ApiParam(value = "Id do grupo", example = "1", required = true) Long grupoId);
	
	@ApiResponses({
		@ApiResponse(code = 201, message = "Grupo criado")
	})
	@ApiOperation("Cria grupo")
	GrupoDTO add(@ApiParam(name = "corpo", value = "Representação do grupo com os dados", required = true) GrupoInputDTO dto);
	
	@ApiResponses({
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class),
		@ApiResponse(code = 200, message = "Grupo atualizado")
	})
	@ApiOperation("Atualiza grupo por ID")
	GrupoDTO update(@ApiParam(name = "corpo", value = "Representação do grupo com os dados", required = true) GrupoInputDTO dto,
			@ApiParam(value = "Id do grupo", example = "1", required = true) Long grupoId);
	
	@ApiResponses({
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class),
		@ApiResponse(code = 204, message = "Grupo excluído"),
	})
	@ApiOperation("Deleta grupo por ID")
	void delete(@ApiParam(value = "Id do grupo", example = "1", required = true) Long grupoId);

}
