package com.algaworks.algafood.api;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.controller.EstatisticasController;
import com.algaworks.algafood.api.controller.FluxoStatusPedidoController;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.GrupoController;
import com.algaworks.algafood.api.controller.GrupoPermissoesController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.PermissaoController;
import com.algaworks.algafood.api.controller.ProdutoFotoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.RestauranteFormaPagamentoController;
import com.algaworks.algafood.api.controller.RestauranteProdutosController;
import com.algaworks.algafood.api.controller.RestauranteUsuariosController;
import com.algaworks.algafood.api.controller.UserController;
import com.algaworks.algafood.api.controller.UsuariosGruposController;

/**
 * Reponsável apenas por montar links
 * @author dougl
 *
 */
@Component
public class AlgaLinks {
	
	//template variables ("http://desenvolvimento:8080/pedidos{?page,sort,size}",)
	public static final TemplateVariables PAGINACAO_LINKS = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM));
	
	public Link linkToPedidos(String rel) {
		var pedidosLink = WebMvcLinkBuilder.linkTo(PedidoController.class).toUri().toString();
		TemplateVariables variablesFilter = new TemplateVariables(
				new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM));
		return Link.of(UriTemplate.of(pedidosLink, PAGINACAO_LINKS.concat(variablesFilter)), rel);
	}
	
	public Link linkToCidade(Long cidadeId) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).findById(cidadeId)).withSelfRel();
	}
	
	public Link linkToCidades() {
		return linkToCidades(IanaLinkRelations.SELF.value());
	}

	public Link linkToCidades(String rel) {
		return WebMvcLinkBuilder.linkTo(CidadeController.class).withRel(rel);
	}
	
	public Link linkToEstado(Long estadoId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class).findById(estadoId)).withRel(rel);
	}

	public Link linkToEstado(Long estadoId) {
		return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToEstados(String rel) {
		return WebMvcLinkBuilder.linkTo(EstadoController.class).withRel(rel);
	}

	public Link linkToEstados() {
		return linkToEstados(IanaLinkRelations.SELF.value());
	}

	public Link linkToCozinhas(String rel) {
		var linkCozinhas = WebMvcLinkBuilder.linkTo(CozinhaController.class).toUri().toString();
		return Link.of(UriTemplate.of(linkCozinhas, PAGINACAO_LINKS), rel);
	}

	public Link linkToCozinha(Long cozinhaId) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CozinhaController.class).findById(cozinhaId)).withSelfRel();
	}

	public Link linkToUsuarios(String rel) {
		return WebMvcLinkBuilder.linkTo(UserController.class).withRel(rel);
	}

	public Link linkToUsuarios() {
		return linkToUsuarios(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToUserGroup(Long userId, String rel) {
		return WebMvcLinkBuilder.linkTo((WebMvcLinkBuilder.methodOn(UsuariosGruposController.class).findAll(userId))).withRel(rel);
	}

	public Link linkToUserGroup(Long userId) {
		return linkToUserGroup(userId, IanaLinkRelations.SELF.value());
	}

	public Link linkToResposaveisRestaurante(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteUsuariosController.class).findAll(restauranteId)).withRel(rel);
	}
	
	public Link linkToRestaurante(Long restauranteId) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).findById(restauranteId)).withSelfRel();
	}
	
	public Link linkToRestaurantes(String rel) {
		var linkRestaurantes = WebMvcLinkBuilder.linkTo(RestauranteController.class).toUri().toString();
		var templateVariables = new TemplateVariables(new TemplateVariable("projecao", VariableType.REQUEST_PARAM));
		return Link.of(UriTemplate.of(linkRestaurantes, templateVariables ), rel);
	}

	public Link linkToCliente(Long clienteId) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).findById(clienteId)).withSelfRel();
	}

	public Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteProdutosController.class).findById(restauranteId, produtoId)).withRel(rel);
	}
	
	public Link linkToConfirmacaoPedido(String codigo) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FluxoStatusPedidoController.class).confirmar(codigo)).withRel("confirmar");
	}
	
	public Link linkToEntregarPedido(String codigo) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FluxoStatusPedidoController.class).entregar(codigo)).withRel("entregar");
	}

	public Link linkToCancelarPedido(String codigo) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FluxoStatusPedidoController.class).cancelar(codigo)).withRel("cancelar");
	}

	public Link linkToRestaurantes() {
		return linkToRestaurantes(IanaLinkRelations.SELF.value());
	}

	public Link linkToFormaPagamento(Long formaPagamentoId) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FormaPagamentoController.class).findById(formaPagamentoId, null)).withSelfRel();
	}
	
	public Link linkToFormasPagamento(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteFormaPagamentoController.class).findAll(restauranteId)).withRel(rel);
	}
	
	public Link linkToFormasPagamento(Long restauranteId) {
		return linkToFormasPagamento(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToResposaveisRestaurante(Long restauranteId) {
		return linkToResposaveisRestaurante(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToAtivarRestaurante(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).ativar(restauranteId)).withRel(rel);
	}

	public Link linkToDesativarRestaurante(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).desativar(restauranteId)).withRel(rel);
	}

	public Link linkToAbrirRestaurante(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).abrir(restauranteId)).withRel(rel);
	}

	public Link linkToFecharRestaurante(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteController.class).fechar(restauranteId)).withRel(rel);
	}

	public Link linkToFormasPagamentos(String rel) {
		return WebMvcLinkBuilder.linkTo(FormaPagamentoController.class).withRel(rel);
	}

	public Link linkToFormaPagamentos() {
		return linkToFormasPagamentos(IanaLinkRelations.SELF.value());
	}

	public Link linkToFormaPagamentoRestauranteDesassociar(Long restauranteId, Long formaPagamentoId) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteFormaPagamentoController.class).desassiociar(restauranteId, formaPagamentoId)).withRel("desassociar");
	}

	public Link linkToFormaPagamentoRestauranteAssociacao(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteFormaPagamentoController.class).associar(restauranteId, null)).withRel(rel);
	}

	public Link linkToDesassociacaoRestauranteResponsavel(Long restauranteId, Long userId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteUsuariosController.class).desassiociar(restauranteId, userId)).withRel(rel);
	}

	public Link linkToAssociacaoRestauranteResponsavel(Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteUsuariosController.class).associar(restauranteId, null)).withRel(rel);
	}
	
	public Link linkToRestauranteProdutos(Long restauranteId, String rel) {
		var linkProdutos = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RestauranteProdutosController.class).findAll(restauranteId, null)).toUri().toString();
		var templateVariables = new TemplateVariables(new TemplateVariable("buscarTodos", VariableType.REQUEST_PARAM));
		return Link.of(UriTemplate.of(linkProdutos, templateVariables), rel);
	}

	public Link linkToRestauranteProdutos(Long restauranteId) {
		return linkToRestauranteProdutos(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToRestauranteProdutoFoto(Long restauranteId, Long produtoId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProdutoFotoController.class).buscarFoto(restauranteId, produtoId)).withRel(rel);
	}

	public Link linkToFotoProduto(Long produtoId, Long restauranteId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProdutoFotoController.class).buscarFoto(restauranteId, produtoId)).withRel(rel);
	}
	
	public Link linkToGrupos(String rel) {
		return WebMvcLinkBuilder.linkTo(GrupoController.class).withRel(rel);
	}

	public Link linkToGrupos() {
		return linkToGrupos(IanaLinkRelations.SELF.value());
	}
	
	public Link linkToPermissoesGrupo(Long grupoId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GrupoPermissoesController.class).findAll(grupoId)).withRel(rel);
	}

	public Link linkToPermissoesGrupo(Long grupoId) {
		return linkToPermissoesGrupo(grupoId, IanaLinkRelations.SELF.value());
	}
	
	public Link linkToPermissoes() {
		return linkToPermissoes(IanaLinkRelations.SELF.value());
	}

	public Link linkToPermissoes(String rel) {
		return WebMvcLinkBuilder.linkTo(PermissaoController.class).withRel(rel);
	}

	public Link linkToAddPermissaoGrupo(Long grupoId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GrupoPermissoesController.class).addPermissao(grupoId, null)).withRel(rel);
	}

	public Link linkToDesassociarPermissaoGrupo(Long grupoId, Long permissaoId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GrupoPermissoesController.class).removePermissao(grupoId, permissaoId)).withRel(rel);
	}

	public Link linkToAddGroupUser(Long userId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuariosGruposController.class).addGrupo(userId, null)).withRel(rel);
	}

	public Link linkToDesassociarGrupoUser(Long usuarioId, Long grupoId, String rel) {
		return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuariosGruposController.class).removeGrupo(usuarioId, grupoId)).withRel(rel);
	}
	
	public Link linkToEstatisticas(String rel) {
		return WebMvcLinkBuilder.linkTo(EstatisticasController.class).withRel(rel);
	}

	public Link linkToEstatisticas() {
		return linkToEstatisticas(IanaLinkRelations.SELF.value());
	}

	public Link linkToVendasDiarias(String rel) {
		var linkVendas = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstatisticasController.class).consultarVendas(null, null)).toUri().toString();
		var templateVariables = new TemplateVariables(
				new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM), 
				new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM), 
				new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM),
				new TemplateVariable("timezone", VariableType.REQUEST_PARAM));
		return Link.of(UriTemplate.of(linkVendas, templateVariables), rel);
	}


}
