package com.algaworks.algafood.api.controller.openapi;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.algaworks.algafood.domain.model.dto.VendaDiariaDTO;
import com.algaworks.algafood.domain.model.filter.VendasDiariasFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Estatísticas")
public interface EstatisticasControllerOpenApi {

	@ApiOperation("Busca vendas diárias")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "restauranteId", value = "Id do restaurante", example = "1", dataType = "int"),
		@ApiImplicitParam(name = "dataCriacaoInicio", value = "Data inicial da consulta", example = "2021-08-13T22:12:00.857Z", dataType = "date-time"),
		@ApiImplicitParam(name = "dataCriacaoFim", value = "Data final da consulta", example = "2021-09-13T22:12:00.857Z", dataType = "date-time"),
		@ApiImplicitParam(name = "timezone", value = "Deslocamento de horário a ser considerado na consulta em relação ao UTC", example = "+00:00"),
	})
	List<VendaDiariaDTO> consultarVendas(VendasDiariasFilter filter, @RequestParam(required = false, defaultValue = "+00:00") String timezone);
	
	@ApiOperation("Busca vendas diárias PDF")
	ResponseEntity<?> consultarVendasPDF(VendasDiariasFilter filter, @RequestParam(required = false, defaultValue = "+00:00") String timezone) ;
}
