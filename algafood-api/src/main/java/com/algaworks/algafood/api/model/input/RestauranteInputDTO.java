package com.algaworks.algafood.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteInputDTO {

	@NotBlank
	@ApiModelProperty(example = "Brasucic", required = true)
	private String nome;
	
	@NotNull
	@PositiveOrZero
	@ApiModelProperty(example = "10.00", required = true)
	private BigDecimal taxaFrete;
	
	@Valid
	@NotNull
	private CozinhaInputId cozinha;
	
	@Valid
	@NotNull
	private EnderecoInputDTO endereco;
	
}
