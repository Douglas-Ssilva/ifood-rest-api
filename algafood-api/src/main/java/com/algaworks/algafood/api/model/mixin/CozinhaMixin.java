package com.algaworks.algafood.api.model.mixin;

import java.util.ArrayList;
import java.util.List;

import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Mixin é uma classe que possui membros de uma classe original, e as config do Jakson pra nao misturarmos 
	anotações de API com a camada domain que ficam as entidades
 * @author dougl
 *
 */
@Deprecated(since = "Deixamos de usar por causa dos DTOs")
public abstract class CozinhaMixin {
	
	@JsonIgnore
	private List<Restaurante> restaurantes = new ArrayList<>();  
	

}
