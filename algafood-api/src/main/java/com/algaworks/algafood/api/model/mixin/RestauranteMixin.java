package com.algaworks.algafood.api.model.mixin;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Mixin é uma classe que possui membros de uma classe original, e as config do Jakson pra nao misturarmos 
	anotações de API com a camada domain que ficam as entidades
 * @author dougl
 *
 */

@Deprecated(since = "Deixamos de usar por causa dos DTOs")
public abstract class RestauranteMixin {
	
	@JsonIgnoreProperties(value = "nome", allowGetters = true) //na atualização do Restaurante, caso o cliente passe nome para atualizar, mostro erro falando que nome não existe.
//	@JsonIgnoreProperties({"hibernateLazyInitializer"})//ignorando a propriedade do proxy que o hibernate cria, pra não dar exception ao chamar. Devo usar apenas se esse cara for lazy
	private Cozinha cozinha;
	
	@JsonIgnore
	private List<FormaPagamento> formasPagamento= new ArrayList<>();
	
	@JsonIgnore
	private Endereco endereco;
	
	@JsonIgnore
	private OffsetDateTime dataCadastro;
	
	@JsonIgnore
	private OffsetDateTime dataAtualizacao;
	
	@JsonIgnore
	private List<Produto> produtos= new ArrayList<>();
	

}
