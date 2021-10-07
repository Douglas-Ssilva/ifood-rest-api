package com.algaworks.algafood.api.controller.openapi;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.RestauranteDTOResumo;
import com.algaworks.algafood.api.model.RestauranteDTOResumoList;
import com.algaworks.algafood.api.model.input.RestauranteInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {
	
	@ApiOperation(value = "Lista todos restaurantes", hidden = false)
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", type = "string", name = "projecao", allowableValues = "apenas-nome", value = "Listar apenas por nome")
	})
	CollectionModel<RestauranteDTOResumoList> findAll();
	
	@ApiIgnore//ainda que esteja com hidden true, esse tipo aparece no modelo de representação. Nos exemplos exibimos RestauranteDTOResumoList, e RestauranteDTOResumoList é um resumo
	@ApiOperation(value = "Lista todos restaurantes", hidden = true)
	CollectionModel<RestauranteDTOResumo> findAllApenasNome();

	@ApiOperation("Busca restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
		@ApiResponse(code = 400, message = "ID com formato inválido", response = Problem.class),
	})
	RestauranteDTO findById(@ApiParam(value = "Id do restaurante", example = "1", required = true)  Long restauranteId);

	@ApiOperation("Cria restaurante")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Restaurante criado")
	})
	RestauranteDTO save(@ApiParam(name = "corpo", value = "Representação do restautante com os dados", required = true) RestauranteInputDTO restauranteInputDTO);

	@ApiResponses({
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
		@ApiResponse(code = 204, message = "Restaurante excluído"),
	})
	@ApiOperation("Remove restaurante por ID")
	ResponseEntity<Void> delete(@ApiParam(value = "Id do restaurante", example = "1", required = true) Long restauranteId);
	
	@ApiResponses({
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
		@ApiResponse(code = 200, message = "Restaurante atualizado")
	})
	@ApiOperation("Atualiza restaurante por ID")
	RestauranteDTO udpate(@ApiParam(value = "Id do restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(name = "corpo", value = "Representação do restautante com os dados", required = true)  RestauranteInputDTO restauranteInputDTO);
	
	@ApiResponses({
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
		@ApiResponse(code = 200, message = "Restaurante atualizado")
	})
	@ApiOperation("Atualiza restaurante por ID Patch")
	RestauranteDTO partialMerge(@ApiParam(value = "Id do restaurante", example = "1", required = true) Long restauranteId, 
			@ApiParam(name = "corpo", value = "Campos a serem atualizados", required = true) Map<String, Object> fieldsRestaurante, 
			HttpServletRequest httpServletRequest);
	
	@ApiResponses({
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
		@ApiResponse(code = 204, message = "Restaurante ativado com sucesso")
	})
	@ApiOperation("Ativa restaurante por ID")
	ResponseEntity<Void> ativar(@ApiParam(value = "Id do restaurante", example = "1", required = true) Long restauranteId);
	
	@ApiResponses({
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
		@ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
	})
	@ApiOperation("Ativa vários restaurantes por IDs")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	ResponseEntity<Void> ativarAll(@ApiParam(value = "Ids dos restaurants", example = "1,2", required = true) List<Long> ids);
	
	@ApiResponses({
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
		@ApiResponse(code = 204, message = "Restaurante desativado"),
	})
	@ApiOperation("Desativa restaurante por ID")
	ResponseEntity<Void> desativar(@ApiParam(value = "Id do restaurante", example = "1", required = true)  Long restauranteId);
	
	@ApiResponses({
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
		@ApiResponse(code = 204, message = "Restaurantes desativados"),
	})
	@ApiOperation("Desativa vários restaurantes por IDs")
	ResponseEntity<Void> desativarAll(@ApiParam(value = "Ids dos restaurants", example = "[1,2]", required = true) List<Long> ids);
	
	@ApiResponses({
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
		@ApiResponse(code = 204, message = "Restaurante aberto com sucesso")
	})
	@ApiOperation("Abre restaurante por ID")
	ResponseEntity<Void> abrir(@ApiParam(value = "Id do restaurante", example = "1", required = true) Long restauranteId);
	
	@ApiResponses({
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
		@ApiResponse(code = 204, message = "Restaurante fechado com sucesso")
	})
	@ApiOperation("Fecha restaurante por ID")
	ResponseEntity<Void> fechar(@ApiParam(value = "Id do restaurante", example = "1", required = true) Long restauranteId);
	

}
