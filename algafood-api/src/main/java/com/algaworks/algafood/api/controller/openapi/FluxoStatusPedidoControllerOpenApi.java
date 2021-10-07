package com.algaworks.algafood.api.controller.openapi;

import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Pedidos")
public interface FluxoStatusPedidoControllerOpenApi {

	@ApiOperation("Confirmar do pedido")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Pedido confirmado com sucesso"),
		@ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> confirmar(@ApiParam(value = "Código do pedido", example = "787c2598-574b-4db0-b611-948aeb0b5357", required = true) String codigoPedido);
	
	@ApiOperation("Entregar do pedido")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Pedido entregue com sucesso"),
		@ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> entregar(@ApiParam(value = "Código do pedido", example = "787c2598-574b-4db0-b611-948aeb0b5357", required = true) String codigoPedido);
	
	@ApiOperation("Cancelar do pedido")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Pedido cancelado com sucesso"),
		@ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
	})
	ResponseEntity<Void> cancelar(@ApiParam(value = "Código do pedido", example = "787c2598-574b-4db0-b611-948aeb0b5357", required = true) String codigoPedido);

}
