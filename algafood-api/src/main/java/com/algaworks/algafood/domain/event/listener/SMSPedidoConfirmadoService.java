package com.algaworks.algafood.domain.event.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SMSPedidoConfirmadoService {
	
	@EventListener
	public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
		log.info(Thread.currentThread().getName() + " enviando SMS");
	}

}
