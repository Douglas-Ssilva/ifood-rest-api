package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDTO {
	
	@ApiModelProperty(example = "33944-111")
	private String cep;
	
	@ApiModelProperty(example = "Rua dos algarves")
	private String logradouro;
	private String complemento;
	
	@ApiModelProperty(example = "777")
	private String numero;
	
	@ApiModelProperty(example = "Maria da glória")
	private String bairro;
	private CidadeResumoDTO cidade;

}
