package com.algaworks.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cozinhas")
@Getter
@Setter
public class CozinhaDTO extends RepresentationModel<CozinhaDTO> {
	
//	@JsonView(RestauranteView.Resumo.class)
	@ApiModelProperty(example = "1")
	private Long id;
	
//	@JsonView(RestauranteView.Resumo.class)
	@ApiModelProperty(example = "Brasileira")
	private String nome;
	
}
