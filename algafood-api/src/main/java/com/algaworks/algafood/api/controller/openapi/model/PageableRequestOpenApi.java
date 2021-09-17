package com.algaworks.algafood.api.controller.openapi.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Query params request
 */
@ApiModel("Pageable")
@Getter
@Setter
public class PageableRequestOpenApi {
	
	@ApiModelProperty(example = "10", value = "Quantidade de elementos por página")
	private int size;
	
	@ApiModelProperty(example = "0", value = "Número da página (Começa com 0)")
	private int page;

	@ApiModelProperty(example = "nome,asc", value = "Nome da propriedade para ordenação")
	private List<String> sort;
}
