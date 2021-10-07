package com.algaworks.algafood.api.controller.openapi;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.ProdutoDTO;
import com.algaworks.algafood.api.model.input.ProdutoInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {

	@ApiOperation("Lista todos produtos do restaurante")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class),
		@ApiResponse(code = 400, message = "ID com formato inválido", response = Problem.class)
	})
	CollectionModel<ProdutoDTO> findAll(@ApiParam(value = "Id do restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "Indica se é para buscar somente restaurantes ativos ou todos", example = "false", defaultValue = "false") Boolean buscarTodos);
	
	@ApiOperation("Busca um produto de um restaurante")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class),
		@ApiResponse(code = 400, message = "ID do restaurante ou produto com formato inválido", response = Problem.class)
	})
	ProdutoDTO findById(@ApiParam(value = "Id do restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "Id do produto", example = "1", required = true) Long produtoId);

	@ApiOperation("Associa produto ao restaurante")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Produto criado com suceso"),
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class)
	})
	ProdutoDTO add(@ApiParam(value = "Id do restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(name = "corpo", value = "Representação do produto com os dados", required = true)  ProdutoInputDTO dto);
	
	@ApiOperation("Atualiza produto associado ao restaurante")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Produto atualizado com suceso"),
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class)
	})
	ProdutoDTO update(@ApiParam(value = "Id do restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "Id do produto", example = "1", required = true) Long produtoId, 
			@ApiParam(name = "corpo", value = "Representação do produto com os dados", required = true) ProdutoInputDTO dto);
}
