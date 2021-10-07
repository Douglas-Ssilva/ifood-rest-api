package com.algaworks.algafood.core.springfox.adapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("PageModel")
public class PagedModelOpenApi {
	
	@ApiModelProperty(example = "0", value = "Número da página (Começa com 0)")
	private int number;
	
	@ApiModelProperty(example = "10", value = "Quantidade de elementos por página")
	private int size;
	
	@ApiModelProperty(example = "20", value = "Total de elementos")
	private int totalElements;
	
	@ApiModelProperty(example = "5", value = "Total de páginas")
	private int totalPages;
}
