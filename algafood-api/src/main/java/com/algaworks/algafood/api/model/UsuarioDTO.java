package com.algaworks.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "usuarios")
@Getter
@Setter
public class UsuarioDTO extends RepresentationModel<UsuarioDTO>{
	
	@ApiModelProperty(example = "1")
	private Long id;
	@ApiModelProperty(example = "José")
	private String nome;
	@ApiModelProperty(example = "jose@email.com")
	private String email;
	

}
