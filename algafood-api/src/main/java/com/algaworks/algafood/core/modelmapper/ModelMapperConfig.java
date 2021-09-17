package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.EnderecoDTO;
import com.algaworks.algafood.api.model.input.ItemPedidoInputDTO;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;

/**
 * Como o ModelMapper não é um componente do Spring, mas sim uma lib de terceiro, temos que ensinar ao Spring como criar uma instancia desse tipo
 * @author dougl
 *
 */

@Component
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		//alterando para atender correspondencia de propriedades
//		modelMapper.createTypeMap(Restaurante.class, RestauranteDTO.class)
//			.addMapping(Restaurante::getTaxaFrete, RestauranteDTO::setPrecoFrete);
		
		modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class)
		.addMapping(endereco -> endereco.getCidade().getEstado().getNome(), 
				(destino, value) -> destino.getCidade().setEstado((String) value));
//			.addMapping(Restaurante::getTaxaFrete, RestauranteDTO::setPrecoFrete);
		
		//devido a regras de correspondencias do modalMapper, pegava o ItemPedidoInputDTO.produtoId e atribuia ao ItemPedido.id
		modelMapper.createTypeMap(ItemPedidoInputDTO.class, ItemPedido.class)
		.addMappings(mapper -> mapper.skip(ItemPedido::setId));
		
		return modelMapper;
	}
}
