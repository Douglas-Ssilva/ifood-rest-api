package com.algaworks.algafood.api.controller.openapi;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.api.model.input.UsuarioInputDTO;
import com.algaworks.algafood.api.model.input.UsuarioInputSemSenhaDTO;
import com.algaworks.algafood.api.model.input.UsuarioInputUpdateSenhaAntigaENovaDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Usuários")
public interface UserControllerOpenApi {

	@ApiOperation("Lista todos usuários")
	List<UsuarioDTO> findAll();
	
	@ApiOperation("Busca usuário por Id")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class),
		@ApiResponse(code = 400, message = "ID do usuário com formato inválido", response = Problem.class)
	})
	UsuarioDTO findById(@ApiParam( value = "Id do usuário", example = "1", required = true) Long userId);
	
	@ApiOperation("Cria usuário")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Usuário criado")
	})
	UsuarioDTO add(@ApiParam(name = "corpo", value = "Representação do usuário com os dados", required = true) UsuarioInputDTO dto);
	
	@ApiOperation("Atualiza usuário por ID")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class),
		@ApiResponse(code = 400, message = "ID do usuário com formato inválido", response = Problem.class),
		@ApiResponse(code = 200, message = "Usuário atualizado")
	})
	UsuarioDTO update(@ApiParam(name = "corpo", value = "Representação do usuário com os dados", required = true) UsuarioInputSemSenhaDTO dto, 
			@ApiParam(value = "Id do usuário", example = "1", required = true) Long userId);
	
	@ApiOperation("Atualiza senha por ID")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class),
		@ApiResponse(code = 400, message = "ID do usuário com formato inválido", response = Problem.class),
		@ApiResponse(code = 204, message = "Senha atualizada com sucesso")
	})
	void updateSenha(@ApiParam(name = "corpo", value = "Senha do usuário", required = true)  UsuarioInputUpdateSenhaAntigaENovaDTO dto, 
			@ApiParam( value = "Id do usuário", example = "1", required = true) Long userId);
	
	@ApiOperation("Remove usuário por ID")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Recurso não encontrado", response = Problem.class),
		@ApiResponse(code = 400, message = "ID do usuário com formato inválido", response = Problem.class),
		@ApiResponse(code = 204, message = "Usuário removido")
	})
	void delete(@ApiParam(value = "Id do usuário", example = "1", required = true) Long userId);
}
