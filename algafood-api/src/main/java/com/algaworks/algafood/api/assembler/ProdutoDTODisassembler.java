package com.algaworks.algafood.api.assembler;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.ProdutoInputDTO;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoDTODisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Produto toDomainObject(ProdutoInputDTO dto) {
		return modelMapper.map(dto, Produto.class);
	}

	public void copyProperties(@Valid ProdutoInputDTO dto, Produto produto) {
		modelMapper.map(dto, produto);
	}
	
}
