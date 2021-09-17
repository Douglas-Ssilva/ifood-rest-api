package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("Problema")
@JsonInclude(value = Include.NON_NULL)
@Getter
@Builder
/**
 * Problem Details for HTTP APIs
 * 
 */
public class Problem {
	
	@ApiModelProperty(value = "400", position = 1)
	private Integer status;
	@ApiModelProperty(value = "https://algafood.com/invalid-datas", position = 3)
	private String type;
	@ApiModelProperty(value = "Dados inválidos", position = 5)
	private String title;
	@ApiModelProperty(value = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.", position = 7)
	private String detail;
	
	//specialization
	@ApiModelProperty(value = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.", position = 9)
	private String userMessage;
	@ApiModelProperty(value = "2021-09-02T22:07:51.2360999Z", position = 11)
	private OffsetDateTime timestamp;
	@ApiModelProperty(position = 13)
	private List<Object> objects;
	
	@ApiModel(value = "ObjetoProblema", description = "Lista de objetos ou campos que geraram o erro (opcional)")
	@Getter
	@Builder
	public static class Object {
		@ApiModelProperty(value = "preço")
		private String name;
		
		@ApiModelProperty(value = "preço é obrigatório")
		private String userMessage;
		
	}
	

}
