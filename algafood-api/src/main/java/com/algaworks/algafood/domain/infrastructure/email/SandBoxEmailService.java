package com.algaworks.algafood.domain.infrastructure.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.algaworks.algafood.core.email.EmailProperties;

/**
 * Caso queiramos verificar como está p layout do email 
 */
public class SandBoxEmailService extends SMTPEnvioEmailService {

	@Autowired
	private EmailProperties properties;
	
	@Override
	protected MimeMessage criarMimeMessage(Message message) throws MessagingException {
		var mimeMessage = super.criarMimeMessage(message);
		var mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
		mimeMessageHelper.setTo(properties.getSandBox().getDestinatario());
		return mimeMessage;
	}
}
