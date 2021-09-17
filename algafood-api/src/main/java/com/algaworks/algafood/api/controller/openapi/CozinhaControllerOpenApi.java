package com.algaworks.algafood.api.controller.openapi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.model.input.CozinhaInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {
	
	@ApiOperation("Lista todas cozinhas")
	Page<CozinhaDTO> findAll(Pageable pageable);
	
	@ApiResponses({
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class),
		@ApiResponse(code = 400, message = "ID com formato inválido", response = Problem.class)
	})
	@ApiOperation("Busca Cozinha por ID")
	CozinhaDTO findById(@ApiParam(value = "Id da Cozinha", example = "1", required = true) Long cozinhaId);
	
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cozinha criada")
	})
	@ApiOperation("Cria Cozinha")
	CozinhaDTO add(@ApiParam(name = "corpo", value = "Representação da Cozinha com os dados", required = true) CozinhaInputDTO dto);
	
	@ApiResponses({
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class),
		@ApiResponse(code = 200, message = "Cozinha atualizada")
	})
	@ApiOperation("Atualiza Cozinha por ID")
	CozinhaDTO update(@ApiParam(value = "Id da Cozinha", example = "1", required = true) Long cozinhaId,
			@ApiParam(name = "corpo", value = "Representação da cozinha com os dados", required = true) CozinhaInputDTO dto);
	
	@ApiResponses({
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class),
		@ApiResponse(code = 204, message = "Cozinha excluída"),
	})
	@ApiOperation("Deleta Cozinha por ID")
	void delete(@ApiParam(value = "Id da Cozinha", example = "1", required = true) Long cozinhaId);
	
	@ApiResponses({
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class),
		@ApiResponse(code = 400, message = "Formato do nome inválido", response = Problem.class)
	})
	@ApiOperation("Lista as cozinhas por nome")
	Page<CozinhaDTO> listarCozinhasPorNome(@ApiParam(name = "nome", value = "Nome da cozinha") String nome, Pageable pageable);

}
