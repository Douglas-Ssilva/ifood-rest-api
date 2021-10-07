package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.ProdutoFotoController;
import com.algaworks.algafood.api.model.FotoProdutoDTO;
import com.algaworks.algafood.domain.model.FotoProduto;

@Component
public class FotoProdutoDTOAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoDTO>{
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public FotoProdutoDTOAssembler() {
		super(ProdutoFotoController.class, FotoProdutoDTO.class);
	}
	
	@Override
	public FotoProdutoDTO toModel(FotoProduto foto) {
		var dto = modelMapper.map(foto, FotoProdutoDTO.class);
		dto.add(algaLinks.linkToFotoProduto(foto.getProduto().getId(), foto.getProduto().getRestaurante().getId(), "foto"));
		dto.add(algaLinks.linkToProduto(foto.getProduto().getRestaurante().getId(), foto.getProduto().getId(), "produto"));
		return dto;
	}
	
}
