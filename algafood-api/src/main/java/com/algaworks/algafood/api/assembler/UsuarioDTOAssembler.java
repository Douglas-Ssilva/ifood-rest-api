package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.UserController;
import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioDTOAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO> {
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public UsuarioDTOAssembler() {
		super(UserController.class, UsuarioDTO.class);
	}
	
	@Override
	public UsuarioDTO toModel(Usuario user) {
		var userWithSelRel = createModelWithId(user.getId(), user);
		mapper.map(user, userWithSelRel);
		userWithSelRel.add(algaLinks.linkToUsuarios("usuarios"));
		userWithSelRel.add(algaLinks.linkToUserGroup(userWithSelRel.getId(), "grupos-user"));
		return userWithSelRel;
	}
	
	public CollectionModel<UsuarioDTO> toCollectionModelUsuarios(Iterable<? extends Usuario> entities) {
		return toCollectionModel(entities).add(algaLinks.linkToUsuarios());
	}
	
	public CollectionModel<UsuarioDTO> toCollectionModelResponsaveisRestaurante(Iterable<? extends Usuario> entities, Long restauranteId) {
		return toCollectionModel(entities)
				.add(algaLinks.linkToResposaveisRestaurante(restauranteId))
				.add(algaLinks.linkToAssociacaoRestauranteResponsavel(restauranteId, "associar"));
	}

}
