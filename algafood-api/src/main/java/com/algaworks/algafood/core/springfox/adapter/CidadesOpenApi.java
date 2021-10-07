package com.algaworks.algafood.core.springfox.adapter;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.CidadeDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("CidadesModel")
public class CidadesOpenApi {
	
	private CidadesEmbedded _embedded;
	private Links _links;
	
	@Data
	@ApiModel("CidadesEmbeddedModel")
	private class CidadesEmbedded {
		private List<CidadeDTO> cidades;
	}

}
