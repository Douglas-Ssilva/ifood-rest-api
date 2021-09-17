package com.algaworks.algafood.api.controller.openapi;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.FormaPagamentoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

	@ApiOperation("Lista todas formas de pagamento de um restaurante")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
		@ApiResponse(code = 400, message = "ID do restaurante com formato inválido", response = Problem.class),
	})
	List<FormaPagamentoDTO> findAll(@ApiParam(value = "Id do restaurante", example = "1", required = true) Long restauranteId);
	
	@ApiOperation("Busca forma de pagamento específica por IDs")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado", response = Problem.class),
		@ApiResponse(code = 400, message = "ID do restaurante ou forma pagamento com formato inválido", response = Problem.class),
	})
	FormaPagamentoDTO findById(@ApiParam(value = "Id do restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "Id da forma de pagamento", example = "1", required = true) Long formaPagamentoId);
	
	@ApiOperation("Desassocia forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class)
	})
	void desassiociar(@ApiParam(value = "Id do restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "Id da forma de pagamento", example = "1", required = true)  Long formaPagamentoId);
	
	@ApiOperation("Associa forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Associação realizada com sucesso"),
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class)
	})
	void associar(@ApiParam(value = "Id do restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "Id da forma de pagamento", example = "1", required = true) Long formaPagamentoId);
	
}
