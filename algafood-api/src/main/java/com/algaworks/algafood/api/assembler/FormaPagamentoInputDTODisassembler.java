package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.FormaPagamentoInputDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoInputDTODisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamento toDomainObject(FormaPagamentoInputDTO dto) {
		return modelMapper.map(dto, FormaPagamento.class);
	}

	public void copyProperties(FormaPagamentoInputDTO dto, FormaPagamento formaPagamento) {
		modelMapper.map(dto, formaPagamento);
	}

}
