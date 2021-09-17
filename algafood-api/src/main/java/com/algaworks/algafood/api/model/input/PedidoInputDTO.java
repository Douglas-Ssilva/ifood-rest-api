package com.algaworks.algafood.api.model.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoInputDTO {
	
	@NotNull
	@Valid
	private RestauranteInputId restaurante;
	
	@NotNull
	@Valid
	private FormaPagamentoInputId formaPagamento;
	
	@NotNull
	@Valid
	private EnderecoInputDTO enderecoEntrega;
	
	@NotNull
	@Size(min = 1)
	@Valid
	private List<ItemPedidoInputDTO> itens;
	

}
