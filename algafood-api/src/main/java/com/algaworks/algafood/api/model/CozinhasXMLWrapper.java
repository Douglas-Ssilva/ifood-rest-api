package com.algaworks.algafood.api.model;

import java.util.List;

import com.algaworks.algafood.domain.model.Cozinha;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;
import lombok.NonNull;

//modelo de representacao de um recurso
@JsonRootName("cozinhas")
@Data
public class CozinhasXMLWrapper {
	
	@JsonProperty("cozinha")
//	@JacksonXmlElementWrapper(useWrapping = false)//desabilitando tag wrapper
	@NonNull
	private List<Cozinha> cozinhas;

}
