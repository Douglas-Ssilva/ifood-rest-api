package com.algaworks.algafood.domain.service;

import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;

@Service
public class FluxoStatusPedidoService {
	
	@Autowired
	private CadastroPedidoService cadastroPedidoService;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Transactional
	public void confirmar(String codigoPedido) {
		var pedido = cadastroPedidoService.findByCodigo(codigoPedido);
		validarRegras(Pedido::naoPodeSerConfirmado, pedido, StatusPedido.CONFIRMADO);
		pedido.confirmarPedido();
		
		pedidoRepository.save(pedido);//temos que chamar o save de um repositório do Spring data para que os eventos que estão na fila, no após o flush, serem disparados
	}

	@Transactional
	public void entregar(String codigoPedido) {
		var pedido = cadastroPedidoService.findByCodigo(codigoPedido);
		validarRegras(Pedido::naoPodeSerEntregue, pedido, StatusPedido.ENTREGUE);
		pedido.entregarPedido();
	}

	@Transactional
	public void cancelar(String codigoPedido) {
		var pedido = cadastroPedidoService.findByCodigo(codigoPedido);
		validarRegras(Pedido::naoPodeSerCancelado, pedido, StatusPedido.CANCELADO);
		pedido.cancelarPedido();
		
		pedidoRepository.save(pedido);
	}
	
	public void validarRegras(Predicate<Pedido> predicate, Pedido pedido, StatusPedido acaoUser) {
		if(predicate.test(pedido)) {
			throw new NegocioException(String.format("O pedido de código: %s não pode ser %s, pois sua situação é: %s.", pedido.getCodigo(), acaoUser.getDescricao(), pedido.getStatus().getDescricao()));
		}
	}

}
