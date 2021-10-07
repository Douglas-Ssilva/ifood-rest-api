package com.algaworks.algafood.api.controller.openapi;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.PedidoDTO;
import com.algaworks.algafood.api.model.PedidoResumoDTO;
import com.algaworks.algafood.api.model.input.PedidoInputDTO;
import com.algaworks.algafood.domain.model.filter.PedidoFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Pedidos")
public interface PedidosControllerOpenApi {
	
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta.", name = "campos", 
				type = "string", paramType = "query", example = "nomeCliente,codigo")
	})
	@ApiOperation("Lista todos pedidos")
	PagedModel<PedidoResumoDTO> pesquisar(PedidoFilter filter, @PageableDefault(size = 2) Pageable pageable);

	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta.", name = "campos", 
				type = "string", paramType = "query", example = "nomeCliente,codigo")
	})
	@ApiOperation("Busca pedido por código")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class),
	})
	PedidoDTO findById(@ApiParam(value = "Código do pedido", example = "787c2598-574b-4db0-b611-948aeb0b5357", required = true) String pedidoCodigo);
	
	@ApiOperation("Emite pedido")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Pedido emitido com sucesso")
	})
	PedidoDTO emitirPedido(@ApiParam(value = "Corpo com a representação para emitir pedidos", name = "pedidoDTO" ) PedidoInputDTO dto);

}
