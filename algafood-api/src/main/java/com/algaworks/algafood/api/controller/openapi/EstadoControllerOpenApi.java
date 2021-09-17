package com.algaworks.algafood.api.controller.openapi;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.EstadoDTO;
import com.algaworks.algafood.api.model.input.EstadoInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

	@ApiOperation("Lista todos estados")
	List<EstadoDTO> findAll();
	
	@ApiOperation("Busca estado por ID")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class),
		@ApiResponse(code = 400, message = "ID com formato inválido", response = Problem.class)
	})
	EstadoDTO findById(@ApiParam(value = "Id do estado", example = "1", required = true) Long estadoId);
	
	@ApiOperation("Cria estado")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Estado criado")
	})
	EstadoDTO save(@ApiParam(name = "corpo", value = "Representação do estado com os dados", required = true) EstadoInputDTO estadoInputDTO);
	
	@ApiOperation("Atualiza estado por ID")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class),
		@ApiResponse(code = 200, message = "Estado atualizado"),
	})
	EstadoDTO update(@ApiParam(example = "1", value = "Id do estado", required = true) Long estadoId, 
			@ApiParam(name = "corpo", value = "Representação do estado com os dados", required = true) EstadoInputDTO estadoInputDTO);
	
	@ApiOperation("Deleta estado por ID")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class),
		@ApiResponse(code = 204, message = "Estado excluído"),
	})
	void delete(@ApiParam(value = "Id do estado", example = "1", required = true)  Long estadoId);
	
}
