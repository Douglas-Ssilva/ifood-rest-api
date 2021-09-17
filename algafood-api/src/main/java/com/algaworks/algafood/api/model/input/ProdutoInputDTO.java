package com.algaworks.algafood.api.model.input;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoInputDTO {

	@NotBlank
	@ApiModelProperty(example = "Picanha", required = true)
	private String nome;

	@NotBlank
	@ApiModelProperty(example = "Picanha Argentina", required = true)
	private String descricao;

	@NotNull
	@PositiveOrZero
	@ApiModelProperty(example = "100.0", required = true)
	private BigDecimal preco;

	@NotNull
	@ApiModelProperty(example = "true", required = true)
	private Boolean ativo;

}
