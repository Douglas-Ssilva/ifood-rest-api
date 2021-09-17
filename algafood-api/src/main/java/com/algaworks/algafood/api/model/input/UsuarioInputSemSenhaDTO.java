package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInputSemSenhaDTO {

	@NotBlank
	@ApiModelProperty(example = "João", required = true)
	private String nome;

	@NotBlank
	@Email
	@ApiModelProperty(example = "joao@email.com", required = true)
	private String email;

}
