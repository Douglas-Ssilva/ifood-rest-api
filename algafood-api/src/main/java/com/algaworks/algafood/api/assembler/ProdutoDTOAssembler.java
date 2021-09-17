package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.ProdutoDTO;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoDTOAssembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public ProdutoDTO toDTO(Produto p) {
		return mapper.map(p, ProdutoDTO.class);
	}
	
	public List<ProdutoDTO> toCollectionDTO(List<Produto> produtos) {
		return produtos.stream().map(this::toDTO).collect(Collectors.toList());
	}

}
