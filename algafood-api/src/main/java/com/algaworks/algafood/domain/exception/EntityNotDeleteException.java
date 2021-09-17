package com.algaworks.algafood.domain.exception;

//@ResponseStatus(HttpStatus.CONFLICT)
public class EntityNotDeleteException extends NegocioException {

	private static final long serialVersionUID = 1L;
	
	public EntityNotDeleteException(String message) {
		super(message);
	}

}
