package com.algaworks.algafood.api.controller;

import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.algaworks.algafood.api.assembler.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoInputDTODisassembler;
import com.algaworks.algafood.api.controller.openapi.FormaPagamentoControllerOpenApi;
import com.algaworks.algafood.api.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.model.input.FormaPagamentoInputDTO;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping(path = "/formasPagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@Autowired
	private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;
	
	@Autowired
	private FormaPagamentoInputDTODisassembler formaPagamentoInputDTODisassembler;

	private static final String VALUE_DEFAULT_IF_TABLE_EMPTY = "0";
	
	@Override
	@GetMapping
	public ResponseEntity<CollectionModel<FormaPagamentoDTO>> findAll(ServletWebRequest request){
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest()); //desabilitando o Shallow pra esse método
		var etag = VALUE_DEFAULT_IF_TABLE_EMPTY;
		var date = cadastroFormaPagamentoService.findLastUpdateDate();
		if (date.isPresent()) {
			etag = String.valueOf(date.get().toEpochSecond());
		}
		if (request.checkNotModified(etag)) {
			return null;
		}
		var dtos = formaPagamentoDTOAssembler.toCollectionModel(cadastroFormaPagamentoService.findBySituacao());
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(30, TimeUnit.SECONDS))
				.eTag(etag)
				.body(dtos);
	}
	
	@Override
	@GetMapping("/{formaPagamentoId}")
	public ResponseEntity<FormaPagamentoDTO> findById(@PathVariable Long formaPagamentoId, ServletWebRequest request) { 
		var etag = VALUE_DEFAULT_IF_TABLE_EMPTY;
		var date = cadastroFormaPagamentoService.findLastUpdateDate(formaPagamentoId);
		if(date.isPresent()) {
			etag = String.valueOf(date.get().toEpochSecond());
		}
		if (request.checkNotModified(etag)) {
			return null;
		}
		var dto = formaPagamentoDTOAssembler.toModel(cadastroFormaPagamentoService.findById(formaPagamentoId));
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(30, TimeUnit.SECONDS))
				.eTag(etag)
				.body(dto);
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDTO add(@RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInputDTO) {
		var formaPagamento = formaPagamentoInputDTODisassembler.toDomainObject(formaPagamentoInputDTO);
		return formaPagamentoDTOAssembler.toModel(cadastroFormaPagamentoService.merge(formaPagamento));
	}
	
	@Override
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long formaPagamentoId) {
		cadastroFormaPagamentoService.delete(formaPagamentoId);
	}
	
	@Override
	@PutMapping("/{formaPagamentoId}")
	public FormaPagamentoDTO update(@RequestBody @Valid FormaPagamentoInputDTO dto, @PathVariable Long formaPagamentoId) {
		var formaPagamento = cadastroFormaPagamentoService.findById(formaPagamentoId);
		formaPagamentoInputDTODisassembler.copyProperties(dto, formaPagamento);
		return formaPagamentoDTOAssembler.toModel(cadastroFormaPagamentoService.merge(formaPagamento));
	}
	

}
