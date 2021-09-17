package com.algaworks.algafood.domain.exception;

public class EstadoNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public EstadoNotFoundException(String message) {
		super(message);
	}
	
	public EstadoNotFoundException(Long id) {
		this(String.format("Estado de id: %d não encontrado!", id));
	}

}
