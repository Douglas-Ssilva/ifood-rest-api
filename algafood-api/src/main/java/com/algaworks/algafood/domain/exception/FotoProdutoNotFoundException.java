package com.algaworks.algafood.domain.exception;

public class FotoProdutoNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public FotoProdutoNotFoundException(String message) {
		super(message);
	}
	
	public FotoProdutoNotFoundException(Long id) {
		this(String.format("Foto do produto de id: %d não encontrada!", id));
	}

}
