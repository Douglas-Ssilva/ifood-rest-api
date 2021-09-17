package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoDTO {
	
	@ApiModelProperty(example = "carteira-trabalho.png")
	private String nome;
	@ApiModelProperty(example = "Carteira de trabalho")
	private String descricao;
	@ApiModelProperty(example = "image/png")
	private String contentType;
	@ApiModelProperty(example = "45KB")
	private Long length;

}
