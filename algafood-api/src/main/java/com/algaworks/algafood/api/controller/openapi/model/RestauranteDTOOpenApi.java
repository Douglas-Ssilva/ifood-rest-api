package com.algaworks.algafood.api.controller.openapi.model;

import com.algaworks.algafood.api.model.CozinhaDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Criado para fins apenas de doc, devido ao JsonFilter 
 *
 */
@ApiModel("RestauranteDTOOpenApi")
@Getter
@Setter
public class RestauranteDTOOpenApi {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Thai Goumert")
	private String nome;
	
	@ApiModelProperty(example = "true")
	private Boolean ativo;
	
	private CozinhaDTO cozinha;
}
