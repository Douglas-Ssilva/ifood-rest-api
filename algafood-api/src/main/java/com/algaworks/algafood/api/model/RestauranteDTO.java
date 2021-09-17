package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@JsonFilter("restauranteFilter")
@Getter
@Setter
public class RestauranteDTO {

	@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNomeEId.class})
	@ApiModelProperty(example = "1")
	private Long id;
	
	@JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNomeEId.class})
	@ApiModelProperty(example = "Brasucas")
	private String nome;
	
	@JsonView(RestauranteView.Resumo.class)
	@ApiModelProperty(example = "true")
	private Boolean ativo;
	
	@JsonView(RestauranteView.Resumo.class)
	private CozinhaDTO cozinha;
	
	@ApiModelProperty(example = "true")
	private Boolean aberto;
	
	@ApiModelProperty(example = "10.00")
	private BigDecimal frete;
	
	private EnderecoDTO endereco;
	
	//private String cozinhaNome; //modelMapper consegue encontrar, caminha pelos atributos
	//private String cozinhaDescription; retorna null pois nao encontrou correspondencia
	
}
