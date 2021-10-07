package com.algaworks.algafood.core.springfox.adapter;

import java.util.List;

import org.springframework.hateoas.Links;

import com.algaworks.algafood.api.model.FormaPagamentoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("FormasPagamentoModel")
public class FormasPagamentoOpenApi {
	
	private FormasPagamentoEmbedded _embedded;
	private Links _links;
	
	@Data
	@ApiModel("FormasEmbeddedPagamentoModel")
	private class FormasPagamentoEmbedded {
		private List<FormaPagamentoDTO> formasPagamentos;
	}
	

}
