package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.PedidoInputDTO;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoInputDTODisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Pedido toDomainObject(PedidoInputDTO dto) {
		return modelMapper.map(dto, Pedido.class);
	}

	
}
