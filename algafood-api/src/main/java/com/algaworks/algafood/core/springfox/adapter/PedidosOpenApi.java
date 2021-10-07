package com.algaworks.algafood.core.springfox.adapter;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.PedidoResumoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("PedidosModel")
public class PedidosOpenApi {
	
	private PedidosEmbeddedModel _embedded;
	private Links _links;
	private PagedModelOpenApi page;
	
	@Data
	@ApiModel("PedidosEmbeddedModel")
	private class PedidosEmbeddedModel {
		private List<PedidoResumoDTO> pedidos;
		
	}

}
