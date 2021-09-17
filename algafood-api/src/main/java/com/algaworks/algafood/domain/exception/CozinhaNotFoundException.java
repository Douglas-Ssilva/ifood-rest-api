package com.algaworks.algafood.domain.exception;

public class CozinhaNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public CozinhaNotFoundException(String message) {
		super(message);
	}
	
	public CozinhaNotFoundException(Long id) {
		this(String.format("Cozinha de id: %d não encontrada!", id));
	}

}
