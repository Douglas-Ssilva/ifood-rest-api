package com.algaworks.algafood.domain.infrastructure.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;

import freemarker.template.Configuration;

//@Service
public class SMTPEnvioEmailService implements EnvioEmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Autowired
	private Configuration freeMarkerTemplate;
	
	
	@Override
	public void send(Message message) {
		try {
			var mimeMessage = criarMimeMessage(message);
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			throw new EmailException("Não foi possível enviar o email.", e);
		}
	}

	protected MimeMessage criarMimeMessage(Message message) throws MessagingException {
		var mimeMessage = mailSender.createMimeMessage();
		var templateEmail = buscarTemplateEmail(message);
		
		var mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");//facilita para criarmos o MimeMessage
		mimeMessageHelper.setFrom(emailProperties.getRemetente());
		mimeMessageHelper.setSubject(message.getAssunto());
		mimeMessageHelper.setText(templateEmail, true);
		mimeMessageHelper.setTo(message.getDestinatarios().toArray(new String[0]));
		return mimeMessage;
	}
	
	public String buscarTemplateEmail(Message message) {
		try {
			var template = freeMarkerTemplate.getTemplate(message.getBody());
			return FreeMarkerTemplateUtils.processTemplateIntoString(template , message.getParameters());
		} catch (Exception e) {
			throw new EmailException("Não foi possível gerar o template do email.", e);
		}
	}

}
