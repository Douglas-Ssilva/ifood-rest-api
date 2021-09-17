package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;
import lombok.Getter;

/**
 * Separando responsabilidades das classes. Assim a CatalogoFotoProdutoService apenas interagirá com FotoStorageService
 * @author dougl
 *
 */
public interface FotoStorageService {

	void armazenar(NovaFoto novaFoto);
	
	void remover(String nomeFotoPersistido);
	
	FotoRecuperada buscarArquivo(String nomeArquivo);
	
	default void substituir(String nomeFotoPersistido, NovaFoto novaFoto) {
		this.remover(nomeFotoPersistido);
		this.armazenar(novaFoto);
	}
	
	default String novoNomeArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString() + "__" + nomeOriginal;
	}
	
	/**
	 * Não estamos recebendo o Multipart aqui pois é uma classe do pacote web e ficaríamos muito acoplado 
	 *
	 */
	@Builder
	@Getter
	class NovaFoto {
		
		private InputStream inputStream; //pegando fluxo de leitura do file
		private String nome;
		private String contentType;
		
	}
	
	@Builder
	@Getter
	class FotoRecuperada {
		private InputStream inputStream;
		private String url;
		
		public boolean existeUrl() {
			return StringUtils.isNoneBlank(this.url);
		}
	}
}
