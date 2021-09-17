package com.algaworks.algafood.api.controller.openapi.model;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageGeneric<T> {
	
	private List<T> content;
	
	@ApiModelProperty(example = "0", value = "Número da página (Começa com 0)")
	private int number;
	
	@ApiModelProperty(example = "10", value = "Quantidade de elementos por página")
	private int size;
	
	@ApiModelProperty(example = "20", value = "Total de elementos")
	private int totalElements;
	
	@ApiModelProperty(example = "5", value = "Total de páginas")
	private int totalPages;
	

}
