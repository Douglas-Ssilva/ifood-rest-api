package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInputUpdateSenhaAntigaENovaDTO {
	
	@NotBlank
	@Length(min = 3)
	@ApiModelProperty(example = "123", required = true)
	private String senhaAtual;
	
	@NotBlank
	@Length(min = 3)
	@ApiModelProperty(example = "321", required = true)
	private String senhaNova;

}
