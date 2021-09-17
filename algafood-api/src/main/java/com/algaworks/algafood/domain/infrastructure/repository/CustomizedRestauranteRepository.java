package com.algaworks.algafood.domain.infrastructure.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algaworks.algafood.domain.model.Restaurante;

public interface CustomizedRestauranteRepository {

	List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);
	List<Restaurante> findWithPredicates(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);
	List<Restaurante> comFreteGratis(String nome);

}