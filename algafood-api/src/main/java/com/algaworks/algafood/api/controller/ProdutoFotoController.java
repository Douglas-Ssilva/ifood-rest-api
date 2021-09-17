package com.algaworks.algafood.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.assembler.FotoProdutoDTOAssembler;
import com.algaworks.algafood.api.controller.openapi.ProdutoFotoControllerOpenApi;
import com.algaworks.algafood.api.model.FotoProdutoDTO;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;

@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto", produces =  MediaType.APPLICATION_JSON_VALUE)
public class ProdutoFotoController implements ProdutoFotoControllerOpenApi {
	
	@Autowired
	private CatalogoFotoProdutoService fotoService;
	
	@Autowired
	private CadastroProdutoService produtoService;
	
	@Autowired
	private FotoProdutoDTOAssembler fotoProdutoDTOAssembler; 
	
	/**
	 * Content-type=multipart/form-data; boundary=0, tive que forçar isso no postman para não dar o status 415
	 * Tive que fazer esse work around pois o swagger não enviada na request o contentType do arquivo, gerando assim uma exceção na aplicação 
	 */
	@Override
	@PutMapping
	public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoInput produtoInput, 
			@RequestParam(required = true) MultipartFile arquivo) throws IOException {
		
		var produto = produtoService.findById(produtoId, restauranteId);
		var file = produtoInput.getArquivo();
		
		var fotoProduto = new FotoProduto();
		fotoProduto.setContentType(file.getContentType());
		fotoProduto.setDescricao(produtoInput.getDescricao());
		fotoProduto.setLength(file.getSize());
		fotoProduto.setNome(file.getOriginalFilename());
		fotoProduto.setProduto(produto);
		return fotoProdutoDTOAssembler.toDTO(fotoService.save(fotoProduto, produtoInput.getArquivo().getInputStream()));
	}
	
	@Override
	@GetMapping
	public FotoProdutoDTO buscarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		var fotoProduto = fotoService.findFotoProdutoByRestauranteEProduto(produtoId, restauranteId);
		return fotoProdutoDTOAssembler.toDTO(fotoProduto);
	}
	
	/**
	 * Devido ao Accept ser image/png no cliente, quando um produto não existe, é lançada uma mensagem de ProdutoNotFound, 
	 * o exceptionHandler faz todo o tratamento e retorna um json com isso temos um problema pois a request foi feita aceitando um image/png
	 * por isso o tratamento abaixo
	 */
	@Override
	@GetMapping(produces = MediaType.ALL_VALUE)
	public ResponseEntity<?> buscarFotoInputStream(@PathVariable Long restauranteId, @PathVariable Long produtoId, 
			@RequestHeader(name = "accept") String accept) throws HttpMediaTypeNotAcceptableException {
		try {
			var fotoProduto = fotoService.findFotoProdutoByRestauranteEProduto(produtoId, restauranteId);
			
			var fotoRecuperada = fotoService.buscarArquivo(fotoProduto.getNome());
			var mediaType = MediaType.parseMediaType(fotoProduto.getContentType());
			var mediaTypesAcceptRequest = MediaType.parseMediaTypes(accept);
			verificarMediaTypes(mediaType, mediaTypesAcceptRequest);
			
			if (fotoRecuperada.existeUrl()) {
				return ResponseEntity
					.status(HttpStatus.FOUND)
					.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
					.build();
			} else {
				return ResponseEntity
						.ok()
						.contentType(mediaType)
						.body(new InputStreamResource(fotoRecuperada.getInputStream()));
			}
		} catch (EntityNotFoundException e) {
			//assim não deixo o exceptionHandler tratar a exception (HttpMediaTypeNotAcceptableException: Could not find acceptable representation])
			return ResponseEntity.notFound().build();
		}
	}

	@Override
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		fotoService.removerArquivo(produtoId, restauranteId);
	}
	
	/**
	 *	Foi usado isCompatibleWith caso o cliente envie: image/*
	 */
	private void verificarMediaTypes(MediaType mediaType, List<MediaType> mediaTypesAcceptRequest) throws HttpMediaTypeNotAcceptableException {
		boolean match = mediaTypesAcceptRequest.stream().anyMatch(media -> media.isCompatibleWith(mediaType));
		if (!match) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAcceptRequest);
		}
	}

}
