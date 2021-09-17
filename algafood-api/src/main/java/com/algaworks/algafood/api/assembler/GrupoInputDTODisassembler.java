package com.algaworks.algafood.api.assembler;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.GrupoInputDTO;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoInputDTODisassembler {
	
	@Autowired
	private ModelMapper mapper;
	
	public Grupo toDomainObject(GrupoInputDTO dto) {
		return mapper.map(dto, Grupo.class);
	}

	public void copyProperties(@Valid GrupoInputDTO dto, Grupo grupo) {
		mapper.map(dto, grupo);
	}

}
