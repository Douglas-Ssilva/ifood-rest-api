package com.algaworks.algafood.api.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import com.algaworks.algafood.domain.model.StatusPedido;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO {
	
	@ApiModelProperty(example = "787c2598-574b-4db0-b611-948aeb0b5357")
	private String codigo;
	@ApiModelProperty(example = "10.00")
	private BigDecimal taxaFrete;
	@ApiModelProperty(example = "20.00")
	private BigDecimal subTotal;
	@ApiModelProperty(example = "30.00")
	private BigDecimal valorTotal;
	@ApiModelProperty(example = "2021-09-06T18:24:44.329Z")
	private OffsetDateTime dataCriacao;
	@ApiModelProperty(example = "2021-09-06T18:24:44.329Z")
	private OffsetDateTime dataConfirmacao;
	@ApiModelProperty(example = "")
	private OffsetDateTime dataCancelamento;
	@ApiModelProperty(example = "2021-09-16T18:24:44.329Z")
	private OffsetDateTime dataEntrega;
	@ApiModelProperty(example = "PENDENTE")
	private StatusPedido status;
	private RestauranteResumoDTO restaurante;
	private UsuarioDTO cliente;
	private FormaPagamentoDTO formaPagamento;
	private EnderecoDTO enderecoEntrega;
	private List<ItemPedidoDTO> itens;
}
