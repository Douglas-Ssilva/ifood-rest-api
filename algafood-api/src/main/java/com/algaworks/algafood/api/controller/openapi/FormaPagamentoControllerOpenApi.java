package com.algaworks.algafood.api.controller.openapi;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.model.input.FormaPagamentoInputDTO;
import com.algaworks.algafood.core.springfox.adapter.FormasPagamentoOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Formas de pagamento")
public interface FormaPagamentoControllerOpenApi {
	
	//Bug do SpringFox. Devido ao ResponseEntity como retorno, é preciso adicionar a classe que customizamos aqui
	@ApiOperation(value = "Lista todas formas de pagamento", response = FormasPagamentoOpenApi.class)
	ResponseEntity<CollectionModel<FormaPagamentoDTO>> findAll(ServletWebRequest request);
	
	@ApiResponses({
		@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class),
		@ApiResponse(code = 400, message = "ID com formato inválido", response = Problem.class)
	})
	@ApiOperation("Busca Forma de pagamento por ID")
	ResponseEntity<FormaPagamentoDTO> findById(@ApiParam( value = "Id da Forma de pagamento", example = "1", required = true) Long formaPagamentoId,
			ServletWebRequest request);
	
	@ApiResponses({
		@ApiResponse(code = 201, message = "Forma de pagamento criada")
	})
	@ApiOperation("Cria Forma de pagamento")
	FormaPagamentoDTO add(@ApiParam(name = "corpo", value = "Representação da Forma de pagamento com os dados", required = true) FormaPagamentoInputDTO dto);
	
	@ApiResponses({
		@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class),
		@ApiResponse(code = 200, message = "Forma de pagamento atualizada")
	})
	@ApiOperation("Atualiza Forma de pagamento por ID")
	FormaPagamentoDTO update(@ApiParam(name = "corpo", value = "Representação da Forma de pagamento com os dados", required = true) FormaPagamentoInputDTO dto, 
			@ApiParam(value = "Id da Forma de pagamento", example = "1", required = true) Long formaPagamentoId);
	
	@ApiResponses({
		@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class),
		@ApiResponse(code = 204, message = "Forma de pagamento excluída"),
	})
	@ApiOperation("Deleta Forma de pagamento por ID")
	void delete(@ApiParam(value = "Id da Forma de pagamento", example = "1", required = true) Long formaPagamentoId);

}
