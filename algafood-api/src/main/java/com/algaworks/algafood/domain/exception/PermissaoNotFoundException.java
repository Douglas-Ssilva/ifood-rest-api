package com.algaworks.algafood.domain.exception;

public class PermissaoNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public PermissaoNotFoundException(String message) {
		super(message);
	}
	
	public PermissaoNotFoundException(Long id) {
		this(String.format("Permissão de id: %d não encontrada!", id));
	}

}
