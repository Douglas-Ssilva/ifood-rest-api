package com.algaworks.algafood.api.assembler;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.CozinhaInputDTO;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaInputDTODisassembler {
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Cozinha toDomainObject(CozinhaInputDTO dto) {
		return modelMapper.map(dto, Cozinha.class);
	}

	public void copyProperties(@Valid CozinhaInputDTO cozinhaInputDTO, Cozinha cozinhaBD) {
		modelMapper.map(cozinhaInputDTO, cozinhaBD);
	}

}
