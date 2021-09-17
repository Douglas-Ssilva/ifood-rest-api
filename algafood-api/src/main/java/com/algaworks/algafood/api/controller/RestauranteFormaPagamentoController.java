package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.controller.openapi.RestauranteFormaPagamentoControllerOpenApi;
import com.algaworks.algafood.api.model.FormaPagamentoDTO;
import com.algaworks.algafood.domain.exception.FormaPagamentoNotFoundException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;

	@Autowired
	private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler; 
	
	@Override
	@GetMapping
	public List<FormaPagamentoDTO> findAll(@PathVariable Long restauranteId) {
		var restaurante = cadastroRestauranteService.buscar(restauranteId);
		return formaPagamentoDTOAssembler.toCollectionDTO(restaurante.getFormasPagamento());
	}
	
	@Override
	@GetMapping("/{formaPagamentoId}")
	public FormaPagamentoDTO findById(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		var restaurante = cadastroRestauranteService.buscar(restauranteId);
		Optional<FormaPagamento> formaPagamento = restaurante.getFormasPagamento().stream().filter(fp -> fp.getId().equals(formaPagamentoId)).findFirst();
		if (formaPagamento.isEmpty()) {
			throw new FormaPagamentoNotFoundException(formaPagamentoId);
		}
		return formaPagamentoDTOAssembler.toDTO(formaPagamento.get());
	}
	
	@Override
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT) 
	public void desassiociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		cadastroRestauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
	}
	
	@Override
	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT) 
	public void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		cadastroRestauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
	}

	
}





