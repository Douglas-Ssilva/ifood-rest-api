package com.algaworks.algafood.domain.infrastructure.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.infrastructure.repository.spec.RestauranteSpecs;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Repository
public class RestauranteRepositoryImpl implements CustomizedRestauranteRepository {// Deve ter Impl pra Spring conseguir vincular essa impl à interface RestauranteRepository

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired @Lazy
	private RestauranteRepository restauranteRepository;

	@Override
	public List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		var jpql = new StringBuilder("FROM Restaurante WHERE 0 = 0 ");
		var map = new HashMap<String, Object>();

		if (StringUtils.hasLength(nome)) {
			jpql.append("AND nome LIKE :nome ");
			map.put("nome", "%" + nome + "%");
		}
		if (Objects.nonNull(taxaInicial)) {
			jpql.append("AND taxaFrete >= :taxaInicial ");
			map.put("taxaInicial", taxaInicial);
		}
		if (Objects.nonNull(taxaFinal)) {
			jpql.append("AND taxaFrete <= :taxaFinal ");
			map.put("taxaFinal", taxaFinal);
		}

		TypedQuery<Restaurante> query = entityManager.createQuery(jpql.toString(), Restaurante.class);
		map.forEach((key, value) -> query.setParameter(key, value));
		return query.getResultList();

	}

	@Override
	public List<Restaurante> findWithPredicates(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		var builder = entityManager.getCriteriaBuilder();
		var criteriaQuery = builder.createQuery(Restaurante.class);
		var root = criteriaQuery.from(Restaurante.class);
		var predicates = new LinkedList<Predicate>();
		
		if (StringUtils.hasLength(nome)) {
			predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
		}
		if (Objects.nonNull(taxaInicial)) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaInicial));
		}
		if (Objects.nonNull(taxaFinal)) {
			predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFinal));
		}
		
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public List<Restaurante> comFreteGratis(String nome) {
		return restauranteRepository.findAll(RestauranteSpecs.comFreteGratis().and(RestauranteSpecs.comNomeSemelhante(nome)));
	}

}