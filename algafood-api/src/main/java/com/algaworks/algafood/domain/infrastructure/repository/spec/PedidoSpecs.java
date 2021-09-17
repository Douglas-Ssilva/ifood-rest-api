package com.algaworks.algafood.domain.infrastructure.repository.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.filter.PedidoFilter;

//Fábrica de Specificacion, assim não preciso dar new em cada Spec RestauranteComFreteGratisSpec
public class PedidoSpecs {
	
	public static Specification<Pedido> usandoFiltro(PedidoFilter filter) {
		return (root, query, builder) -> {
			//O Pageable faz um select count pra verificar quantos elementos existem no total, e se não colocarmos essa condição lança excação:  query specified join fetching, 
			//but the owner of the fetched association was not present in the select list pois não faz sentido count com fetch que é o retorno do select
			if (Pedido.class.equals(query.getResultType())) {
				root.fetch("cliente");
				root.fetch("restaurante").fetch("cozinha");//evitando problema do N + 1
			}
			
			var predicates = new ArrayList<>();
			if (filter.getClienteId() != null) {
				predicates.add(builder.equal(root.get("cliente"), filter.getClienteId()));
			}
			if (filter.getRestauranteId() != null) {
				predicates.add(builder.equal(root.get("restaurante"), filter.getRestauranteId()));
			}
			if (filter.getDataCriacaoInicio() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoInicio()));
			}
			if (filter.getDataCriacaoFim() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoFim()));
			}
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
