package com.algaworks.algafood.api.assembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.AlgaLinks;

import springfox.documentation.annotations.ApiIgnore;

/**
 * Para tirarmos melhor proveito do HATEOAS, esse será o ponto raiz e os consumidores irão se guiar por aqui, não tendo necessidade de conhecer as URLs e colocar de forma hard code
 */

@ApiIgnore
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {
	
	@Autowired
	private AlgaLinks algaLinks;
	
	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {}
	
	@GetMapping
	public RootEntryPointModel root() {
		var rootEntryPointModel = new RootEntryPointModel();
		rootEntryPointModel.add(algaLinks.linkToEstados("estados"));
		rootEntryPointModel.add(algaLinks.linkToCidades("cidades"));
		rootEntryPointModel.add(algaLinks.linkToCozinhas("cozinhas"));
		rootEntryPointModel.add(algaLinks.linkToRestaurantes("restaurantes"));
		rootEntryPointModel.add(algaLinks.linkToFormasPagamentos("formas-pagamento"));
		rootEntryPointModel.add(algaLinks.linkToGrupos("grupos"));
		rootEntryPointModel.add(algaLinks.linkToUsuarios("usuarios"));
		rootEntryPointModel.add(algaLinks.linkToPedidos("pedidos"));
		rootEntryPointModel.add(algaLinks.linkToPermissoes("permissoes"));
		rootEntryPointModel.add(algaLinks.linkToEstatisticas("estatisticas"));
		return rootEntryPointModel;
	}

}
