package com.algaworks.algafood.domain.infrastructure.report;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.filter.VendasDiariasFilter;
import com.algaworks.algafood.domain.service.VendaDiariaReportService;
import com.algaworks.algafood.domain.service.VendaQueryService;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Slf4j
@Service
public class PDFVendaDiariaReportService implements VendaDiariaReportService {

	@Autowired
	private VendaQueryService vendaQueryService; 
	
	@Override
	public byte[] emitirVendasDiarias(VendasDiariasFilter filter, String offset) {
		try {
			// Dentro do meu classpath busque esse fluxo de dados
			var inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias-with-border.jasper");
			var inputStreamLogo = this.getClass().getResourceAsStream("/relatorios/logo-algaworks.png");

			// Caso quisessemos imprimir a data inicial e final que o user mandou iríamos  passar aqui
			var parameters = new HashMap<String, Object>();
			parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));
			parameters.put("logo", inputStreamLogo);

			var vendas = vendaQueryService.consultarVendasDiarias(filter, offset);
			var dataSource = new JRBeanCollectionDataSource(vendas);

			// jasperPrint é um documento preenchido
			var jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ReportException("Não foi possível gerar o relatório.", e);
		}
	}
	

}
