package com.algaworks.algafood.core.springfox.adapter;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.UsuarioDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("UsuariosModel")
public class UsuariosOpenApi {
	
	private UsuariosEmbeddedModel _embedded;
	private Links _links;
	
	@Data
	@ApiModel("UsuariosEmbeddedModel")
	private class UsuariosEmbeddedModel {
		private List<UsuarioDTO> usuarios;
	}

}
