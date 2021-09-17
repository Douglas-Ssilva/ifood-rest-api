package com.algaworks.algafood.domain.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Situacao;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long>{

	@Query("SELECT MAX(dataAtualizacao) FROM FormaPagamento")
	Optional<OffsetDateTime> findLastUpdateDate(); 
	
	List<FormaPagamento> findBySituacao(Situacao situacao);

	@Query("SELECT MAX(dataAtualizacao) FROM FormaPagamento WHERE id = :id")
	Optional<OffsetDateTime> findLastUpdateDate(Long id);
}
