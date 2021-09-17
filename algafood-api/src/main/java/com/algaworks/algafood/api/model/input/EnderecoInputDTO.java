package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInputDTO {

	@ApiModelProperty(example = "33944-111", required = true)
	@NotBlank
	private String cep;
	
	@ApiModelProperty(example = "Rua dos algarves", required = true)
	@NotBlank
	private String logradouro;

	@ApiModelProperty(example = "\"777\"", required = true)
	@NotBlank
	private String numero;
	
	private String complemento;

	@ApiModelProperty(example = "Maria da glória", required = true)
	@NotBlank
	private String bairro;
	
	@Valid
	@NotNull
	private CidadeInputId cidade;

}
