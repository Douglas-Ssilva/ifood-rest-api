package com.algaworks.algafood.domain.event.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService.Message;

@Component
public class NotificadorPedidoCanceladoListener {
	
	@Autowired
	private EnvioEmailService envioEmailService;
	
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void notificarCancelamentoPedido(PedidoCanceladoEvent event) {
		var pedido = event.getPedido();
		var message = Message.builder()
				.assunto("Pedido cancelado")
				.body("pedido-cancelado.html")
				.destinatario(pedido.getCliente().getEmail())
				.parameter("pedido", pedido)
				.build();
		envioEmailService.send(message);
	}

}
