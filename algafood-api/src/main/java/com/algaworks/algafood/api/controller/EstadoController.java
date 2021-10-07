package com.algaworks.algafood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.EstadoDTOAssembler;
import com.algaworks.algafood.api.assembler.EstadoInputDTODisassembler;
import com.algaworks.algafood.api.controller.openapi.EstadoControllerOpenApi;
import com.algaworks.algafood.api.model.EstadoDTO;
import com.algaworks.algafood.api.model.input.EstadoInputDTO;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping(path = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	@Autowired
	private EstadoDTOAssembler estadoDTOAssembler;
	
	@Autowired
	private EstadoInputDTODisassembler estadoInputDTODisassembler;
	
	@Override
	@GetMapping
	public CollectionModel<EstadoDTO> findAll() {
		return estadoDTOAssembler.toCollectionModel(estadoRepository.findAll());
	}
	
	@Override
	@GetMapping("/{estadoId}")
	public EstadoDTO findById(@PathVariable Long estadoId) {
		return estadoDTOAssembler.toModel(cadastroEstadoService.buscar(estadoId));
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO save(@RequestBody @Valid EstadoInputDTO estadoInputDTO) {
		var estado = estadoInputDTODisassembler.toDomainObject(estadoInputDTO);
		return estadoDTOAssembler.toModel(cadastroEstadoService.save(estado));
	}
	
	@Override
	@PutMapping("/{estadoId}")
	public EstadoDTO update(@PathVariable Long estadoId, @RequestBody @Valid EstadoInputDTO estadoInputDTO) {
		var estadoBD = cadastroEstadoService.buscar(estadoId);
		estadoInputDTODisassembler.copyProperties(estadoInputDTO, estadoBD);
		return estadoDTOAssembler.toModel(cadastroEstadoService.save(estadoBD));
	}
	
	@Override
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long estadoId){
		cadastroEstadoService.delete(estadoId);
	}

}
