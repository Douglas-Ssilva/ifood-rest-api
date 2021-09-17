package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoInputId {

	@ApiModelProperty(example = "1", required = true)//essa anotação possui required=false por default, com isso sobrescreve a impl da BeanValidatorPluginsConfiguration.class na SpringFoxConfig
	@NotNull
	private Long id;
}
