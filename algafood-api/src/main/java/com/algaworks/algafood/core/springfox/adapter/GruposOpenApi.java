package com.algaworks.algafood.core.springfox.adapter;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.GrupoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("GruposModel")
public class GruposOpenApi {
	
	private GruposEmbeddedModel _embedded;
	private Links _links;
	
	@Data
	@ApiModel("GruposEmbeddedModel")
	private class GruposEmbeddedModel {
		private List<GrupoDTO> grupos;
	}

}
