package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(value = "Cidade")
@Getter
@Setter
public class CidadeDTO {
	
	@ApiModelProperty(example = "1")
	private Long id;
	@ApiModelProperty(example = "Belo Horizonte")
	private String nome;
	private EstadoDTO estado;

}
