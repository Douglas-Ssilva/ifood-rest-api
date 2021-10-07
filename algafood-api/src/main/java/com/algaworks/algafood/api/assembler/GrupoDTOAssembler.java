package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.GrupoController;
import com.algaworks.algafood.api.model.GrupoDTO;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoDTOAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoDTO>{
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public GrupoDTOAssembler() {
		super(GrupoController.class, GrupoDTO.class);
	}
	
	@Override
	public GrupoDTO toModel(Grupo grupo) {
		var dto = createModelWithId(grupo.getId(), grupo);
		modelMapper.map(grupo, dto);
		dto.add(algaLinks.linkToGrupos("grupos"));
		dto.add(algaLinks.linkToPermissoesGrupo(dto.getId(), "permissoes"));
		return dto;
	}
	
	@Override
	public CollectionModel<GrupoDTO> toCollectionModel(Iterable<? extends Grupo> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToGrupos());
	}
	
	public CollectionModel<GrupoDTO> toCollectionModelGruposUser(Iterable<? extends Grupo> entities, Long userId) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToUserGroup(userId))
				.add(algaLinks.linkToAddGroupUser(userId, "associar"));
	}
	
}
