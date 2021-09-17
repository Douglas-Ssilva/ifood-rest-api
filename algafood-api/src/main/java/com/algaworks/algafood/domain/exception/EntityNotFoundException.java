package com.algaworks.algafood.domain.exception;

//@ResponseStatus(HttpStatus.NOT_FOUND)
//public class EntityNotFoundException extends ResponseStatusException {
	public abstract class EntityNotFoundException extends NegocioException {

	private static final long serialVersionUID = 1L;
//	
//	public EntityNotFoundException(HttpStatus httpStatus, String message) {
//		super(httpStatus, message);
//	}
	
	public EntityNotFoundException(String message) {
		super(message);
	}

}
