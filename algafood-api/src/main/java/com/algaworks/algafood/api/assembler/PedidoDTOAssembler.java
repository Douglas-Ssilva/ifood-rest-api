package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.model.PedidoDTO;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO>{
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public PedidoDTOAssembler() {
		super(PedidoController.class, PedidoDTO.class);
	}
	
	@Override
	public PedidoDTO toModel(Pedido pedido) {
		var pedidoDTO = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoDTO);
		pedidoDTO.add(algaLinks.linkToPedidos("pedidos"));
		pedidoDTO.getRestaurante().add(algaLinks.linkToRestaurante(pedidoDTO.getRestaurante().getId()));
		pedidoDTO.getCliente().add(algaLinks.linkToCliente(pedidoDTO.getCliente().getId()));
		pedidoDTO.getFormaPagamento().add(algaLinks.linkToFormaPagamento(pedidoDTO.getFormaPagamento().getId()));
		pedidoDTO.getEnderecoEntrega().getCidade().add(algaLinks.linkToCidade(pedidoDTO.getEnderecoEntrega().getCidade().getId()));
		pedidoDTO.getItens().forEach(item -> item.add(algaLinks.linkToProduto(pedidoDTO.getRestaurante().getId(), item.getProdutoId(), "produto")));
		
		if (pedido.podeSerConfirmado()) {
			pedidoDTO.add(algaLinks.linkToConfirmacaoPedido(pedidoDTO.getCodigo()));
		}
		if (pedido.podeSerCancelado()) {
			pedidoDTO.add(algaLinks.linkToCancelarPedido(pedidoDTO.getCodigo()));
		}
		if (pedido.podeSerEntregue()) {
			pedidoDTO.add(algaLinks.linkToEntregarPedido(pedidoDTO.getCodigo()));
		}
		return pedidoDTO;
	}
	
}
