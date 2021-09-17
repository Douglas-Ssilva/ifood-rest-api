package com.algaworks.algafood.core.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.core.email.EmailProperties.EmailImpl;
import com.algaworks.algafood.domain.infrastructure.email.FakeEnvioEmailService;
import com.algaworks.algafood.domain.infrastructure.email.SMTPEnvioEmailService;
import com.algaworks.algafood.domain.infrastructure.email.SandBoxEmailService;
import com.algaworks.algafood.domain.service.EnvioEmailService;

@Configuration
public class EmailConfig {
	
	@Autowired
	private EmailProperties properties;
	
	@Bean
	public EnvioEmailService envioEmailService() {
		if(properties.getImpl().equals(EmailImpl.SMTP)) {
			return new SMTPEnvioEmailService();
		} else if(properties.getImpl().equals(EmailImpl.FAKE)) {
			return new FakeEnvioEmailService();
		} else {
			return new SandBoxEmailService();
		}
	}

}
