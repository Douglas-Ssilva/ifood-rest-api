package com.algaworks.algafood.core.springfox.adapter;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.EstadoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("EstadosModel")
public class EstadosOpenApi {
	
	private EstadosEmbeddedModel _embedded;
	private Links _links;
	
	@Data
	@ApiModel("EstadosEmbeddedModel")
	private class EstadosEmbeddedModel {
		private List<EstadoDTO> estados;
	}

}
