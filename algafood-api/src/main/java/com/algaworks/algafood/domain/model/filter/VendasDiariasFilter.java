package com.algaworks.algafood.domain.model.filter;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Getter;
import lombok.Setter;

/**
 * Decidimos não anotar as propriedades com anotações do swagger pois trata-se de uma classe de domínio 
 *
 */

@Getter
@Setter
public class VendasDiariasFilter {
	
	private Long restauranteId;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoInicio;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private OffsetDateTime dataCriacaoFim;
	
	
}
