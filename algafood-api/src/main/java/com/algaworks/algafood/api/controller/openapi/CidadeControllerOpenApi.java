package com.algaworks.algafood.api.controller.openapi;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.api.model.input.CidadeInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {
	
	@ApiOperation("Lista todas cidades")
	CollectionModel<CidadeDTO> findAll();
	
	@ApiResponses({
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class),
		@ApiResponse(code = 400, message = "ID com formato inválido", response = Problem.class)
	})
	@ApiOperation("Busca cidade por ID")
	CidadeDTO findById(@ApiParam(value = "Id de uma cidade", example = "1", required = true) Long cidadeId);
	
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cidade criada")
	})
	@ApiOperation("Cria cidade")
	CidadeDTO save(@ApiParam(name = "corpo", value = "Representação da cidade com os dados", required = true) CidadeInputDTO cidadeInputDTO);
	
	@ApiResponses({
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class),
		@ApiResponse(code = 200, message = "Cidade atualizada")
	})
	@ApiOperation("Atualiza cidade por ID")
	CidadeDTO update(@ApiParam(value = "Id de uma cidade", example = "1", required = true) Long cidadeId,
			@ApiParam(name = "corpo", value = "Representação da cidade com os novos dados", required = true) CidadeInputDTO cidadeInputDTO);
	
	@ApiResponses({
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class),
		@ApiResponse(code = 204, message = "Cidade excluída"),
	})
	@ApiOperation("Deleta cidade por ID")
	void delete(@ApiParam(value = "Id de uma cidade", example = "1", required = true) Long cidadeId);
}