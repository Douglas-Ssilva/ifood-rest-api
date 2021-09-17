package com.algaworks.algafood.domain.infrastructure.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.model.dto.VendaDiariaDTO;
import com.algaworks.algafood.domain.model.filter.VendasDiariasFilter;
import com.algaworks.algafood.domain.service.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

	@PersistenceContext
	private EntityManager manager;
	
	/**
	 * convert_tz do mysql para converter a data conforme offset
	 * 
	 * select date(convert_tz(p.data_criacao, '+00:00', '-03:00')) as data_pedido, 
	 *	count(p.id) as total_vendas, 
	 *	sum(p.valor_total) as total 
	 *	from pedido p
	 *	group by date(convert_tz(p.data_criacao, '+00:00', '-03:00'));
	 */
	@Override
	public List<VendaDiariaDTO> consultarVendasDiarias(VendasDiariasFilter filter, String timezone) {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(VendaDiariaDTO.class);
		var root = query.from(Pedido.class);
		
		var predicates = new ArrayList<Predicate>();
		predicates.add(root.get("status").in(Arrays.asList(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE)));
		if (filter.getRestauranteId() != null) {
			predicates.add(builder.equal(root.get("restaurante"), filter.getRestauranteId()));
		}
		if (filter.getDataCriacaoInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoInicio()));
		}
		if (filter.getDataCriacaoFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoFim()));
		}
		
		Expression<String> literalUTC = builder.literal("+00:00");
		Expression<String> literalTimezone = builder.literal(timezone);
		
		var functionConvertTzDataCriacao = builder.function("convert_tz", Date.class, root.get("dataCriacao"), literalUTC, literalTimezone);
		var functionDateDataCriacao = builder.function("date", Date.class, functionConvertTzDataCriacao);
		
		var selection = builder.construct(VendaDiariaDTO.class, 
				functionDateDataCriacao,
				builder.count(root.get("id")), 
				builder.sum(root.get("valorTotal")));
		
		query.select(selection);
		query.where(predicates.toArray(new Predicate[0]));
		query.groupBy(functionDateDataCriacao);
		
		return manager.createQuery(query).getResultList();
	}

}
