package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.model.RestauranteDTOResumoList;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteDTOResumoListAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDTOResumoList> {

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public RestauranteDTOResumoListAssembler() {
		super(RestauranteController.class, RestauranteDTOResumoList.class);
	}
	
	@Override
	public RestauranteDTOResumoList toModel(Restaurante restaurante) {
		var restauranteDTO = createModelWithId(restaurante.getId(), restaurante);
		mapper.map(restaurante, restauranteDTO);
		restauranteDTO.add(algaLinks.linkToRestaurantes("restaurantes"));
		restauranteDTO.getCozinha().add(algaLinks.linkToCozinha(restauranteDTO.getCozinha().getId()));
		return restauranteDTO;
	}
	
	@Override
	public CollectionModel<RestauranteDTOResumoList> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities).add(algaLinks.linkToRestaurantes());
	}
	

}
