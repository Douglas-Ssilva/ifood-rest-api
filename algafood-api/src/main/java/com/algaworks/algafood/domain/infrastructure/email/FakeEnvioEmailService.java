package com.algaworks.algafood.domain.infrastructure.email;

import lombok.extern.slf4j.Slf4j;
/**
 * Para os casos nos quais copiamos a base de produção para desenv e qeremos realizar alguns testes
 */
@Slf4j
public class FakeEnvioEmailService extends SMTPEnvioEmailService {

	@Override
	public void send(Message message) {
		var templateEmail = buscarTemplateEmail(message);
		log.info("[FAKE e-mail] para: {}\n{}", message.getDestinatarios(), templateEmail);
	}

}
