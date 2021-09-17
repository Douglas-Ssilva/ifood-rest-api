package com.algaworks.algafood.domain.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class VendaDiariaDTO {
	
	@ApiModelProperty(example = "2021-08-13")
	private Date data;//VendaQueryServiceImpl.consultarVendas função de truncar data não retorna um LocalDate
	@ApiModelProperty(example = "10")
	private Long totalVendas;
	@ApiModelProperty(example = "109.00")
	private BigDecimal totalFaturado;

}
