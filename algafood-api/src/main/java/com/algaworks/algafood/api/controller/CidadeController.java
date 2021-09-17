package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.assembler.CidadeDTOAssembler;
import com.algaworks.algafood.api.assembler.CidadeInputDTODisassembler;
import com.algaworks.algafood.api.controller.openapi.CidadeControllerOpenApi;
import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.api.model.input.CidadeInputDTO;
import com.algaworks.algafood.domain.exception.EstadoNotFoundException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CidadeDTOAssembler cidadeDTOAssembler;

	@Autowired
	private CidadeInputDTODisassembler cidadeDTODisassembler;
	
	
	@Override
	@GetMapping
	public List<CidadeDTO> findAll() {
		return cidadeDTOAssembler.toCollectionDTO(cidadeRepository.findAllJoinFetch());
	}
	
	@Override
	@GetMapping("/{cidadeId}")
	public CidadeDTO findById(@PathVariable Long cidadeId) {
		return cidadeDTOAssembler.toDTO(cadastroCidadeService.buscar(cidadeId));
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO save(@RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
		try {
			var cidade = cidadeDTODisassembler.toDomainObject(cidadeInputDTO);
			var dto = cidadeDTOAssembler.toDTO(cadastroCidadeService.save(cidade));
			ResourceUriHelper.addLocationInResponse(dto.getId());
			return dto;
		} catch (EstadoNotFoundException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@Override
	@PutMapping("/{cidadeId}")
	public CidadeDTO update(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInputDTO cidadeInputDTO){
		var cidadeBD = cadastroCidadeService.buscar(cidadeId);
		cidadeDTODisassembler.copyProperties(cidadeInputDTO, cidadeBD);
		try {
			return cidadeDTOAssembler.toDTO(cadastroCidadeService.save(cidadeBD));
		} catch (EstadoNotFoundException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@Override
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long cidadeId){
		cadastroCidadeService.delete(cidadeId);
	}
	
}
