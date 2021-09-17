package com.algaworks.algafood.domain.exception;

public class ProdutoNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public ProdutoNotFoundException(String message) {
		super(message);
	}
	
	public ProdutoNotFoundException(Long id) {
		this(String.format("Produto de id: %d não encontrado!", id));
	}

}
