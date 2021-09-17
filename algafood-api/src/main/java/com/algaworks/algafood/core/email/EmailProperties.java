package com.algaworks.algafood.core.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("algafood.email")
public class EmailProperties {

	@NotNull//caso Spring não consiga injetar, antes de a aplicação subir, será lancada excecao
	private String remetente;
	private SandBox sandBox = new SandBox();
	private EmailImpl impl = EmailImpl.FAKE;
	
	enum EmailImpl {
		FAKE, SMTP, SANDBOX
	}
	
	@Getter
	@Setter
	public class SandBox {
		private String destinatario;
	}
}
