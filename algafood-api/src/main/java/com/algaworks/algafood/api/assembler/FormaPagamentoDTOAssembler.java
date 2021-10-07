package com.algaworks.algafood.api.assembler;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.model.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoDTOAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoDTO>{
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public FormaPagamentoDTOAssembler() {
		super(FormaPagamentoController.class, FormaPagamentoDTO.class);
	}
	
	@Override
	public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
		var dto = createModelWithId(formaPagamento.getId(), formaPagamento);
		modelMapper.map(formaPagamento, dto);
		dto.add(algaLinks.linkToFormasPagamentos("formas-pagamento"));
		return dto;
	}
	
	@Override
	public CollectionModel<FormaPagamentoDTO> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
		return super.toCollectionModel(entities).add(algaLinks.linkToFormaPagamentos());
	}

	public CollectionModel<FormaPagamentoDTO> toCollectionModelRestaurantes(Set<FormaPagamento> formasPagamento, Long restauranteId) {
		return super.toCollectionModel(formasPagamento)
				.add(algaLinks.linkToFormasPagamento(restauranteId))
				.add(algaLinks.linkToFormaPagamentoRestauranteAssociacao(restauranteId, "associar"));
	}

}
