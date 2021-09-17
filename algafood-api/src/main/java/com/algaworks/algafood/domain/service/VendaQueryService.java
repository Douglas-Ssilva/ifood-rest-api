package com.algaworks.algafood.domain.service;

import java.util.List;

import com.algaworks.algafood.domain.model.dto.VendaDiariaDTO;
import com.algaworks.algafood.domain.model.filter.VendasDiariasFilter;

public interface VendaQueryService {
	
	List<VendaDiariaDTO> consultarVendasDiarias(VendasDiariasFilter diariasFilter, String timezone);

}
