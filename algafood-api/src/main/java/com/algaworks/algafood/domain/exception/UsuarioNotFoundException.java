package com.algaworks.algafood.domain.exception;

public class UsuarioNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public UsuarioNotFoundException(String message) {
		super(message);
	}
	
	public UsuarioNotFoundException(Long id) {
		this(String.format("Usuário de id: %d não encontrado!", id));
	}

}
