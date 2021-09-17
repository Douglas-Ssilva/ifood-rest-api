package com.algaworks.algafood.domain.exception;

public class GrupoNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public GrupoNotFoundException(String message) {
		super(message);
	}
	
	public GrupoNotFoundException(Long id) {
		this(String.format("Grupo de id: %d não encontrado!", id));
	}

}
