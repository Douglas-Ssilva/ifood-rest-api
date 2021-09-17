package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {

	@Override
	@Query("FROM Pedido p INNER JOIN FETCH p.restaurante r INNER JOIN FETCH r.cozinha INNER JOIN FETCH p.cliente")//reduzindo vários selects a 1 apenas
	List<Pedido> findAll();
	
	Optional<Pedido> findByCodigo(String codigo);
}
