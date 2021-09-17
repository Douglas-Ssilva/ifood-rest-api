package com.algaworks.algafood.api.controller.openapi;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.FotoProdutoDTO;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Produtos")
public interface ProdutoFotoControllerOpenApi {

	@ApiOperation(value = "Atualizar foto de um restaurante", notes = "Retorna os dados da foto atualizada")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Foto do produto atualizada"),
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class),
		@ApiResponse(code = 400, message = "ID do restaurante com formato inválido", response = Problem.class)
	})
	FotoProdutoDTO atualizarFoto(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId, 
			FotoProdutoInput produtoInput,
			@ApiParam(value = "Arquivo da foto do produto (máximo 500KB, apenas JPG e PNG)", hidden = false, required = true) MultipartFile arquivo) throws IOException;
	
	@ApiOperation(value = "Buscar foto", hidden = false, produces = "image/png,image/jpeg")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class),
		@ApiResponse(code = 400, message = "ID do restaurante com formato inválido", response = Problem.class)
	})
	FotoProdutoDTO buscarFoto(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId);
	
	@ApiOperation(value = "Buscar foto", hidden = true)
	ResponseEntity<?> buscarFotoInputStream(Long restauranteId, Long produtoId, String accept) throws HttpMediaTypeNotAcceptableException;

	@ApiOperation("Remove foto do restaurante")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Foto excluída com sucesso"),
		@ApiResponse(code = 400, message = "ID do restaurante ou produto com formato inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class)
	})
	void delete(@ApiParam(value = "ID do restaurante", example = "1", required = true)  Long restauranteId, 
			@ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId);
}
