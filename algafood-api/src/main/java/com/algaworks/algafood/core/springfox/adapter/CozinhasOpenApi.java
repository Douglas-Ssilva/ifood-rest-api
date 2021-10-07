package com.algaworks.algafood.core.springfox.adapter;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.CozinhaDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("CozinhasModel")
public class CozinhasOpenApi {
	
	private CozinhasEmbeddedModel _embedded;
	private Links _links;
	private PagedModelOpenApi page;
	
	@Data
	@ApiModel("CozinhasEmbeddedModel")
	private class CozinhasEmbeddedModel {
		private List<CozinhaDTO> cozinhas;
		
	}

}
