package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioDTOAssembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public UsuarioDTO toDTO(Usuario user) {
		return mapper.map(user, UsuarioDTO.class);
	}
	
	public List<UsuarioDTO> toCollectionDTO(Collection<Usuario> users) {
		return users.stream().map(this::toDTO).collect(Collectors.toList());
	}

}
