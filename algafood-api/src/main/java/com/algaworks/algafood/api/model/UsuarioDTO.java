package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
	
	@ApiModelProperty(example = "1")
	private Long id;
	@ApiModelProperty(example = "José")
	private String nome;
	@ApiModelProperty(example = "jose@email.com")
	private String email;
	

}
