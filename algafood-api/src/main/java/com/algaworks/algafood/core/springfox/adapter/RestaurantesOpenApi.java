package com.algaworks.algafood.core.springfox.adapter;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.RestauranteDTOResumoList;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("RestaurantesModel")
public class RestaurantesOpenApi {
	
	private RestaurantesEmbeddedModel _embedded;
	private Links _links;
	
	@Data
	@ApiModel("RestaurantesEmbeddedModel")
	private class RestaurantesEmbeddedModel {
		private List<RestauranteDTOResumoList> restaurantes;
	}

}
