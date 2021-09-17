package com.algaworks.algafood.api.assembler;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.CidadeInputDTO;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class CidadeInputDTODisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Cidade toDomainObject(CidadeInputDTO dto) {
		return modelMapper.map(dto, Cidade.class);
	}

	public void copyProperties(@Valid CidadeInputDTO cidadeInputDTO, Cidade cidadeBD) {
		cidadeBD.setEstado(new Estado());
		modelMapper.map(cidadeInputDTO, cidadeBD);
	}
	
}
