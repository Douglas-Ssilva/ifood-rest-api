package com.algaworks.algafood.domain.service;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

public interface EnvioEmailService {

	void send(Message message);
	
	@Getter
	@Builder
	class Message {
		@Singular
		private Set<String> destinatarios;
		
		@NonNull//lombok, ao fazer o build, lança exception 
		private String assunto;
		
		@NonNull
		private String body;
		
		@Singular
		private Map<String, Object> parameters;
	}
}
