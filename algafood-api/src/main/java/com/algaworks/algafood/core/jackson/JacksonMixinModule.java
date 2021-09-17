package com.algaworks.algafood.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Responsável por vincular a classe Mixin a de domínio
 * Pacote core - colocamos coisas que não estão muito ligadas com outros pacotes exemplo: domain e  api.
	Ou ainda algo que é compartilhado por várias camadas
 */
@Component
@Deprecated
public class JacksonMixinModule extends SimpleModule {

	private static final long serialVersionUID = 1L;
	
	public JacksonMixinModule() {
//		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
//		setMixInAnnotation(Cidade.class, CidadeMixin.class);
//		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
	}

}
