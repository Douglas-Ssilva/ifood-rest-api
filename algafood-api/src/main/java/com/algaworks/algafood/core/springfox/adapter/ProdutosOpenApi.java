package com.algaworks.algafood.core.springfox.adapter;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.ProdutoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("ProdutosModel")
public class ProdutosOpenApi {
	
	private ProdutosEmbeddedModel _embedded;
	private Links _links;
	
	@Data
	@ApiModel("ProdutosEmbeddedModel")
	private class ProdutosEmbeddedModel {
		private List<ProdutoDTO> produtos;
	}

}
