package com.algaworks.algafood.api.controller.openapi;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.UsuarioDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteUsuariosControllerOpenApi {

	@ApiOperation("Lista todos usuários do restautante")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class),
		@ApiResponse(code = 400, message = "ID do restaurante com formato inválido", response = Problem.class),
	})
	CollectionModel<UsuarioDTO> findAll(@ApiParam(value = "Id do restaurante", example = "1", required = true) Long restauranteId);
	
	@ApiOperation("Desassociação de restaurante com usuário responsável")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> desassiociar(@ApiParam(value = "Id do restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "Id do usuário", example = "1", required = true) Long usuarioId);
	
	@ApiOperation("Associação de restaurante com usuário responsável")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Associação realizada com sucesso"),
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> associar(@ApiParam(value = "Id do restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "Id do usuário", example = "1", required = true) Long usuarioId);
}
