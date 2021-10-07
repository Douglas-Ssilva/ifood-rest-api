package com.algaworks.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * A fim de melhorarmos a visualização na representação do objeto. Removendo o aninhamento com Estado
 * @author dougl
 *
 */
@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeResumoDTO extends RepresentationModel<CidadeResumoDTO>{

	@ApiModelProperty(example = "1")
	private Long id;
	@ApiModelProperty(example = "Belo Horizonte")
	private String nome;
	@ApiModelProperty(example = "Minas Gerais")
	private String estado;
}
