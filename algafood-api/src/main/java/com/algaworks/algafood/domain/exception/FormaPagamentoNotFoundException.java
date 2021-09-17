package com.algaworks.algafood.domain.exception;

public class FormaPagamentoNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public FormaPagamentoNotFoundException(String message) {
		super(message);
	}
	
	public FormaPagamentoNotFoundException(Long id) {
		this(String.format("Forma de pagamento id: %d não encontrada!", id));
	}

}
