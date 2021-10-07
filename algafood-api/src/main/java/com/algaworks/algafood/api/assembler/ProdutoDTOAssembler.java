package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteProdutosController;
import com.algaworks.algafood.api.model.ProdutoDTO;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoDTOAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoDTO>{
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public ProdutoDTOAssembler() {
		super(RestauranteProdutosController.class, ProdutoDTO.class);
	}
	
	@Override
	public ProdutoDTO toModel(Produto p) {
		var dto = createModelWithId(p.getId(), p, p.getRestaurante().getId());
		mapper.map(p, dto);
		dto.add(algaLinks.linkToRestauranteProdutos(p.getRestaurante().getId(), "produtos"));
		dto.add(algaLinks.linkToRestauranteProdutoFoto(p.getRestaurante().getId(), dto.getId(), "foto"));
		return dto;
	}
	
	public CollectionModel<ProdutoDTO> toCollectionModel(Iterable<? extends Produto> entities, Long restauranteId) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToRestauranteProdutos(restauranteId));
	}
}
