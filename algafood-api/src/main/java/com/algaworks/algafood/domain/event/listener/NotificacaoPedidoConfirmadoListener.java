package com.algaworks.algafood.domain.event.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService.Message;

/**
 * 
	O problema é quando você não consegue garantir que esse evento será disparado dentro de uma transação.
	Nós garantimos isso, pois o método que realiza a confirmação / cancelamento são métodos transacionais.
	E como escutamos ao evento utilizando @TransactionalEventListener, esse evento respeitará as fases da transação.
 *
 */
@Component
public class NotificacaoPedidoConfirmadoListener {
	
	@Autowired
	private EnvioEmailService emailService;
	
	/**
	 * O default é ATFER_COMMIT, dessa forma, caso haja alguma exception aqui, a transação da confirmação do pedido já terá sido comitada e com isso não sendo possível
	 * desfazer a confirmação do pedido.
	 * Caso queiramos amarrar o envio do email com a confirmação do pedido, usamos BEFORE_COMMIT, dando alguma exception aqui, desfaz a confirmação do pedido.
	 * Com EventListener não temos controle da transação
	 */
	@TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
		var pedido = event.getPedido();
		var message = Message.builder()
				.assunto(pedido.getRestaurante().getNome() + " - " + "realizou a confirmação do pedido")
				.body("pedido-confirmado.html")
				.parameter("pedido", pedido)
				.destinatario(pedido.getCliente().getEmail())
				.build();
		emailService.send(message);
	}

}
