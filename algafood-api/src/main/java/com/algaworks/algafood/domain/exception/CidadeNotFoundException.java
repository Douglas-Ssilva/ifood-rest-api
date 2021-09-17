package com.algaworks.algafood.domain.exception;

public class CidadeNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public CidadeNotFoundException(String message) {
		super(message);
	}
	
	public CidadeNotFoundException(Long id) {
		this(String.format("Cidade de id: %d não encontrada!", id));
	}

}
