package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.FotoProdutoNotFoundException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.FotoRecuperada;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository; //Agreggate root
	
	@Autowired
	private FotoStorageService fotoStorage;
	
	public FotoProduto findFotoProdutoByRestauranteEProduto(Long produtoId, Long restauranteId) {
		return produtoRepository.findFotoProduto(produtoId, restauranteId).orElseThrow(() -> new FotoProdutoNotFoundException(produtoId));
	}
	
	public FotoRecuperada buscarArquivo(String nomeArquivo) {
		return fotoStorage.buscarArquivo(nomeArquivo);
	}
	
	@Transactional
	public FotoProduto save(FotoProduto fotoProduto, InputStream inputStream) {
		var produto = fotoProduto.getProduto();
		var restaurante = produto.getRestaurante();
		var novoNomeArquivo = fotoStorage.novoNomeArquivo(fotoProduto.getNome());
		var nomeArquivoPersistido = "";
		
		
		Optional<FotoProduto> fotoProdutoOp = produtoRepository.findFotoProduto(produto.getId(), restaurante.getId());
		if (fotoProdutoOp.isPresent()) {
			nomeArquivoPersistido = fotoProdutoOp.get().getNome();
			produtoRepository.delete(fotoProdutoOp.get());
		}
		
		fotoProduto.setNome(novoNomeArquivo);
		fotoProduto = produtoRepository.save(fotoProduto);
		produtoRepository.flush();
		
		//Caso lance exception aqui, desfaz o que foi feito no banco
		var novaFoto = NovaFoto.builder()
				.nome(fotoProduto.getNome())
				.inputStream(inputStream)
				.contentType(fotoProduto.getContentType())
				.build();
		
		fotoStorage.substituir(nomeArquivoPersistido, novaFoto);
		
		return fotoProduto;
	}
	
	@Transactional
	public void removerArquivo(Long produtoId, Long restauranteId) {
		var fotoProduto = findFotoProdutoByRestauranteEProduto(produtoId, restauranteId);
		produtoRepository.delete(fotoProduto);
		produtoRepository.flush();
		fotoStorage.remover(fotoProduto.getNome());
	}

}
