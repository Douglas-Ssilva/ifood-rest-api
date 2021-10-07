package com.algaworks.algafood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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

import com.algaworks.algafood.api.assembler.CozinhaDTOAssembler;
import com.algaworks.algafood.api.assembler.CozinhaInputDTODisassembler;
import com.algaworks.algafood.api.controller.openapi.CozinhaControllerOpenApi;
import com.algaworks.algafood.api.model.CozinhaDTO;
import com.algaworks.algafood.api.model.input.CozinhaInputDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(path = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired 
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	private CozinhaDTOAssembler cozinhaDTOAssembler;
	
	@Autowired
	private CozinhaInputDTODisassembler cozinhaInputDTODisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;
	
	@Override
	@GetMapping//desenvolvimento:8080/cozinhas?size=3&page=0&sort=nome&sort=id
	public PagedModel<CozinhaDTO> findAll(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
		PagedModel<CozinhaDTO> cozinhasPagedModel = pagedResourcesAssembler.toModel(cozinhasPage, cozinhaDTOAssembler);
		return cozinhasPagedModel;
	}
	
	@Override
	@GetMapping("/por-nome")
	public PagedModel<CozinhaDTO> listarCozinhasPorNome(String nome, Pageable pageable) {
		Page<Cozinha> cozinhasPage = cozinhaRepository.findByNomeContaining(nome, pageable);
		PagedModel<CozinhaDTO> cozinhasPagedModel = pagedResourcesAssembler.toModel(cozinhasPage, cozinhaDTOAssembler);
		return cozinhasPagedModel;
	}
	
	@Override
	@GetMapping("/{cozinhaId}")
	public CozinhaDTO findById(@PathVariable Long cozinhaId){
		return cozinhaDTOAssembler.toModel(cadastroCozinhaService.buscar(cozinhaId));
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO add(@RequestBody @Valid CozinhaInputDTO cozinhaInputDTO) {
		var cozinha = cozinhaInputDTODisassembler.toDomainObject(cozinhaInputDTO);
		return cozinhaDTOAssembler.toModel(cadastroCozinhaService.save(cozinha));
	}
	
	@Override
	@PutMapping("/{cozinhaId}")
	public CozinhaDTO update(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInputDTO cozinhaInputDTO) {
		var cozinhaBD = cadastroCozinhaService.buscar(cozinhaId);
		cozinhaInputDTODisassembler.copyProperties(cozinhaInputDTO, cozinhaBD);
		return cozinhaDTOAssembler.toModel(cadastroCozinhaService.save(cozinhaBD));
	}
	
	@Override
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)//em caso de sucesso
	public void delete(@PathVariable Long cozinhaId) {
		cadastroCozinhaService.delete(cozinhaId);
	}

//	@PutMapping("/{id}")
//	public ResponseEntity<Cozinha> update(@PathVariable Long id, @RequestBody Cozinha cozinha) {
//		Optional<Cozinha> cozinhaOptional = cozinhaRepository.findById(id);
//		if (cozinhaOptional.isPresent()) {
//			Cozinha cozinhaBD = cozinhaOptional.get();
//			BeanUtils.copyProperties(cozinha, cozinhaBD, "id");//ignore a propriedade id
//			cozinhaBD = cadastroCozinhaService.save(cozinhaBD);
//			return ResponseEntity.ok(cozinhaBD);
//		}
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//	}
	
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Cozinha> delete(@PathVariable Long id) {
//		try {
//			cadastroCozinhaService.delete(id);
//			return ResponseEntity.noContent().build();
//		} catch (EntityNotDeleteException ex) {
//			return ResponseEntity.status(HttpStatus.CONFLICT).build();
//		} catch (EntityNotFoundException ex) {
//			return ResponseEntity.notFound().build();
//		}
//	}

//	@ApiOperation("Lista as cozinhas XML")
//	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
//	public CozinhasXMLWrapper listarXML() {
//		return new CozinhasXMLWrapper(cozinhaRepository.findAll());
//	}
	
//	@GetMapping("/{cozinhaId}")
//	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId){
//		Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);
//		if (cozinha.isPresent()) {
//			return ResponseEntity.ok(cozinha.get());
//		}
//		return ResponseEntity.notFound().build();
//	}
}
