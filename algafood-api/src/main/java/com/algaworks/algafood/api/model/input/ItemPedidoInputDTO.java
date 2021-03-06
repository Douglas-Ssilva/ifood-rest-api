package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInputDTO {
	
	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long produtoId;
	
	@ApiModelProperty(example = "10", required = true)
	@NotNull
	@PositiveOrZero
	private Integer quantidade;
	
	@ApiModelProperty(example = "Inserir muito molho shucunberry")
	private String observacao;

}
