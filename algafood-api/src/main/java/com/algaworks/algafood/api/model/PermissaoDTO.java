package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissaoDTO {
	
	@ApiModelProperty(example = "1")
	private Long id;
	@ApiModelProperty(example = "Edição")
	private String nome;
	@ApiModelProperty(example = "Permite editar recursos")
	private String descricao;

}
