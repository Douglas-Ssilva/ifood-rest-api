package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.model.RestauranteDTOResumo;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteDTOResumoAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDTOResumo> {

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public RestauranteDTOResumoAssembler() {
		super(RestauranteController.class, RestauranteDTOResumo.class);
	}
	
	@Override
	public RestauranteDTOResumo toModel(Restaurante restaurante) {
		var restauranteDTO = createModelWithId(restaurante.getId(), restaurante);
		mapper.map(restaurante, restauranteDTO);
		restauranteDTO.add(algaLinks.linkToRestaurantes("restaurantes"));
		return restauranteDTO;
	}
	
	@Override
	public CollectionModel<RestauranteDTOResumo> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities).add(algaLinks.linkToRestaurantes());
	}
	

}
