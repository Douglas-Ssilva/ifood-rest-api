package com.algaworks.algafood.domain.exception;

public class PedidoNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public PedidoNotFoundException(String codigo) {
		super(String.format("Pedido de código: %s não encontrado!", codigo));;
	}
	
}
