package com.algaworks.algafood.api.model.mixin;

import com.algaworks.algafood.domain.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Mixin é uma classe que possui membros de uma classe original, e as config do Jakson pra nao misturarmos 
	anotações de API com a camada domain que ficam as entidades
 * @author dougl
 *
 */
@Deprecated(since = "Deixamos de usar por causa dos DTOs")
public abstract class CidadeMixin {
	
	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Estado estado;
	

}
