package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.ProdutoNotFoundException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Transactional
	public Produto update(Produto produto) {
		return produto;
	}
	
	public Produto findById(Long produtoId, Long restauranteId) {
		return produtoRepository.findByIdAndRestauranteId(produtoId, restauranteId)
				.orElseThrow(() -> new ProdutoNotFoundException(String.format("Produto de id: %d não associado ao restaurante: %d", produtoId, restauranteId)));
	}

	public Produto findById(Long produtoId) {
		return produtoRepository.findById(produtoId).orElseThrow(() -> new ProdutoNotFoundException(produtoId)); 
	}

	public List<Produto> findByProdutosAtivos(Restaurante restaurante) {
		return produtoRepository.findByProdutosAtivos(restaurante);
	}
	
}
