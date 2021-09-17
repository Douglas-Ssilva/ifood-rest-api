package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PedidoDTOAssembler;
import com.algaworks.algafood.api.assembler.PedidoInputDTODisassembler;
import com.algaworks.algafood.api.assembler.PedidoResumoDTOAssembler;
import com.algaworks.algafood.api.controller.openapi.PedidosControllerOpenApi;
import com.algaworks.algafood.api.model.PedidoDTO;
import com.algaworks.algafood.api.model.PedidoResumoDTO;
import com.algaworks.algafood.api.model.input.PedidoInputDTO;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.infrastructure.repository.spec.PedidoSpecs;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.filter.PedidoFilter;
import com.algaworks.algafood.domain.service.CadastroPedidoService;

//@CrossOrigin(maxAge = 10)//tempo máximo, em segundos, que os navegadores irão armazenar, em cache, a resposta do preflight. Default 30min
@RestController
@RequestMapping(path = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidosControllerOpenApi {
	
	@Autowired
	private CadastroPedidoService cadastroPedidoService;
	
	@Autowired
	private PedidoDTOAssembler pedidoDTOAssembler;
	
	@Autowired
	private PedidoResumoDTOAssembler pedidoResumoDTOAssembler;
	
	@Autowired
	private PedidoInputDTODisassembler pedidoInputDTODisassembler;
	
	//desenvolvimento:8080/pedidos?clienteId=1&restauranteId=1&dataCriacaoInicio=2021-08-14T10:09:33Z&dataCriacaoFim=2021-08-15T20:09:33Z
	@Override
	@GetMapping
	public Page<PedidoResumoDTO> pesquisar(PedidoFilter filter, @PageableDefault(size = 2) Pageable pageable) { //Spring faz a injeção do filtro sem anotar com nada
		pageable = handlePageable(pageable);
		Page<Pedido> pedidosPage = cadastroPedidoService.findAll(PedidoSpecs.usandoFiltro(filter), pageable);
		List<PedidoResumoDTO> pedidos = pedidoResumoDTOAssembler.toCollectionDTO(pedidosPage.getContent());
		return new PageImpl<>(pedidos, pageable, pedidosPage.getTotalElements());
	}

	@Override
	@GetMapping("/{pedidoCodigo}")
	public PedidoDTO findById(@PathVariable String pedidoCodigo) {
		return pedidoDTOAssembler.toDTO(cadastroPedidoService.findByCodigo(pedidoCodigo));
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO emitirPedido(@RequestBody @Valid PedidoInputDTO dto) {
		var pedido = pedidoInputDTODisassembler.toDomainObject(dto);
		
		try {
			return pedidoDTOAssembler.toDTO(cadastroPedidoService.emitirPedido(pedido));
		} catch (EntityNotFoundException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	/**
	 * Caso na representação do recurso exibamos nomes de propriedades que não correspondem com a entidade, o SDJPA lança uma exceção informando que na entidade não existe aquela propriedade.
	 *  Assim temos que traduzir as propriedades. Coloco no mapa todas propriedades que os clientes podem filtrar
	 */
	private Pageable handlePageable(Pageable pageable) {
		var mapeamento = Map.of(
				"nomeCliente", "cliente.nome",
				"codigo", "codigo",
				"taxaFrete", "taxaFrete",
				"restaurante.nome", "restaurante.nome",
				"dataCriacao", "dataCriacao");
		return PageableTranslator.translator(mapeamento, pageable);
	}

}
