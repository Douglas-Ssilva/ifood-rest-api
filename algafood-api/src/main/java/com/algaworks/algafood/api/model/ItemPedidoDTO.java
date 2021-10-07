package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoDTO extends RepresentationModel<ItemPedidoDTO> {
	
	@ApiModelProperty(example = "1")
	private Long produtoId;
	
	@ApiModelProperty(example = "Peti gateau")
	private String produtoNome;
	
	@ApiModelProperty(example = "1")
	private Integer quantidade;
	
	@ApiModelProperty(example = "10.00")
	private BigDecimal precoUnitario;
	
	@ApiModelProperty(example = "10.00")
	private BigDecimal precoTotal;
	private String observacao;

}
