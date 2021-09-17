package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.RestauranteInputDTO;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDTODisassembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public Restaurante toDomainObject(RestauranteInputDTO restauranteInputDTO) {
		return modelMapper.map(restauranteInputDTO, Restaurante.class);
	}

	/**
	 *  Para evitar: Caused by: org.hibernate.HibernateException: identifier of an instance of com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2
	 */
	public void copyToDomainObject(RestauranteInputDTO restauranteInputDTO, Restaurante restauranteBD) {
		restauranteBD.setCozinha(new Cozinha());
		if (restauranteBD.getEndereco() != null) {
			restauranteBD.getEndereco().setCidade(new Cidade());
		}
		modelMapper.map(restauranteInputDTO, restauranteBD);
		
	}
	
}
