package com.algaworks.algafood.api.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.ProdutoDTOAssembler;
import com.algaworks.algafood.api.assembler.ProdutoDTODisassembler;
import com.algaworks.algafood.api.controller.openapi.RestauranteProdutoControllerOpenApi;
import com.algaworks.algafood.api.model.ProdutoDTO;
import com.algaworks.algafood.api.model.input.ProdutoInputDTO;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutosController implements RestauranteProdutoControllerOpenApi {
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;

	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private ProdutoDTOAssembler produtoDTOAssembler; 

	@Autowired
	private ProdutoDTODisassembler produtoDTODisassembler; 
	
	@Override
	@GetMapping//desenvolvimento:8080/restaurantes/1/produtos?buscarTodos=true
	public CollectionModel<ProdutoDTO> findAll(@PathVariable Long restauranteId, @RequestParam(required = false) Boolean buscarTodos) {
		var restaurante = cadastroRestauranteService.buscar(restauranteId);
	 	var produtos = Objects.nonNull(buscarTodos) && buscarTodos.booleanValue() ? restaurante.getProdutos() : cadastroProdutoService.findByProdutosAtivos(restaurante);
		var dtos = produtoDTOAssembler.toCollectionModel(produtos, restauranteId);
		return dtos;
	}
	
	@Override
	@GetMapping("/{produtoId}")
	public ProdutoDTO findById(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		var produto = cadastroProdutoService.findById(produtoId, restauranteId);
		return produtoDTOAssembler.toModel(produto);
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) 
	public ProdutoDTO add(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInputDTO dto) {
		var produto = produtoDTODisassembler.toDomainObject(dto);
		var produtoBD = cadastroRestauranteService.associarProduto(restauranteId, produto);
		return produtoDTOAssembler.toModel(produtoBD);
	}
	
	@Override
	@PutMapping("/{produtoId}")
	public ProdutoDTO update(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody @Valid ProdutoInputDTO dto) {
		var produto = cadastroProdutoService.findById(produtoId, restauranteId);
		produtoDTODisassembler.copyProperties(dto, produto);
		return produtoDTOAssembler.toModel(cadastroProdutoService.update(produto));
	}

}





