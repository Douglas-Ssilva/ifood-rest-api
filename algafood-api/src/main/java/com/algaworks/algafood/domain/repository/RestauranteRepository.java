package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.infrastructure.repository.CustomizedRestauranteRepository;
import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>,  CustomizedRestauranteRepository, JpaSpecificationExecutor<Restaurante> {
	
	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long id);
	Optional<Restaurante> findFirstByNomeContaining(String nome);
	List<Restaurante> findTop2ByNomeContaining(String nome);
	int countByCozinhaId(Long idConzinha);
	boolean existsByNomeContaining(String nome);
	
//	@Query("FROM Restaurante WHERE nome LIKE %:nome% AND cozinha.id = :id")
	List<Restaurante> buscarRestaurantesPorNomeNaCozinha(String nome, @Param("id") Long idConzinha);
	
	@Override
//	@Query("SELECT DISTINCT r FROM Restaurante r JOIN FETCH r.cozinha LEFT OUTER JOIN FETCH r.formasPagamento")
	@Query("SELECT DISTINCT r FROM Restaurante r JOIN FETCH r.cozinha")
	List<Restaurante> findAll();
	
	
}
