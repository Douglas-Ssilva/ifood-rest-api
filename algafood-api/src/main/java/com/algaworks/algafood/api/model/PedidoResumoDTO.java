package com.algaworks.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafood.domain.model.StatusPedido;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "pedidos")
@Getter
@Setter
public class PedidoResumoDTO extends RepresentationModel<PedidoResumoDTO> {
	
	@ApiModelProperty(example = "787c2598-574b-4db0-b611-948aeb0b5357")
	private String codigo;
	
	@ApiModelProperty(example = "20.99")
	private BigDecimal taxaFrete;
	
	@ApiModelProperty(example = "40.99")
	private BigDecimal subTotal;
	
	@ApiModelProperty(example = "90.99")
	private BigDecimal valorTotal;
	
	@ApiModelProperty(example = "2021-09-06T18:07:43.439Z")
	private OffsetDateTime dataCriacao;
	
	@ApiModelProperty(example = "CONFIRMADO")
	private StatusPedido status;
	
	private RestauranteDTOResumo restaurante;
	
	private UsuarioDTO cliente;
	
	@ApiModelProperty(example = "José")
	private String nomeCliente;
}
