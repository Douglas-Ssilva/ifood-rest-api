package com.algaworks.algafood.api.assembler;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.PermissaoController;
import com.algaworks.algafood.api.model.PermissaoDTO;
import com.algaworks.algafood.domain.model.Permissao;

@Component
public class PermissaoDTOAssembler extends RepresentationModelAssemblerSupport<Permissao, PermissaoDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public PermissaoDTOAssembler() {
		super(PermissaoController.class, PermissaoDTO.class);
	}

	@Override
	public PermissaoDTO toModel(Permissao p) {
		return modelMapper.map(p, PermissaoDTO.class);
	}
	
	@Override
	public CollectionModel<PermissaoDTO> toCollectionModel(Iterable<? extends Permissao> entities) {
		return super.toCollectionModel(entities).add(algaLinks.linkToPermissoes());
	}

	public CollectionModel<PermissaoDTO> toCollectionModelGrupoPermissoes(Set<Permissao> permissoes, Long grupoId) {
		return super.toCollectionModel(permissoes)
				.add(algaLinks.linkToPermissoesGrupo(grupoId))
				.add(algaLinks.linkToAddPermissaoGrupo(grupoId, "associar"));
	}
}
