package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
public class RestauranteDTOResumoList extends RepresentationModel<RestauranteDTOResumoList> {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Aloha")
	private String nome;
    
	@ApiModelProperty(example = "12.00")
    private BigDecimal taxaFrete;
    
    private CozinhaDTO cozinha;
}
