package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.openapi.EstatisticasControllerOpenApi;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNotFoundException;
import com.algaworks.algafood.domain.model.dto.VendaDiariaDTO;
import com.algaworks.algafood.domain.model.filter.VendasDiariasFilter;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.domain.service.VendaDiariaReportService;
import com.algaworks.algafood.domain.service.VendaQueryService;

@RestController
@RequestMapping(path = "/estatisticas")
public class EstatisticasController implements EstatisticasControllerOpenApi {
	
	public static class EstatisticasDTO extends RepresentationModel<EstatisticasDTO> {}
	
	@Autowired
	private VendaQueryService vendaQueryService;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private VendaDiariaReportService vendaReportService;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public EstatisticasDTO estatisticas() {
	    var estatisticasModel = new EstatisticasDTO();
	    estatisticasModel.add(algaLinks.linkToVendasDiarias("vendas-diarias"));
	    return estatisticasModel;
	}
	
	@Override
	@GetMapping(path = "/vendas-diarias",  produces = MediaType.APPLICATION_JSON_VALUE)//como esse DTO é somente para consultas, não vejo problemas ela está no pacote domain e retornamos aqui 
	public List<VendaDiariaDTO> consultarVendas(VendasDiariasFilter filter, @RequestParam(required = false, defaultValue = "+00:00") String timezone) {
		validarExistenciaRestaurante(filter);
		return vendaQueryService.consultarVendasDiarias(filter, timezone);
	}
	
	/**
	 * Consumidor da API deve mandar no accept assim: application/pdf,application/json, para pegar os json de mensagens de erro
	 */ 
	@Override
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE) 
	public ResponseEntity<?> consultarVendasPDF(VendasDiariasFilter filter, @RequestParam(required = false, defaultValue = "+00:00") String timezone) {
		if (filter.getRestauranteId() != null) {
			try {
				cadastroRestauranteService.buscar(filter.getRestauranteId());
			} catch (RestauranteNotFoundException e) {
				return ResponseEntity.notFound().build();
			}
		}
		
		byte[] pdf = vendaReportService.emitirVendasDiarias(filter, timezone);
		
		//attachment -> browser, faça o download desse arquivo
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(pdf);
	}

	private void validarExistenciaRestaurante(VendasDiariasFilter filter) {
		if (filter.getRestauranteId() != null) {
			try {
				cadastroRestauranteService.buscar(filter.getRestauranteId());
			} catch (RestauranteNotFoundException e) {
				throw new NegocioException(e.getMessage());
			}
		}
	}

}
