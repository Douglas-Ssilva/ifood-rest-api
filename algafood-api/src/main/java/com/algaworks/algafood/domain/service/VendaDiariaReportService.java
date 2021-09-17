package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.filter.VendasDiariasFilter;

/**
 * Criada nesse pacote pois não teremos implementação aqui no pacote domain, porém o conceito de um serviço de relatório de vendas podemos
 * dizer que tem a ver com o negócio
 */
public interface VendaDiariaReportService {

	byte[] emitirVendasDiarias(VendasDiariasFilter filter, String offset);
}
