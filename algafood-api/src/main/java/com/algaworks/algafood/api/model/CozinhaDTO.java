package com.algaworks.algafood.api.model;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaDTO {
	
	@JsonView(RestauranteView.Resumo.class)
	@ApiModelProperty(example = "1")
	private Long id;
	
	@JsonView(RestauranteView.Resumo.class)
	@ApiModelProperty(example = "Brasileira")
	private String nome;
	
}
