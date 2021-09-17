package com.algaworks.algafood.domain.exception;

public class RestauranteNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public RestauranteNotFoundException(String message) {
		super(message);
	}
	
	public RestauranteNotFoundException(Long id) {
		this(String.format("Restaurante de id: %d não encontrado!", id));
	}

}
