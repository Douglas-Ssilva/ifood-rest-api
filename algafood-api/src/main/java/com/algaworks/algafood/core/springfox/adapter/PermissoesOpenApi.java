package com.algaworks.algafood.core.springfox.adapter;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.PermissaoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("PermissoesModel")
public class PermissoesOpenApi {
	
	private PermissoesEmbeddedModel _embedded;
	private Links _links;
	
	@Data
	@ApiModel("PermissoesEmbeddedModel")
	private class PermissoesEmbeddedModel {
		private List<PermissaoDTO> permissoes;
	}

}
