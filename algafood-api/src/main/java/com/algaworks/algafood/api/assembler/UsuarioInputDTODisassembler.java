package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.UsuarioInputDTO;
import com.algaworks.algafood.api.model.input.UsuarioInputSemSenhaDTO;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioInputDTODisassembler {

	@Autowired
	private ModelMapper mapper;

	public Usuario toDomainObject(UsuarioInputDTO dto) {
		return mapper.map(dto, Usuario.class);
	}

	public void copyProperties(UsuarioInputSemSenhaDTO dto, Usuario usuario) {
		mapper.map(dto, usuario);
	}

}
