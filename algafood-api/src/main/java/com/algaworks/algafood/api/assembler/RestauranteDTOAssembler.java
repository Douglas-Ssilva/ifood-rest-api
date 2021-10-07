package com.algaworks.algafood.api.assembler;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteDTOAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDTO> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public RestauranteDTOAssembler() {
		super(RestauranteController.class, RestauranteDTO.class);
	}

	@Override
	public RestauranteDTO toModel(Restaurante restaurante) {
		var restauranteDTO = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteDTO);
		restauranteDTO.add(algaLinks.linkToRestaurantes("restaurantes"));
		restauranteDTO.add(algaLinks.linkToFormasPagamento(restauranteDTO.getId(), "formas-pagamento"));
		restauranteDTO.add(algaLinks.linkToResposaveisRestaurante(restauranteDTO.getId(), "responsaveis"));
		restauranteDTO.add(algaLinks.linkToRestauranteProdutos(restauranteDTO.getId(), "produtos"));
		restauranteDTO.getCozinha().add(algaLinks.linkToCozinha(restauranteDTO.getCozinha().getId()));
		if (restaurante.podeSerAtivado()) {
			restauranteDTO.add(algaLinks.linkToAtivarRestaurante(restauranteDTO.getId(), "ativar"));
		}
		if (restaurante.podeSerDesativado()) {
			restauranteDTO.add(algaLinks.linkToDesativarRestaurante(restauranteDTO.getId(), "desativar"));
		}
		if (restaurante.podeSerAberto()) {
			restauranteDTO.add(algaLinks.linkToAbrirRestaurante(restauranteDTO.getId(), "abrir"));
		}
		if (restaurante.podeSerFechado()) {
			restauranteDTO.add(algaLinks.linkToFecharRestaurante(restauranteDTO.getId(), "fechar"));
		}
		if (Objects.nonNull(restauranteDTO.getEndereco())) {
			restauranteDTO.getEndereco().getCidade().add(algaLinks.linkToCidade(restauranteDTO.getEndereco().getCidade().getId()));
		}
		return restauranteDTO;
	}
	
	@Override
	public CollectionModel<RestauranteDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities).add(algaLinks.linkToRestaurantes());
	}
	
}
